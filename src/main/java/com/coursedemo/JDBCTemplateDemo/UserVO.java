package com.coursedemo.JDBCTemplateDemo;

public class UserVO {

    private String userId;

    private String userName;

    private int accountValue;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAccountValue() {
        return accountValue;
    }

    public void setAccountValue(int accountValue) {
        this.accountValue = accountValue;
    }

    @Override
    public String toString() {

        return userId + " | " + userName + " | " + accountValue;
    }
}
