package org.example.tuole.controller;

import org.example.tuole.dto.ResponseDTO;
import org.example.tuole.entity.SysUser;
import org.example.tuole.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController extends BaseController {
    final
    IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private static final String PAGE = "page/";

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
        return "/index";
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "/index";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/registry")
    public String registry() {
        return "registry";
    }

    @RequestMapping("/logOut")
    public String logOut() {
        indexService.logOut();
        return "/index";
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResponseDTO getUserInfo() {
        SysUser user = indexService.getUserInfo();
        return ResponseDTO.returnSuccess("操作成功",user);
    }

}
