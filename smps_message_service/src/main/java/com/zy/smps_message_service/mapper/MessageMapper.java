package com.zy.smps_message_service.mapper;

import com.zy.smps_message_service.entity.*;
import org.apache.ibatis.annotations.Mapper;
import sun.security.krb5.internal.PAData;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    List<MessageEntity> findNotPublishMess(PageParam pageParam);

    List<MessageEntity> findNotPublishMessByType(PageParam pageParam);
    //查询全部信息
    List<MessageEntity> findAll(PageParam pageParam);

    //更新浏览量
    int updateMessageViewNum(Map<String,Object> map);
    //更新评论量
    int updateMessageCommentNum(Map<String,Object> map);
    //获取原有浏览量
    int getViewNum(String mId);
    //消获取原有评论量
    int getCommentNum(String mId);
    //查看消息和其评论
    MessageEntity findOneById(String mId);
    //保存信息
    int saveMessage(MessageEntity message);
    //更新状态
    int updateMessageState(Map<String,Object> map);
    //查询名下所写的消息
    List<MessageEntity> findMessageByAccount(PageParam pageParam);
    //更新消息
    int updateMessage(MessageEntity message);
    //删除消息
    int deleteMessage(String mId);
    //删除消息类型
    int deleteMessageType(String mId);
    //保存消息类型
    int saveMessageType(Map<String,Object> map);
    //保存评论
    int saveComment(CommentEntity comment);
    //删除评论
    int deleteCommentById(String commentId);
    //删除消息下的所有评论
    int deleteCommentBymId(String mId);
    //查询用户的收藏
    List<CollectMessageEntity> findUserCollectMess(PageParam pageParam);
    //删除用户的收藏
    int deleteUserCollection(String messId);
    //消息类型
    List<MessageTypeEntity> findAllMessType();
    //保存收藏
    int collectMessage(CollectMessageEntity entity);

    List<MessageEntity> findCanceledMessage(PageParam pageParam);
}
