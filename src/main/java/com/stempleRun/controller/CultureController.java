package com.stempleRun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.stempleRun.db.dto.Culture;
import com.stempleRun.db.service.CultureService;

@Controller
@RequestMapping("/culture")
public class CultureController {

	@Autowired
	CultureService service;
	
	@RequestMapping(value="/reg") //, method = RequestMethod.POST
	public String register(Model model) {
		
		try {
			
			model.addAttribute("cultureList", service.getCultures());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/culture/c_register";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public void c_save(Culture c) {
		
		String name = c.getC_name();
		String c_latitude = c.getC_latitude();
		String c_longitude = c.getC_longitude();
		
		service.register(c);
		
		System.out.println("name : " + name + "  c_latitude : " + c_latitude + "  c_longitude : " + c_longitude);
		
	}
	
}
