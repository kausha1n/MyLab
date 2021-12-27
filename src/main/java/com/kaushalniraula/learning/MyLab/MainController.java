package com.kaushalniraula.learning.MyLab;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("greet")
    public String greet(){
        return "Hi!";
    }

    
}
