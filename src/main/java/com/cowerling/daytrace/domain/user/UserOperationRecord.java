package com.cowerling.daytrace.domain.user;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("userOperationRecord")
public class UserOperationRecord {
    private Long id;
    private Long userId;
    private UserOperation userOperation;
    private Date time;
    private String message;

    public UserOperationRecord() {
    }

    public UserOperationRecord(Long userId, UserOperation userOperation, Date time) {
        this.userId = userId;
        this.userOperation = userOperation;
        this.time = time;
    }

    public UserOperationRecord(Long userId, UserOperation userOperation, Date time, String message) {
        this.userId = userId;
        this.userOperation = userOperation;
        this.time = time;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserOperation getUserOperation() {
        return userOperation;
    }

    public void setUserOperation(UserOperation userOperation) {
        this.userOperation = userOperation;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
