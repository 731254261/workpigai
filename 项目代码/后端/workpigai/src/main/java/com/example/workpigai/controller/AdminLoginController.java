package com.example.workpigai.controller;

import com.example.workpigai.model.User;
import com.example.workpigai.result.Result;
import com.example.workpigai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

/**
 * 控制层  转发调用
 */

@Controller
public class AdminLoginController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/admin_login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String account = requestUser.getAccount();
        account = HtmlUtils.htmlEscape(account);

        // 返回码：100对应管理员，200对应教师，300对应学生，400是错误码
        User user = userService.get(account, requestUser.getPassword());
        if (null == user) {
            return new Result(400);
        } else if (user.getType() == 1){
            return new Result(100);
        } else {
            return new Result(400);
        }
    }
}
