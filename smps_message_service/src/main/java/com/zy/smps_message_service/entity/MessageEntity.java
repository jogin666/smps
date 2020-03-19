package com.zy.smps_message_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

//@Data
//@AllArgsConstructor
public class MessageEntity implements Serializable {

    private String messId; //消息Id
    private String userId; //userId
    private String title; //标题
    private String content; //内容
    private Timestamp publishDate; //发布日期
    private Integer messState; //是否发布
    private Integer viewNum;  //浏览量
    private Integer commentNum; //评论量
    private Timestamp createTime; //创建时间
    private Integer authority; //是否是学校发布
    private String account; //账号
    private String remark;  //摘要
    private String reason;  //驳回原因

    private List<MessageTypeEntity> types;

    //辅助属性
    private String name;
    private String type;
    private String state;
    private String timeStr;
    private String url;

    public final static int MESSAGE_STATE_PUBLISH=1;
    public final static int MESSAGE_STATE_CHECK=0;
    public final static int MESSAGE_STATE_CANCEL=-1;

    public final static int MESSAGE_IS_AUTHORITY=1;
    public final static int MESSAGE_NOT_AUTHORITY=0;

    private List<CommentEntity> comments;
    private String[] typeId;

    public String getMessId() {
        return messId;
    }

    public void setMessId(String messId) {
        this.messId = messId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getMessState() {
        return messState;
    }

    public void setMessState(Integer messState) {
        this.messState = messState;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public String[] getTypeId() {
        return typeId;
    }

    public void setTypeId(String[] typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MessageTypeEntity> getTypes() {
        return types;
    }

    public void setTypes(List<MessageTypeEntity> types) {
        this.types = types;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
