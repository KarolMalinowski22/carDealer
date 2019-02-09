package com.sda.carDealer.controller.utilities;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @ExceptionHandler
    public String errorHandler(){
        return "error";
    }
}
