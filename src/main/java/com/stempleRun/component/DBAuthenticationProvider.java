package com.stempleRun.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.stempleRun.db.service.MemberDetailsServiceImpl;

@Component
public class DBAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MemberDetailsServiceImpl memberDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		
		String userId = (String) token.getPrincipal();
		String password = (String) token.getCredentials();
		
		UserDetails userDetails = null;
		
		if (!StringUtils.isEmpty(userId)) {
			userDetails = memberDetailsService.loadUserByUsername(userId);
		}
		if (ObjectUtils.isEmpty(userDetails)) {
			throw new UsernameNotFoundException("Invalid userId");
		}
		if (!password.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
