package com.durex.lauchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "friends_request")
public class FriendsRequest {
    @Id
    private String id;

    @Column(name = "send_user_id")
    private String sendUserId;

    @Column(name = "accept_user_id")
    private String acceptUserId;

    /**
     * 发送请求的事件
     */
    @Column(name = "request_date_time")
    private Date requestDateTime;

    // 0 添加中 1同意添加
    @Column(name = "status")
    private Integer status;

    @Column(name = "is_ready")
    private Integer isReady;
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
     * 获取发送请求的事件
     *
     * @return request_date_time - 发送请求的事件
     */
    public Date getRequestDateTime() {
        return requestDateTime;
    }

    /**
     * 设置发送请求的事件
     *
     * @param requestDateTime 发送请求的事件
     */
    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return is_ready
     */
    public Integer getIsReady() {
        return isReady;
    }

    /**
     * @param isReady
     */
    public void setIsReady(Integer isReady) {
        this.isReady = isReady;
    }
}