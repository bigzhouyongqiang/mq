package com.zhouyq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description TODO
 * @Date 2020/6/21 19:02
 * @Author zhouyq
 */
public class Consumer {

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
         * 创建消息消费者
         * 1. 要监听的消息队列名称
         * 2. 消息消费应答模式
         * 3. 读取消息，并处理
         */
        channel.basicConsume("test_queue", true, new DefaultConsumer(channel){
            /**
             *
             * @param consumerTag
             * @param envelope      接受消息的封装
             * @param properties    额外参数
             * @param body          消息内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到的消息");
                System.out.println("consumerTag:\t" + consumerTag);
                System.out.println("envelope:\t" + envelope);
                System.out.println("body:\t" + new String(body, "utf-8"));
            }
        });

    }

}
