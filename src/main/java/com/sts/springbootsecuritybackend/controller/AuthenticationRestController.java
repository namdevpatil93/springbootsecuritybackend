package com.sts.springbootsecuritybackend.controller;


import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sts.springbootsecuritybackend.domain.User;
import com.sts.springbootsecuritybackend.security.CustomAuthenticationProvider;
import com.sts.springbootsecuritybackend.security.SecurityUser;
import com.sts.springbootsecuritybackend.security.TokenProvider;
import com.sts.springbootsecuritybackend.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    
    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
	@Autowired
	private UserService userService;
	
    private Logger log = Logger.getLogger(this.getClass()); 

   // @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
/*    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device, HttpServletRequest request) throws AuthenticationException {

    	System.out.println("Device---ismobile--" + device.isMobile() + "-- isnormal--" + device.isNormal() + 
    			"-- istablet--" + device.isTablet());
    	
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenProvider.generateToken(userDetails, device, request.getRemoteAddr());

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }*/
   
    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> getAuthenticationToken(@RequestBody User user, Device device, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    	UsernamePasswordAuthenticationToken authenticationToken;
    	
    	//Check if we are getting socialAccessToken
    	if(user.getSocialMediaTokenFromApp()==null){	         
    				authenticationToken = new UsernamePasswordAuthenticationToken(
	    	    	user.getUsername(),
	    	    	user.getPassword()
	    	        );	    			
    	}else{
    				authenticationToken = new UsernamePasswordAuthenticationToken(
		    	    user.getUsername(),
		    	    "socialAccessToken::"+user.getSocialMediaIdFromApp()+"::"+user.getSocialMediaUserIdFromApp()+
		    	    "::"+user.getSocialMediaEmailIdFromApp()+"::"+user.getSocialMediaTokenFromApp()+"::"+request.getRemoteAddr());
    	}
		        	
		final Authentication authentication = customAuthenticationProvider.authenticate(
				authenticationToken
		);
		        	
		//If authentication == null (User credentials are not matching with data of Social media)
		
		if(authentication!=null){

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        // Reload password post-security so we can generate token
	        String userName;
	    	if(user.getSocialMediaTokenFromApp()==null)	   
	    		userName = user.getUsername();
	    	else
	    		userName = user.getSocialMediaEmailIdFromApp();	    	
	        
	        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
	        	      
	        SecurityUser user1 = (SecurityUser) userDetails;
	      //  log.debug("\tUser:\t"+userDetails.getUsername()+"\tLastAccessTime:\t" +user1.getLastAccessTime()+"\tIpAddress:\t"+user1.getIpAddress());
	        final String token = tokenProvider.generateToken(userDetails, device, request.getRemoteAddr());
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.add("Authorization",token);
	        return new ResponseEntity<>(new JWTToken(token), httpHeaders, HttpStatus.OK);
	   
		}else{
			log.info("Unauthorized - BadCredentials -- "+"Device---ismobile--" + device.isMobile() + "-- isnormal--" + device.isNormal() + 
	    			"-- istablet--" + device.isTablet()+ "UserName -- "+user.getUsername());
			return new ResponseEntity<String>("Unauthorized - BadCredentials", HttpStatus.UNAUTHORIZED);
		}
        

    }
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}