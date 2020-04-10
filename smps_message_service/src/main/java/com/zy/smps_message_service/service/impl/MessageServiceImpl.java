package com.zy.smps_message_service.service.impl;

import com.zy.smps_message_service.entity.*;
import com.zy.smps_message_service.helper.RedisHelper;
import com.zy.smps_message_service.mapper.MessageMapper;
import com.zy.smps_message_service.page.HtmlHelper;
import com.zy.smps_message_service.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Transactional
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<MessageEntity> findNotPublishMess(PageParam pageParam){
        if (!StringUtils.isEmpty(pageParam.getTypeName())){
            log.info(" 执行分类分支");
            return messageMapper.findNotPublishMessByType(pageParam);
        }
        return messageMapper.findNotPublishMess(pageParam);
    }

    //查询全部信息
    @Override
    public List<MessageEntity> findAll(PageParam pageParam) {
        pageParam.setPage(0);
        String key=String.valueOf(pageParam.hashCode());
        if (redisTemplate.hasKey(key)){
            log.info("way: {}","use redis");
            return (List<MessageEntity>) redisTemplate.opsForSet().pop(key);
        }else{
            List<MessageEntity> messages = messageMapper.findAll(pageParam);
            redisTemplate.opsForSet().add(key,messages);
            redisTemplate.expire(key,15,TimeUnit.MINUTES);
            RedisHelper.keySet().add(key);
            return messages;
        }
    }

    @Override
    public MessageEntity findOneById(String mId) {
        return messageMapper.findOneById(mId);
    }

    //查看消息和其评论
    @Override
    public int updateViewNum(String mId) {
        log.info(mId);
        int viewNum = messageMapper.getViewNum(mId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("num",viewNum+1);
        map.put("mId",mId);
        return messageMapper.updateMessageViewNum(map);//更新浏览量
    }

    //保存信息
    @Override
    public int saveMessage(MessageEntity message) {
        int row= messageMapper.saveMessage(message);
        log.info("messId: "+message.getMessId());
        saveMessageType(message);
        return row;
    }

    //更新状态
    @Override
    public int updateMessageState(Map<String,Object> map) {
        String mId=(String) map.get("mId");
        String content=messageMapper.findOneById(mId).getContent();
        if ((int)map.get("messState")==MessageEntity.MESSAGE_STATE_PUBLISH){ //发布
            String str=content.replace("--start--","")
                           .replace("--end--","")
                           .replace("++start++","")
                            .replace("++end++","");
            new Thread(()-> HtmlHelper.createHtml(mId,str,true)).start();
            Collection<String> set= RedisHelper.keySet();
            redisTemplate.delete(set);
        }else if ((int)map.get("messState")==MessageEntity.MESSAGE_STATE_CANCEL){
            new Thread(()->HtmlHelper.createHtml(mId,content,false)).start();
        }
        return messageMapper.updateMessageState(map);
    }

    //查询名下所写的消息
    @Override
    public List<MessageEntity> findMessageByAccount(PageParam pageParam) {
        return messageMapper.findMessageByAccount(pageParam);
    }

    //更新消息
    @Override
    public int updateMessage(MessageEntity message) {
        int row = messageMapper.updateMessage(message);
        messageMapper.deleteMessageType(message.getMessId());
        saveMessageType(message);
        return row;
    }

    //删除消息
    @Override
    public int deleteMessage(List<String> mIds) throws Exception {
        int row=0;
        for (String mId:mIds) {
            messageMapper.deleteMessageType(mId); //删除所有分类记录
            messageMapper.deleteCommentBymId(mId);
            row+=messageMapper.deleteMessage(mId);
            HtmlHelper.deleteHtml(mId,false); //删除html 文件
        }
        return row ;
    }

    @Override //保存评论
    public int saveComment(CommentEntity comment) {
        String messId = comment.getMessId();
        int commentNum = messageMapper.getCommentNum(messId)+1;
        HashMap<String, Object> map = new HashMap<>();
        map.put("num",commentNum+1);
        map.put("mId",messId);
        messageMapper.updateMessageCommentNum(map);
        return messageMapper.saveComment(comment);
    }

    @Override //删除评论
    public int deleteCommentById(String mId,String commentId) {
        int commentNum = messageMapper.getCommentNum(mId)-1;
        HashMap<String, Object> map = new HashMap<>();
        map.put("num",commentNum);
        map.put("mId",mId);
        messageMapper.updateMessageCommentNum(map);
        return messageMapper.deleteCommentById(commentId);
    }

    @Override //查询用户收藏的全部信息
    public List<CollectMessageEntity> findUserCollectMess(PageParam pageParam) {
        List<CollectMessageEntity> messages = messageMapper.findUserCollectMess(pageParam);
        return messages;
    }

    //保存消息类型
    private int saveMessageType(MessageEntity message){
        int raw=0;
        HashMap<String, Object> map = new HashMap<>();
        map.put("createTime",message.getCreateTime());
        map.put("mId",message.getMessId());
        map.put("creator",message.getAccount());
        String[] typeIds = message.getTypeId();
        for (String id: typeIds) {
            map.put("tId",id);
            raw+=messageMapper.saveMessageType(map);
        }
        return raw;
    }

    @Override //
    public List<MessageTypeEntity> findAllMessType() {
        return messageMapper.findAllMessType();
    }

    @Override //删除用户收藏
    public int deleteUserCollection(List<String> messIds){
        int row=0;
        for (String id:messIds){
            row+=messageMapper.deleteUserCollection(id);
        }
        return row;
    }

    @Override  //收藏信息
    public int collectMessage(CollectMessageEntity entity) {
        return messageMapper.collectMessage(entity);
    }

    @Override
    public List<MessageEntity> findCanceledMessage(PageParam pageParam) {
        return messageMapper.findCanceledMessage(pageParam);
    }
}
