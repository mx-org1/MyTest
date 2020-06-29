
package com.example.demo.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** 

* @author 作者 Your-Name: mx

* @version 创建时间：2020年1月10日 下午1:59:44 

* 类说明 https://www.jianshu.com/p/c0cba2546900

*/
@Controller
public class TopicProducer {
		@Autowired
	    private RabbitTemplate rabbitTemplate;
		
		@ResponseBody
		@RequestMapping("/produce")
	    private void send() {
			System.out.println("produce");
	        //(交换机,routingKey,消息内容),routingKey随意
	        rabbitTemplate.convertAndSend("news-exchange","province.city.street.shop","有人中了大奖");
	    }
}
