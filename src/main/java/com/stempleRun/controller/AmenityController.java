package com.stempleRun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stempleRun.db.dto.Amenity;
import com.stempleRun.db.dto.Cul_amenity;
import com.stempleRun.db.dto.Culture;
import com.stempleRun.db.dto.Story;
import com.stempleRun.db.service.AmenityService;
import com.stempleRun.db.service.Cul_amenityService;
import com.stempleRun.db.service.CultureService;

@Controller
@RequestMapping("/amenity")
public class AmenityController {

	@Autowired
	AmenityService amenityservice;
	@Autowired
	CultureService cultureservice;
	@Autowired
	Cul_amenityService cul_amenityservice;

	// 위에처럼 어떤 서비스인지 알 수 있도록 이름 만들기
	// 밑에는 지도에 마커 생성할 수 있도록 설정
	@RequestMapping(value = "/form")
	public String register(Model model) {
		try {

			model.addAttribute("cultureList", cultureservice.getCultures());
			model.addAttribute("amenityList", amenityservice.getAmenitys());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/amenity/a_register";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void a_save(Amenity a, Culture c) {

		String a_name = a.getA_name();
		String a_latitude = a.getA_latitude();
		String a_longitude = a.getA_longitude();
		
		amenityservice.register(a);
		
//		System.out.println(c.getC_num());
		// 방금 저장된 스토리의 c_num값 가져오기
		
		Amenity amenity = amenityservice.findNum(a_latitude,a_longitude);
		
		

		Cul_amenity ca = new Cul_amenity();
		ca.setC_num(c.getC_num());
		ca.setA_num(amenity.getA_num());
		cul_amenityservice.register(ca);
		
		System.out.println("name : " + a_name + "  a_latitude : " + a_latitude + "  a_longitude : " + a_longitude);
	}
}
