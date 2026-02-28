package com.yahiaprince.store.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;

@Hidden
@RestController
@RequestMapping("/")
@Tag(name = "Home")
public class HomeController {

    @RequestMapping
    public String index() {
        return "Hello World!";
    }
}