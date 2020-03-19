package com.zy.smps_message_service.controller;

import com.zy.smps_message_service.entity.*;
import com.zy.smps_message_service.helper.RequestResultModel;
import com.zy.smps_message_service.service.MessageService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {

    private final String SUCCESS="success";
    private final String FAILURE="failure";
    private final String MESSAGE_URL="http://127.0.0.1:8080/smps/message/view/";

    @Autowired
    private MessageService messageService;

    @GetMapping("/list") //查询未发布的信息
    public RequestResultModel<MessageEntity> getNotPublishMessage(PageParam pageParam){
        List<MessageEntity> messages = messageService.findNotPublishMess(pageParam);
        return  handleResult(pageParam,messages);
    }

    @GetMapping("/list/home") //查询全部已发布的信息
    public List<MessageEntity> findAll(@ModelAttribute PageParam pageParam){
        List<MessageEntity> messages = messageService.findAll(pageParam);
        int size=pageParam.getLimit();
        int end=(size!=0? size:PageParam.PAGE_NUM);
        if (end>messages.size()){
            end=messages.size();
        }
        int start=(pageParam.getPage()-1)*size;
        for (int i=start;i<end;i++){
            MessageEntity entity=messages.get(i);
            entity.setUrl(MESSAGE_URL+entity.getMessId());
            LocalDateTime dateTime = entity.getPublishDate().toLocalDateTime();
            String publishTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
            entity.setTimeStr(publishTime);
        }
        return messages;
    }

    @GetMapping("/list/{account}") //查询用户已发布的消息
    public  RequestResultModel<MessageEntity> findMessageByAccount(@PathVariable("account")String account,
                                                                   @ModelAttribute PageParam pageParam){
        pageParam.setAccount(account);
        List<MessageEntity> messages = messageService.findMessageByAccount(pageParam);
        return handleResult(pageParam,messages);

    }

    @GetMapping("/list/cancel/{account}")  //查询用户被驳回的信息
    public RequestResultModel<MessageEntity> findCanceledMessage(@PathVariable("account")String account,
                                                                 PageParam pageParam){
        pageParam.setAccount(account);
        List<MessageEntity> messages = messageService.findCanceledMessage(pageParam);
        try{
            int start=(pageParam.getPage()-1)*pageParam.getLimit();
            int end=start+pageParam.getLimit();
            if (end>messages.size()){
                end=messages.size();
            }
            for (int i=start;i<end;i++){
                MessageEntity entity = messages.get(i);
                List<MessageTypeEntity> types = entity.getTypes();
                StringBuffer sb = new StringBuffer();
                for (MessageTypeEntity typeEntity:types){
                    if (sb.length()>0){
                        sb.append("、");
                    }
                    sb.append(typeEntity.getTypeName());
                }
                LocalDateTime createTime = entity.getCreateTime().toLocalDateTime();
                entity.setTimeStr(createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                entity.setType(sb.toString());
            }
            return new RequestResultModel<>(messages.size(),"success",0,messages.subList(start,end));
        }catch (Exception e){
            return new RequestResultModel<>(-1,"查询数据失败",-1,null);
        }
    }
    @GetMapping("/list/collect/{account}")  //查看用户的收藏
    public RequestResultModel<CollectMessageEntity> findUserCollection(@PathVariable("account")String account,
                                                                       @ModelAttribute PageParam pageParam){
        pageParam.setAccount(account);
        List<CollectMessageEntity> userCollectMess = messageService.findUserCollectMess(pageParam);
        try {
            int start=(pageParam.getPage()-1)*pageParam.getLimit();
            int end=start+pageParam.getLimit();
            if (end>userCollectMess.size()){
                end=userCollectMess.size();
            }
            for (CollectMessageEntity entity:userCollectMess){
                List<MessageTypeEntity> types = entity.getTypes();
                StringBuffer sb = new StringBuffer();
                for (MessageTypeEntity typeEntity:types){
                    if (sb.length()>0){
                        sb.append(",");
                    }
                    sb.append(typeEntity.getTypeName());
                }
                LocalDateTime dateTime = entity.getCollectTime().toLocalDateTime();
                String str = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                entity.setCollectionTimeStr(str);
                entity.setTypeStr(sb.toString());
            }
            return new RequestResultModel<>(userCollectMess.size(),"success",0,userCollectMess.subList(start,end));
        }catch (Exception e){
            return new RequestResultModel<>(-1,"获取数据失败",-1,null);
        }
    }
    @GetMapping("/{messId}") //用户查看消息（获取消息内容）
    public Map<String,String> getMessageContent(@PathVariable("messId")String messId){
        HashMap<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(messId)){
            MessageEntity mess = messageService.findOneById(messId);
            map.put("message",mess.getContent());
        }else{
            map.put("message","<h1>服务器繁忙，请稍后重试！<h1>");
        }
        return map;
    }

    @DeleteMapping("/{messIds}") //删除用户发布的消息
    public Map<String,String> deleteMessage(@PathVariable("messIds") String mIdStr) throws Exception {
        String[] mIds = mIdStr.split(",");
        return getResult(messageService.deleteMessage(Arrays.asList(mIds)));
    }

    @DeleteMapping("/list/collect/{messIds}")  //删除用户收藏
    public Map<String,String> deleteUserCollection(@PathVariable("messIds")String messIdStr){
        String[] messIds = messIdStr.split(",");
        List<String> messIdList = Arrays.asList(messIds);
        return getResult(messageService.deleteUserCollection(messIdList));
    }

    @PutMapping("/{mId}") //提交用户重新编辑的信息
    public Map<String,String> updateMessageInfo(@PathVariable("mId")String mId,@RequestParam("content")String content,
                                                @RequestParam("contentText")String contentText,@RequestParam("typeIds")String typeIdStr){
        MessageEntity entity=messageService.findOneById(mId);
        int start1=contentText.indexOf("--start--")+9; //标题的开始位置
        int end=contentText.indexOf("--end--")-6;
        String title=(contentText.substring(start1,end)).replace("&nbsp;","");
        entity.setTitle(title); //标题
        int start2=contentText.indexOf("++start++")+9; //摘要的开始位置
        String remark=contentText.substring(start2,start2+40);
        entity.setRemark(remark);//摘要
        entity.setContent(content); //内容
        entity.setCreateTime(new Timestamp(new Date().getTime())); //时间
        entity.setTypeId(typeIdStr.split(","));
        entity.setReason("");
        entity.setMessState(MessageEntity.MESSAGE_STATE_CHECK);
        return getResult(messageService.updateMessage(entity));
    }

    @PutMapping("/check/{messId}") //提交已审核的信息
    public Map<String,String> updateMessState(@PathVariable("messId")String messId,@RequestBody MessageEntity entity){
        log.info(messId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mId",messId);
        map.put("messState",entity.getMessState());
        if (entity.getMessState()== MessageEntity.MESSAGE_STATE_PUBLISH){ //发布
            map.put("publishDate",new Timestamp(new Date().getTime())); //发布时间
            map.put("authority",entity.getAuthority());
        }else if (entity.getMessState()==MessageEntity.MESSAGE_STATE_CANCEL){ //不发布
            map.put("reason",entity.getReason());

        }
        int row=messageService.updateMessageState(map);
        return getResult(row);
    }

    @PutMapping("/check/list") //批量提交已审核的信息（更新消息的状态为发布）
    public Map<String,String> updateMessStateList(@RequestParam("messIds")String messIdStr){
        log.info(messIdStr);
        int row=0;
        String[] messIds = messIdStr.split(",");
        if (messIds!=null && messIds.length>0){
            for (String messId:messIds){
                HashMap<String, Object> map = new HashMap<>();
                map.put("messState",MessageEntity.MESSAGE_STATE_PUBLISH);
                map.put("publishDate",new Timestamp(new Date().getTime()));
                map.put("mId",messId);
                map.put("authority",MessageEntity.MESSAGE_NOT_AUTHORITY);
                row =row+ messageService.updateMessageState(map);
            }
        }
        return getResult(row);
    }

    @PostMapping("/") //添加消息
    public Map<String,String> saveMessage(@ModelAttribute MessageEntity messageEntity, @RequestParam("typeIds")String typeIds){
        String remark = messageEntity.getRemark();
        int start1=remark.indexOf("--start--")+9; //标题的开始位置
        int end=remark.indexOf("--end--")-6;
        String title=remark.substring(start1,end);
        title=title.replace("&nbsp;","");
        int start2=remark.indexOf("++start++")+9; //摘要的开始位置
        String str=remark.substring(start2,start2+40);
        messageEntity.setTitle(title);
        messageEntity.setRemark(str);
        messageEntity.setCreateTime(new Timestamp(new Date().getTime())); //时间
        messageEntity.setMessState(MessageEntity.MESSAGE_STATE_CHECK); //状态
        messageEntity.setCommentNum(0); //评论量
        messageEntity.setViewNum(0); //阅读量
        messageEntity.setAuthority(MessageEntity.MESSAGE_NOT_AUTHORITY); //默认非官方发布
        messageEntity.setTypeId(typeIds.split(",")); //消息类型
        int row=messageService.saveMessage(messageEntity);
        return getResult(row);
    }

    @PostMapping("/collection/")  //添加收藏
    public Map<String,String> collectMessage(@RequestBody CollectMessageEntity entity){
        entity.setCollectTime(new Timestamp(new Date().getTime()));
        return getResult(messageService.collectMessage(entity));
    }




    @PostMapping("/comment")  //保存评论
    public Map<String,String> saveComment(@ModelAttribute CommentEntity commentEntity){
        commentEntity.setCreateDate(new Timestamp(new Date().getTime()));
        return getResult(messageService.saveComment(commentEntity));
    }

    
    @DeleteMapping("/comment/{Id}")//删除评论
    public Map<String,String> deleteCommentById(@PathVariable("Id")String Id,@RequestParam("mId") String mId){
        return getResult(messageService.deleteCommentById(mId,Id));
    }

    private RequestResultModel<MessageEntity> handleResult(PageParam pageParam,List<MessageEntity> messages){
        try{
            int start=(pageParam.getPage()-1)*pageParam.getLimit();
            int end=start+pageParam.getLimit();
            if (end>messages.size()){
                end=messages.size();
            }
            ArrayList<MessageEntity> entities = new ArrayList<>();
            for (int i=start;i<end;i++) {
                StringBuffer buffer = new StringBuffer();
                MessageEntity messageEntity = messages.get(i);
                List<MessageTypeEntity> types = messageEntity.getTypes();
                for (MessageTypeEntity typeEntity : types) {
                    if (buffer.length() > 0) {
                        buffer.append("、");
                    }
                    buffer.append(typeEntity.getTypeName());
                }
                LocalDateTime dateTime = messageEntity.getCreateTime().toLocalDateTime();
                String str = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                messageEntity.setType(buffer.toString());
                if (messageEntity.getMessState()== MessageEntity.MESSAGE_STATE_CHECK){
                    messageEntity.setState("未发布");
                }else if (messageEntity.getMessState()==MessageEntity.MESSAGE_STATE_PUBLISH){
                    messageEntity.setState("发布");
                }else if (messageEntity.getMessState()==MessageEntity.MESSAGE_STATE_CANCEL){
                    messageEntity.setState("驳回");
                }
                messageEntity.setTimeStr(str);
                entities.add(messageEntity);
            }
            return new RequestResultModel<>(messages.size(),"success",0,messages.subList(start,messages.size()));
        }catch (Exception e){
            return new RequestResultModel<>(-1,"获取数据失败",-1,null);
        }
    }

    //获取返回的结果
    private Map<String,String> getResult(int row){
        HashMap<String, String> map = new HashMap<>();
        if (row!=0){
            map.put("result",SUCCESS);
        }else{
            map.put("result",FAILURE);
        }
        return map;
    }



}
