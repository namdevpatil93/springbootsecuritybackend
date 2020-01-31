package com.sts.springbootsecuritybackend.security;

import org.springframework.mobile.device.Device;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class TokenProvider 
{
	static final String CLAIM_KEY_USERNAME = "sub";
	 private static final String AUTHORITIES_KEY = "auth";
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_EXPIRED = "exp";
    static final String CLAIM_IP_ADDRESS = "ipaddress";
    
    
    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    
	 public String getUsernameFromToken(String token) {
	        String username;
	        try {
	            final Claims claims = getClaimsFromToken(token);
	            username = (String)claims.get(CLAIM_KEY_USERNAME);
	        } catch (Exception e) {
	            username = null;
	        }
	        return username;
	    }
	 
	 private Claims getClaimsFromToken(String token) {
	        Claims claims;
	        try {
	            claims = Jwts.parser()
	                    .setSigningKey(secret)
	                    .parseClaimsJws(token)
	                    .getBody();
	        } catch (Exception e) {
	            claims = null;
	        }
	        return claims;
	    }
	 public Date getExpirationDateFromToken(String token) {
	        Date expiration;
	        try {
	            final Claims claims = getClaimsFromToken(token);
	            expiration = claims.getExpiration();
	        } catch (Exception e) {
	            expiration = null;
	        }
	        return expiration;
	    }
	 
	 private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }
	 private String generateAudience(Device device) {
	        String audience = AUDIENCE_UNKNOWN;
	        if (device.isNormal()) {
	            audience = AUDIENCE_WEB;
	        } else if (device.isTablet()) {
	            audience = AUDIENCE_TABLET;
	        } else if (device.isMobile()) {
	            audience = AUDIENCE_MOBILE;
	        }
	        return audience;
	    }
	 public String generateToken(UserDetails userDetails,Device device, String ipAddress) {
	        Map<String, Object> claims = new HashMap<>();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    	Date lastAccessTime=java.sql.Timestamp.valueOf(LocalDateTime.now().format(formatter));
//	    	log.debug("lastAccessTime----"+lastAccessTime+"----userDetails.getUsername()----"+userDetails.getUsername()+"---ipAddress----"+ipAddress);
	        //userService.saveIpAddress(ipAddress,lastAccessTime, userDetails.getUsername());   	    	
	  	
	        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
	        claims.put(CLAIM_IP_ADDRESS, ipAddress);
	        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));

	        final Date createdDate = new Date();
	        claims.put(CLAIM_KEY_CREATED, createdDate);

	        final String token = doGenerateToken(claims);
	        
	      //  userDataMap.getUserDataMap().put(userDetails.getUsername(), token);        
	        //System.out.println("userDataMap -- "+userDataMap.getUserDataMap());
	        
	    //    userService.saveIpAddressAndToken(ipAddress, token, lastAccessTime, userDetails.getUsername());        
	        //System.out.println("Token and IP address saved in DB !!");
	        
	        return token;        
	    }

	    private String doGenerateToken(Map<String, Object> claims) {
	        final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
	        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

//	        System.out.println("doGenerateToken " + createdDate);

	        return Jwts.builder()
	                .setClaims(claims)
	                .setExpiration(expirationDate)
	                .signWith(SignatureAlgorithm.HS512, secret)
	                .compact();
	    }
	    public int validateToken(String token, UserDetails userDetails) {
	        SecurityUser user = (SecurityUser) userDetails; 
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    	Date lastAccessTime=java.sql.Timestamp.valueOf(LocalDateTime.now().format(formatter));
	        
	    	// userService.saveLastAccessTime(lastAccessTime,userDetails.getUsername());
	        final String username = getUsernameFromToken(token);
	        //final String ipAddressFromDb = user.getIpAddress();
	        
	        int statusFlag=200;
	        if(!username.equals(user.getUsername()))
	        	statusFlag = 201;        
	        else if(isTokenExpired(token))
	    		statusFlag = 202;    	
	        //Below code is to add IP address verification only for Production
	       // else if (applicationProperties.getResources().getProfile().equalsIgnoreCase("prod") &&  (!ipAddressFromDb.equalsIgnoreCase(ipAddressFromToken)))

	        //else if (!ipAddressFromDb.equalsIgnoreCase(ipAddressFromToken))        	
	        //statusFlag = 203; 
	        
	        //log.info("user-" +  user.getUsername() +"--ipAddressFromDb----------"+ipAddressFromDb + "----ipAddressFromToken----" + ipAddressFromToken + "--statusFlag--" + statusFlag);
	        return statusFlag;
	    }
}
