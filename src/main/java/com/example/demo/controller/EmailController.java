package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 这个没开发好还
 * @author JD-DZ327
 *
 */
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private static JavaMailSender mailSender;

	@RequestMapping("/send")
	public void toSend(@RequestParam String to ,@RequestParam String subject,@RequestParam String content ) {
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("741200631@qq.com");
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(content);
	        try{

	            mailSender.send(message);
	            System.out.println("success");
	        }catch (Exception e){
	            System.out.println("fail"+e);
	        }
	}

	/*public static void main(String[] args) {
		send("1556416261@qq.com","test html mail","hello, this is html mail！");
	}*/
	public static void send(String to,String subject,String content) {
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("741200631@qq.com");
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(content);
	        try{

	            mailSender.send(message);
	            System.out.println("success");
	        }catch (Exception e){
	            System.out.println("fail"+e);
	        }
	}
}
