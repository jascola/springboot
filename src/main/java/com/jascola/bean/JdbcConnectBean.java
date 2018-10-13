package com.jascola.bean;

import org.springframework.beans.factory.annotation.Value;

public class JdbcConnectBean {
    //数据库驱动
    @Value(value ="${jdbc.driver}")
    private String driver;
    //数据库连接
    @Value(value ="${jdbc.url}")
    private String url;
    //用户名
    @Value(value = "${jdbc.username}")
    private String username;
    //密码
    @Value(value ="${jdbc.password}")
    private String password;
    //初始连接数
    @Value(value ="${jdbc.initialSize}")
    private int initialSize;
    //最大连接数
    @Value(value ="${jdbc.maxTotal}")
    private int maxActive;
    //最大空闲连接数
    @Value(value ="${jdbc.maxIdle}")
    private int maxIdle;
    //最小空闲连接数
    @Value(value ="${jdbc.minIdle}")
    private int minIdle;
    //定义最长等待时间
    @Value(value ="${jdbc.maxWait}")
    private int maxWait;

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }
}
