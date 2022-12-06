package com.sparepart.config;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sparepart.security.CustomUserDetailService;
import com.sparepart.security.JwtAuthenticationEntryPoint;
import com.sparepart.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/api/v1/auth/**").permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
//	public FilterRegistrationBean corsFilter() {
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowCredentials(true);
		corsConfig.addAllowedOriginPattern("*");
		corsConfig.addAllowedHeader("Authorization");
		corsConfig.addAllowedHeader("Content-Type");
		corsConfig.addAllowedHeader("Accept");
		corsConfig.addAllowedMethod("POST");
		corsConfig.addAllowedMethod("GET");
		corsConfig.addAllowedMethod("PUT");
		corsConfig.addAllowedMethod("DELETE");
		corsConfig.addAllowedMethod(HttpMethod.OPTIONS);
		corsConfig.addAllowedMethod(HttpMethod.HEAD);

		corsConfig.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", corsConfig);
		return new CorsFilter(source);
//		FilterRegistrationBean frBean = new FilterRegistrationBean(new CorsFilter(source));
//		return frBean;
	}

 }
