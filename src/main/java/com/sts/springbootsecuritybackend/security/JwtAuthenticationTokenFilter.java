package com.sts.springbootsecuritybackend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sts.springbootsecuritybackend.core.config.ApplicationProperties;
import com.sts.springbootsecuritybackend.domain.ModuleOperation;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Value("${jwt.header}")
	private String tokenHeader;

	List<AntPathRequestMatcher> permitURLsMatchers;

	public JwtAuthenticationTokenFilter(String permitURLs) {
		super();
		if (permitURLs != null && !permitURLs.isEmpty()) {
			permitURLsMatchers = new ArrayList<AntPathRequestMatcher>();
			if (permitURLs.contains(",")) {
				for (String permitURL : permitURLs.split(",")) {
					permitURLsMatchers.add(new AntPathRequestMatcher(permitURL));
				}

			} else {
				permitURLsMatchers.add(new AntPathRequestMatcher(permitURLs));
			}
		}

	}

	protected boolean hasMatched(HttpServletRequest request) {
		if (permitURLsMatchers != null && !permitURLsMatchers.isEmpty()) {
			for (AntPathRequestMatcher antPathRequestMatcher : permitURLsMatchers) {
				if (antPathRequestMatcher.matches(request)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// logger.info(request.getRequestURL());
		
		// we dont want to valid token like html css page 
		if ((!hasMatched(request))) {
			// &&
			// ((applicationProperties.getResources().getProfile().equalsIgnoreCase("prod"))||
			// (applicationProperties.getResources().getProfile().equalsIgnoreCase("uat"))
			// )) {

			String authToken = request.getHeader(this.tokenHeader);
			String username = tokenProvider.getUsernameFromToken(authToken);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

				// Uncomment this if want to allow simultaneous login using same userName
				// int statusFlag = jwtTokenUtil.validateToken(authToken, userDetails,
				// request.getRemoteAddr());

				// Allowing only one login per user.
				int statusFlag;
				statusFlag = tokenProvider.validateToken(authToken, userDetails);

				if (statusFlag == 200) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					userDetails.getAuthorities().stream().map(e->e.getAuthority());
					
					
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// logger.info("authenticated user " + username + ", setting security context");
					SecurityContextHolder.getContext().setAuthentication(authentication);
					chain.doFilter(request, response);

				} else {
					// REMOVE TOKEN From LIVE DATA server
					response.resetBuffer();
					HttpServletResponse hsr = (HttpServletResponse) response;

					if (statusFlag == 201) {
						response.getOutputStream().write("Invalid UserName in Token".getBytes());
						hsr.setStatus(201);
					} else if (statusFlag == 202) {
						response.getOutputStream().write("Token Expired".getBytes());
						hsr.setStatus(202);
					} else if (statusFlag == 203) {
						// this user logged in from another machine hence now database is holding new IP
						// address and token is holding old IP address becz of that IP
						// address is not matching in this case we need to
						response.getOutputStream().write("User logged in from another IP address".getBytes());
						hsr.setStatus(203);
					}
				}
			} else {
				HttpServletResponse hsr = (HttpServletResponse) response;
				response.getOutputStream().write("Unauthorized User".getBytes());
				hsr.setStatus(401);
			}
		} else {
			chain.doFilter(request, response);
		}
	}
}