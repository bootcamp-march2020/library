package com.tw.bootcamp.librarysystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @RequestMapping("/sample")
    public String sampleEndpoint() {
        return "Welcome"
    }

}
