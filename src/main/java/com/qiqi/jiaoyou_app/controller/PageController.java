package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.support.DunUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class PageController {

    @RequestMapping("/invitation")
    public String showIndex(String code,Model model){
        model.addAttribute("code",code);
        return "/index";
    }

    @RequestMapping("/jiaoyou_app/videoCallback")
    @ResponseBody
    public String videoCallback(String code){

	  String s = DunUtils.detectionVideoResultsDun(code);
	  return s;
    }
}
