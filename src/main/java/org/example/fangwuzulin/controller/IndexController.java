package org.example.fangwuzulin.controller;

import org.example.fangwuzulin.config.groups.IsLogin;
import org.example.fangwuzulin.config.groups.IsRegister;
import org.example.fangwuzulin.dto.ResponseDTO;
import org.example.fangwuzulin.entity.SysUser;
import org.example.fangwuzulin.form.UserForm;
import org.example.fangwuzulin.service.IndexService;
import org.example.fangwuzulin.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class IndexController extends BaseController {
    private final IndexService indexService;
    private final SysService sysService;

    public IndexController(IndexService indexService, SysService sysService) {
        this.indexService = indexService;
        this.sysService = sysService;
    }

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private static final String PAGE = "portal/";

    @GetMapping("/page/{pageName}")
    public String page(@PathVariable("pageName") String pageName) {
        logger.info("收到请求->进入页面[{}]", PAGE + pageName);
        return PAGE + pageName;
    }

    @GetMapping("/page/{dir}/{pageName}")
    public String dirPage(@PathVariable("dir") String dir, @PathVariable("pageName") String pageName) {
        logger.info("收到请求->进入二级页面[{}]", PAGE + dir + "/" + pageName);
        return PAGE + dir + "/" + pageName;
    }

    @RequestMapping("/")
    public String index() {
        return toIndex();
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/registry")
    public String registry() {
        return "registry";
    }

    @PostMapping("/loginCheck")
    @ResponseBody
    public ResponseDTO loginCheck(@RequestBody UserForm form) {
        logger.info("收到请求->用户登陆验证:[{}]", form);
        super.validator(form, IsLogin.class);
        ResponseDTO responseDTO = sysService.login(form);
        logger.info("登录验证结束->用户信息:[{}]", responseDTO);
        return responseDTO;
    }

    @PostMapping("/registryCheck")
    @ResponseBody
    public ResponseDTO registryCheck(@RequestBody UserForm form) {
        logger.info("收到请求->用户注册[{}]", form);
        super.validator(form, IsRegister.class);
        SysUser sysUser = sysService.registry(form);
        logger.info("注册验证结束->用户信息:[{}]", sysUser);
        return ResponseDTO.returnSuccess("注册成功，即将返回登录页面", sysUser);
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResponseDTO getUserInfo() {
        logger.info("收到请求->获取用户信息");
        SysUser sysUser = indexService.getUserInfo();
        logger.info("返回结果->获取用户信息结束:[{}]", sysUser);
        return ResponseDTO.returnSuccess("操作成功", sysUser);
    }

//
//    @RequestMapping("/mineInfo")
//    public String mineInfo() {
//        return "/mineInfo";
//    }
//
    @RequestMapping("/loginOut")
    public String loginOut() {
        logger.info("收到请求->退出登录");
        indexService.logout();
        logger.info("返回结果->退出登录结束");
        return "index-bak";
    }
}
