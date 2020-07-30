package com.stempleRun.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stempleRun.db.dto.Member;
import com.stempleRun.db.service.MemberService;

@Controller
@RequestMapping("/app_login")
public class App_login {

	@Autowired
	MemberService memberService;
	
	@RequestMapping(value="login")
	@ResponseBody
	public List<String> login(HttpServletRequest request) throws Exception  {
		
		ArrayList<String> data = new ArrayList<String>();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		System.out.println("id : " + request.getParameter("id"));
		System.out.println("pw : " + request.getParameter("pw"));
		
		// 지금은 앱으로 전달할 정보가 아이디, 비밀번호, 이름뿐 => 나중에 마이페이지 추가시 데이터 더 들고와야함
		if(memberService.app_getMember(id) != null) {
			Member m = memberService.app_getMember(id);
			System.out.println(m.getM_id());
			System.out.println(m.getM_pw());
			if(m.getM_pw().equals(pw) ) {
				data.add(m.getM_name() + "님 환영합니다.");
				data.add(m.getM_id());
				data.add(m.getM_pw());
				data.add(m.getM_email());
			}
			else {
				System.out.println("log : 비밀번호가 틀립니다.");
				data.add("비밀번호가 틀립니다.");
			}
		}
		else {
			System.out.println("log : 아이디가 존재하지 않습니다.");
			data.add("아이디가 존재하지 않습니다.");
		}
		
		for(int i=0; i<data.size(); i++) {
			System.out.println("data : " + data.get(i));
		}
		return data;
	}
	
}
