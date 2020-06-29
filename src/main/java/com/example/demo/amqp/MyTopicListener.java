
package com.example.demo.amqp;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
/** 

* @author 作者 Your-Name: mx

* @version 创建时间：2020年1月10日 下午2:00:10 

* 类说明 

*/

@Component
public class MyTopicListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("province-news-queue"),
            exchange = @Exchange(value = "news-exchange", type = ExchangeTypes.TOPIC),
            key = "province.#"))
    @RabbitHandler
    public void provinceNews(String msg) {
        System.out.println("来自省TV的消息:" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("city-news-queue"),
            exchange = @Exchange(value = "news-exchange", type = ExchangeTypes.TOPIC),
            key = "province.city.#"))
    @RabbitHandler
    public void cityNews(String msg) {
        System.out.println("来自市TV的消息:" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("street-news-queue"),
            exchange = @Exchange(value = "news-exchange", type = ExchangeTypes.TOPIC),
            key = "province.city.street.*"))
    @RabbitHandler
    public void streetNews(String msg) {
        System.out.println("来自街区TV的消息:" + msg);
    }

}
