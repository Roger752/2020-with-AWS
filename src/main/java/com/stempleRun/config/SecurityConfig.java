package com.stempleRun.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
      // post 메소드가 포함된 컨트롤러만 여기다가 컨트롤러의 requestMapping + **을 붙여주고 .permitAll()을 해준다.
         .authorizeRequests()
            .antMatchers("/").permitAll() // 모든 권한을 줌.=로그인 필요 없음.
            .antMatchers("/test/**").permitAll()
            .antMatchers("/member/**").permitAll()
            .antMatchers("/files/**").permitAll()//hasRole("user") // user 권한만 접근 가능.
            .antMatchers("/logout").permitAll()
            .antMatchers("/storymake/aaa").permitAll()
            .antMatchers("/storymake/bbb").permitAll()
            .antMatchers("/storymake/storyStart").permitAll()
            .antMatchers("/app_login/login").permitAll()
            .anyRequest()
            .authenticated() // 로그인 체크함.
            .and()
            .formLogin()
            .loginPage("/login") // 이 줄을 지우면 스프링이 제공하는 폼이 출력됨.
            .defaultSuccessUrl("/loginProc")
            .permitAll()
            .successHandler(successHandler())
            .and()
         .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .and()
         .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
      
      // 시큐리티가 모든 post요청을 막기 때문에 이렇게 적어줘서 막는걸 피한다.
      http.csrf().ignoringAntMatchers("/culture/save");
      //http.csrf().ignoringAntMatchers("/storymake/manage_fileSave");
      http.csrf().ignoringAntMatchers("/storymake/**");
      http.csrf().ignoringAntMatchers("/bingo/**");
      http.csrf().ignoringAntMatchers("/files");
      http.csrf().ignoringAntMatchers("/gallery/**");
      http.csrf().ignoringAntMatchers("/review/**");
      http.csrf().ignoringAntMatchers("/insertProc");
      http.csrf().ignoringAntMatchers("/updateProc");
      http.csrf().ignoringAntMatchers("/amenity/**");
      http.csrf().ignoringAntMatchers("/comment/**");
      http.csrf().ignoringAntMatchers("/app_login/**");

   }
   
   // css랑 이미지 같은거 무조건 통과시켜주기 위한거
      @Override
      public void configure(WebSecurity web) {
         web.ignoring().antMatchers("/img/**", "/css/**", "/uploads/**", "/js/**");
      }

   public AuthenticationSuccessHandler successHandler() {
      SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
      return handler;
   }
}
   
