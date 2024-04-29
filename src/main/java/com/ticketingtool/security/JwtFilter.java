package com.ticketingtool.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	@Autowired
	JwtUtility jwtutill;
	@Autowired
	CustomUserDetailsServiceImpl serviceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (header != null && header.startsWith("Bearer")) {
			token = header.substring(7);
			logger.info(token);
			try {
				username = jwtutill.extractUsername(token);

			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				logger.info("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				logger.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (StringIndexOutOfBoundsException e) {
				logger.info("No token present in header!");
				e.printStackTrace();
			} 
			catch (Exception e) {
				e.printStackTrace();

			}
		} else {
			logger.info("Invalid Header Value ");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userdetail = this.serviceImpl.loadUserByUsername(username);
			Boolean validatetoken = this.jwtutill.validateToken(token, userdetail);
			if (validatetoken) {
				logger.info("token validation : "+validatetoken);
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken
						(userdetail, null, userdetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
			else {
                logger.info("Validation fails !!");
            }


        }

        filterChain.doFilter(request, response);

		
	}

}
