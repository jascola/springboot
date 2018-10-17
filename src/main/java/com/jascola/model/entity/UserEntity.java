package com.jascola.model.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String username;
    /**
     * 密码（MD5加密）
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
