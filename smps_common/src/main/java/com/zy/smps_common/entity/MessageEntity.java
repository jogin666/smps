package com.zy.smps_common.entity;

import com.zy.smps_common.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MessageEntity {

    private String mId; //消息Id
    private String[] typeId;
    private String title; //标题
    private String account; //账号
    private String context; //内容
    private LocalDateTime createTime; //创建时间
    private LocalDateTime publishDate; //发布日期
    private LocalDateTime isPublish; //是否发布
    private BigInteger viewNum;  //浏览量
    private BigInteger commentNum; //评论量
    private int authority; //是否是学校发布
    private List<CommentEntity> comments;


}
