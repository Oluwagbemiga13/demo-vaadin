package com.example.demo;

import com.example.demo.controller.HelloWorld;
import org.springframework.stereotype.Controller;


@Controller
public class HelloWorldImpl implements HelloWorld {

    public String sayHello() {
        return "Hello, World!";
    }

}
