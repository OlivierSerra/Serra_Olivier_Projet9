package com.openclassroom.frontService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * affiche le dashboard - étape peut être utile dans une versin ultérieure
 * - consultation remboursement mutuelle ou Secu
 * */
@Controller
public class FrontController {
    @GetMapping("/")
    public String home(){
        return "dashboard";
    }


}
