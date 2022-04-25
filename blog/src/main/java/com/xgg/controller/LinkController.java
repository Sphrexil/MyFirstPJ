package com.xgg.controller;


import com.xgg.domain.ResponseResult;
import com.xgg.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkservice;
        @GetMapping("/getAllLink")
        public ResponseResult getAllLink(){


                return linkservice.getAllLink();
        }

}
