package com.hrms.controller;

import com.hrms.util.JsonMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author GenshenWang.nomico
 * @date 2018/3/9.
 */
@Controller
@RequestMapping(value = "/hrms")
public class LoginController {

    /**
     * ��¼����ת����¼ҳ��
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    /**
     * �Ե�¼ҳ��������û������������򵥵��ж�
     * @param request
     * @return
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    @ResponseBody
    public JsonMsg dologin(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + password);
        if (!"admin1234".equals(username + password)){
            return JsonMsg.fail().addInfo("login_error", "�����˺��û��������벻ƥ�䣬���������룡");
        }
        return JsonMsg.success();
    }

    /**
     * ��ת����ҳ��
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(){
        return "main";
    }

    /**
     * �˳���¼������ҳ����ת����¼ҳ��
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        return "login";
    }






}