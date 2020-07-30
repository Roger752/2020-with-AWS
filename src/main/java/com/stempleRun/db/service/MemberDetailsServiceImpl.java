package com.stempleRun.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.stempleRun.db.dto.EntityMember;
import com.stempleRun.db.dto.Member;
import com.stempleRun.db.repository.MemberJpaRepository;

@Component
public class MemberDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MemberJpaRepository memberRepository;
	
	@Autowired
	private MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("UserDetailsServiceImpl: " + username);
		
		EntityMember Entitymember = memberRepository.findByUserId(username); //memberService.getMember(username);
		if (ObjectUtils.isEmpty(Entitymember)) {
			throw new UsernameNotFoundException("Invalid username");
		}
		// 여기에 .name(Entitymember.getM_name()) 하면 컨트롤러쪽에서 UserDetails.getName 가능한듯
		UserDetails user = 
				User
				.withUsername(Entitymember.getM_id())
					.password(Entitymember.getM_pw())
					.username(Entitymember.getM_id())
					.authorities(AuthorityUtils.createAuthorityList("USER"))
					.roles("USER")
				.build();
		return user;
	}

}
