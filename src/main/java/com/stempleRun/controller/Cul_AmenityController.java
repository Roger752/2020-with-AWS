package com.stempleRun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stempleRun.db.service.Cul_amenityService;

@Controller
@RequestMapping("/amenity")
public class Cul_AmenityController {
	
//	@Autowired
//	Cul_amenityService cul_amenityservice;
//	
//	@RequestMapping(value = "/form")
//	public String register(Model model) {
//		try {
//			model.addAttribute("cul_amenityList", cul_amenityservice.getCul_amenitys());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "/amenity/a_register";
//		
//	}
}
