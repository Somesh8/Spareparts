package com.sparepart.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparepart.model.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private CustomUserDetailService userService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Request 1 => "+ request.getRequestURI());
		System.out.println("Request 2 => "+ request.getMethod());
		System.out.println("Request 3 => "+ request.toString());
		
		String requestToken = request.getHeader("Authorization");
		System.out.println("token => "+ requestToken);
		
		String username = null;
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
				System.out.println("Username "+username);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get token");
			} catch (ExpiredJwtException e) {
				System.out.println("Token Expired");
			} catch (MalformedJwtException e) {
				// TODO: handle exception
				System.out.println("Invalid token");
			}
			
		} else {
			System.out.println("TOken not begin with `Bearer` ");
		}
		
		
		if(username !=  null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
//			User user = this.userService.getUserByEmail(username);
			User user = this.userService.loadUserByUsername(username);
			System.out.println("User "+user);
			if(this.jwtTokenHelper.validateToken(token, user)) {
				System.out.println("Valid JWT token"); 
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			} else {
				System.out.println("Invalid JWT token");
			}
		} else {
			System.out.println("Username is null & context is null");
		}

		filterChain.doFilter(request, response);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
