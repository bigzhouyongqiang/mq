package com.zhouyq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description TODO
 * @Date 2020/6/21 18:43
 * @Author zhouyq
 */
public class Proucer {

    public static void main(String[] args) throws Exception{

        // 创建连接工厂对象
        ConnectionFactory factory = new ConnectionFactory();

        // 设置rabbitmq的主机地址和端口
        factory.setHost("192.168.56.2");
        factory.setPort(5672);

        // 设置虚拟机
        factory.setVirtualHost("/test");

        // 设置用户
        factory.setUsername("admin");

        // 设置密码
        factory.setPassword("admin");

        // 创建连接
        Connection connection = factory.newConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        /**
         *  声明队列
         *  1. 队列名称（类似数据库的表）
         *  2. 消息是否持久化
         *  3. 当前消息队列是否属于当前连接对象独有
         *  4. 消息使用完毕后是否删除
         *  5. 附加参数
         */
        channel.queueDeclare("test_queue", false, false, true, null);

        //
        /**
         * 创建消息
         * 1. 消息要发送的交换机对象，如果填写null，则表示使用AMQP default
         * 2. 当前消息路由地址，简单消息模式，路由地址可以直接写成队列地址
         * 3. 附加数据，可以不写
         * 4. 消息内容
         */
        channel.basicPublish("", "test_queue", null, "测试消息".getBytes("utf-8"));

        // 关闭资源
        channel.close();
        connection.close();

    }

}
