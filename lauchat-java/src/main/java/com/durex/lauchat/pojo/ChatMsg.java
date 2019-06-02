package com.durex.lauchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chat_msg")
public class ChatMsg {
    @Id
    private String id;

    @Column(name = "send_user_id")
    private String sendUserId;

    @Column(name = "accept_user_id")
    private String acceptUserId;

    private String msg;

    private String type;

    /**
     * 消息是否签收状态
1：签收
0：未签收

     */
    @Column(name = "sign_flag")
    private Integer signFlag;

    /**
     * 发送请求的事件
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return send_user_id
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * @param sendUserId
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * @return accept_user_id
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * @param acceptUserId
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取消息是否签收状态
1：签收
0：未签收

     *
     * @return sign_flag - 消息是否签收状态
1：签收
0：未签收

     */
    public Integer getSignFlag() {
        return signFlag;
    }

    /**
     * 设置消息是否签收状态
1：签收
0：未签收

     *
     * @param signFlag 消息是否签收状态
1：签收
0：未签收

     */
    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    /**
     * 获取发送请求的时间
     *
     * @return create_time - 发送请求的时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发送请求的时间
     *
     * @param createTime 发送请求的时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}