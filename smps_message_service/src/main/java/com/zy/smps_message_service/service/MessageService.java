package com.zy.smps_message_service.service;

import com.zy.smps_message_service.entity.*;

import java.util.List;
import java.util.Map;

public interface MessageService {

    //查询未发布的信息
    List<MessageEntity> findNotPublishMess(PageParam pageParam);
    //查询全部信息
    List<MessageEntity> findAll(PageParam pageParam);
    //查看消息和其评论
    MessageEntity findOneById(String mId);
    //更新阅读量
    int updateViewNum(String messId);
    //保存信息
    int saveMessage(MessageEntity message);
    //更新状态
    int updateMessageState(Map<String,Object> map);
    //查询名下所写的消息
    List<MessageEntity> findMessageByAccount(PageParam pageParam);
    //删除消息
    int deleteMessage(List<String> mIds) throws Exception;
    //保存评论
    int saveComment(CommentEntity comment);
    //删除评论
    int deleteCommentById(String mId,String commentId);
    //查询收藏的信息
    List<CollectMessageEntity> findUserCollectMess(PageParam pageParam);
    //删除收藏
    int deleteUserCollection(List<String> messIds);
    //收藏消息
    int collectMessage(CollectMessageEntity entity);
    //查看所有的消息类型
    List<MessageTypeEntity> findAllMessType();
    //更新消息
    int updateMessage(MessageEntity message);

    List<MessageEntity> findCanceledMessage(PageParam pageParam);
}
