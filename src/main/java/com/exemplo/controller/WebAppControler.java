package com.exemplo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebAppControler {

    @GetMapping("/index")
    public ModelAndView index(@RequestParam("usuario") String usuario) {
        // ações com o argumento 'usuario'
        ModelAndView index = new ModelAndView("index");
        index.addObject("usuario",usuario);
        return index;
    }
}
