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
@Table(name = "user_group_msg")
public class UserGroupMsg {
    @Id
    private String id;

    @Column(name = "user_group_id")
    private String userGroupId;

    @Column(name = "send_user_id")
    private String sendUserId;

    private String msg;

    private String type;

    /**
     * 时间
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
     * @return user_group_id
     */
    public String getUserGroupId() {
        return userGroupId;
    }

    /**
     * @param userGroupId
     */
    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
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
     * 获取时间
     *
     * @return create_time - 时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置时间
     *
     * @param createTime 时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}