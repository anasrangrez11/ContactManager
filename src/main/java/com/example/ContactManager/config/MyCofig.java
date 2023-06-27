package com.example.ContactManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyCofig{

	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailServiceImpl();
	}
	
   @Bean
   BCryptPasswordEncoder passwordEncoder() {
	   
	   return new BCryptPasswordEncoder();
   }
	
   @Bean 
   public DaoAuthenticationProvider authenticationProvider() {
	   DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
	   dap.setUserDetailsService(this.getUserDetailService());
	   dap.setPasswordEncoder(passwordEncoder());
	   
	   return dap;
	   
   }
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
         .authorizeRequests()
         .requestMatchers("/admin/**")
         .hasAnyRole("ADMIN")
         .requestMatchers("/user/**")
         .hasAnyRole("USER")
         .requestMatchers("/**")
         .permitAll()
         .and()
         .formLogin().loginPage("/signin")
         .loginProcessingUrl("/dologin")
         .defaultSuccessUrl("/user/index")
         .and().csrf().disable();
       return http.build();
   }
}
