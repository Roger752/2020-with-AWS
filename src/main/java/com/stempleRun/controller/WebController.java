package com.stempleRun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stempleRun.db.dto.EntityMember;
import com.stempleRun.db.dto.Member;
import com.stempleRun.db.mapper.MemberMapper;
import com.stempleRun.db.service.MemberDetailsServiceImpl;

@Controller
public class WebController {

	@Autowired
	MemberMapper memberMapper;

	@RequestMapping("/")
	public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		// System.out.println(userDetails.getUsername());

		return "index";
	}

	@RequestMapping("/test")
	public String test(Model model) {
		String[] toStudy = { "java", "python", "kotlin", "swift" };
		Member member = new Member("u1", "1234", "관리자", "u1@mail.com");
		model.addAttribute("member", member);
		model.addAttribute("admin", false);
		model.addAttribute("toStudy", toStudy);
		return "test";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/loginProc")
	public String loginProc() {
		return "layout";
	}
	

}
