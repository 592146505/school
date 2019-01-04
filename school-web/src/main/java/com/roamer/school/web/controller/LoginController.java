package com.roamer.school.web.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 登陆控制器
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/29 14:58
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request) throws Exception {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if (!Objects.isNull(exceptionClassName)) {
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                request.setAttribute("errorMsg", "用户名不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                request.setAttribute("errorMsg", "用户名/密码不正确");
            } else {
                // 最终在异常处理器生成未知错误
                throw new Exception();
            }
            // 由于使用的是 html 文件，它并不支持响应头带有 post 的应答包
            // 所以登陆失败必须使用转发
            return "redirect:/login";
        }
        // 此处返回登陆页面
        return "/login";
    }

    @RequestMapping("/")
    private String rootPage() {
        return "redirect:/index";
    }

}
