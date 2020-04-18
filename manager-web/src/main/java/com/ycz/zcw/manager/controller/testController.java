package com.ycz.zcw.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.service.UserService;

@Controller
@RequestMapping("/test/")
public class testController {
    
    @Autowired
    private UserService uService;
    
    @RequestMapping("index")
    public ModelAndView index(
            @RequestParam(name="id",required = false,defaultValue = "1")Integer id) {
        ModelAndView mav = new ModelAndView("index");
        User user =  uService.queryUser(id);
        mav.addObject("user",user);
        return mav;
    }
    

}
