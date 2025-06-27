package com.example.securetask.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	 private final JwtService jwtService;
	    private final UserDetailsService userDetailsService;

	    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
	        this.jwtService = jwtService;
	        this.userDetailsService = userDetailsService;
	    }

	    @Override
	    protected void doFilterInternal(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    FilterChain filterChain)
	            throws ServletException, IOException {

	        if (request.getServletPath().startsWith("/api/auth")) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        final String authHeader = request.getHeader("Authorization");
	        final String jwt;
	        final String username;

	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        jwt = authHeader.substring(7);
	        username = jwtService.extractUsername(jwt);

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	            if (jwtService.isTokenValid(jwt)) {
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities()
	                );
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }

	        filterChain.doFilter(request, response);
	    }
}