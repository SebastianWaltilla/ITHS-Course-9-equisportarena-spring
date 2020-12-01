package com.usermodule.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class WebController  {

    @RequestMapping
    public String index(){
        return "index";
    }
}
