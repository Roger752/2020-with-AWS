package com.stempleRun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stempleRun.db.dto.EntityMember;
import com.stempleRun.db.repository.MemberJpaRepository;
import com.stempleRun.db.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberJpaRepository memberRepository;

	@RequestMapping("/")
	public String userList(Model model) {
		try {
			model.addAttribute("memberList", memberService.getMembers());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}

	@RequestMapping("/jpalist")
	public String jpaUserList(Model model) {
		model.addAttribute("memberList", memberRepository.findAll());
		return "memberlist";
	}

	/* 회원가입 */
	@GetMapping("/signup") // /member/signup
	public String signup() {

		return "signup"; // /member/signup
	}
	
	@ResponseBody
	@GetMapping("/idcheck/{m_id}")
	public int idcheck(@PathVariable String m_id) {
		int check = 0;
		
		EntityMember member = memberRepository.findByUserId(m_id);
		System.out.println(member);
		if(member == null) {
			check = 1;
		}
		
		return check; // 1 일 경우 가입 가능
	}
	

	@PostMapping("/signupProc") // /member/signupProc
	public String SignupProc(EntityMember entityMember) {		
		entityMember.setType_num(1);
		memberRepository.save(entityMember);

		return "redirect:/login";
	}
	
	// 성기진
	@RequestMapping("/getM_num/{id}")
	@ResponseBody
	public int getM_num(@PathVariable String id) throws Exception {
		int m_num = memberService.getM_num(id);
		return m_num;
	}

	
}
