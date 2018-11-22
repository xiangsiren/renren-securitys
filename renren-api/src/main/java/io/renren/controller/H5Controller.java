package io.renren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "h5")
public class H5Controller {

    @RequestMapping("/sc.html")
    public String socket(Model model){

        model.addAttribute("name","fdsfsd");

        return "websocket.html";
    }

    @RequestMapping("ceshi")
    public String ss(){
        return "dfssdf";
    }
}
