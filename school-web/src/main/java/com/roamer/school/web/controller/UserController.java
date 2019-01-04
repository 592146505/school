package com.roamer.school.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆控制器
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/29 14:58
 */
@RestController
public class UserController {

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("/index");
    }


}
