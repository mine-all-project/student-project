package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Device;
import com.service.IndexService;

@Controller
public class IndexController {
	@Autowired
	IndexService indexService;
	
	@RequestMapping("fileUpload")
	@ResponseBody
	public String fileUpload(MultipartFile file) {
		String path = indexService.fileUpload(file);
		return path;
	}
	
	@RequestMapping("addReg")
 	public String addReg(HttpServletRequest request,String username,String code,String phone) {
		if(indexService.addReg(request,username,code,phone)==1) {
			return "success";
		}else {
			request.setAttribute("result", "0");
		}
		return "fail";
	}
	
	@RequestMapping("loginOut")
	public void loginOut(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		session.removeAttribute("user");
		request.getRequestDispatcher("/").forward(request, response);
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,String username,String password) {
		if(indexService.checkUser(request,username,password)) {
			return "index";
		}
		return "unpassword";
	}
	
	@RequestMapping("regUser")
	public String regUser(HttpServletRequest request,String username,String password) {
		return "reg";
	}
	
	@RequestMapping("updateDevice")
	public String updateDevice(HttpServletRequest request) {
		List<Device> devices = indexService.getDeviceList();
		request.setAttribute("devices", devices);
		return "updateDevice";
	}
	
	@RequestMapping("needDevice")
	public String needDevice(HttpServletRequest request) {
		List<Device> devices = indexService.getDeviceList();
		request.setAttribute("devices", devices);
		return "needDevice";
	}
	
	@RequestMapping("addNeed")
	public String addNeed(HttpServletRequest request) {
		List<Device> devices = indexService.getDeviceList();
		request.setAttribute("devices", devices);
		return "addNeed";
	}
	
	@RequestMapping("saveNeed")
	public void saveNeed(HttpServletRequest request,HttpServletResponse response,Device device) throws ServletException, IOException {
		Integer status = indexService.saveNeed(device);
		request.setAttribute("result", status);
		request.getRequestDispatcher("needDevice").forward(request, response);
	}
	
}
