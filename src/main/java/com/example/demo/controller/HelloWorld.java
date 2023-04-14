package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "/organ")
public interface HelloWorld {

    @GetMapping()
    @ResponseBody
    String sayHello();
}
