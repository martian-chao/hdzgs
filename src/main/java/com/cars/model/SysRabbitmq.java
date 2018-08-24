package com.cars.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**rabbitmq监控类
 * Created by liuyanchao
 * on 2018/4/15.
 */
@Component
public class SysRabbitmq {
    /**
     * mq的ip地址
     */
    @Value("${spring.rabbitmq.host}")
    private String ip;
    /**
     * mq的端口
     */
    @Value("${spring.rabbitmq.port}")
    private int port;//port
    /**
     * mq的虚拟host
     */
    @Value("${spring.rabbitmq.virtualHost}")
    private String vhost;
    /**
     * mq的用户名
     */
    @Value("${spring.rabbitmq.username}")
    private String username;
    /**
     * mq的密码
     */
    @Value("${spring.rabbitmq.password}")
    private String password;//密码
    /**
     * hd_hdhz_queue队列未消费的消息数
     */
    private long messageCount1;
    /**
     * hd_lcrqhz_queue队列未消费的消息数
     */
    private long messageCount2;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

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

    public long getMessageCount1() {
        return messageCount1;
    }

    public void setMessageCount1(long messageCount1) {
        this.messageCount1 = messageCount1;
    }

    public long getMessageCount2() {
        return messageCount2;
    }

    public void setMessageCount2(long messageCount2) {
        this.messageCount2 = messageCount2;
    }

    @Override
    public String toString() {
        return "SysRabbitmq{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", vhost='" + vhost + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", messageCount1=" + messageCount1 +
                ", messageCount2=" + messageCount2 +
                '}';
    }
}
