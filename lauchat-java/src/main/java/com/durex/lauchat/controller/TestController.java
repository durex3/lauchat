package com.durex.lauchat.controller;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private Sid sid;

    @GetMapping("/test")
    public String test() {
        return sid.nextShort();
    }
}
