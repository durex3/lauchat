package com.durex.lauchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_group_msg_to_user")
public class UserGroupMsgToUser {
    @Id
    private String id;

    @Column(name = "accept_user_id")
    private String acceptUserId;

    @Column(name = "user_group_msg_id")
    private String userGroupMsgId;

    /**
     * 消息是否签收状态
1：签收
0：未签收

     */
    @Column(name = "sign_flag")
    private Integer signFlag;

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
     * @return user_group_msg_id
     */
    public String getUserGroupMsgId() {
        return userGroupMsgId;
    }

    /**
     * @param userGroupMsgId
     */
    public void setUserGroupMsgId(String userGroupMsgId) {
        this.userGroupMsgId = userGroupMsgId;
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
}