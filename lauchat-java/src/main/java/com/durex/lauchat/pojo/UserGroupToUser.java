package com.durex.lauchat.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_group_to_user")
public class UserGroupToUser {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_group_id")
    private String userGroupId;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
}