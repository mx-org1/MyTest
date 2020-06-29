package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class UserController {

	@RequestMapping("/hello")

    public String hello() {
			System.out.println("hello");
           return "helloworld2";

    }
}
