package com.sts.springbootsecuritybackend.security;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sts.springbootsecuritybackend.domain.User;
import com.sts.springbootsecuritybackend.repository.UserRepository;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationManager authenticationManager;	
    
    @Autowired
    UserRepository userRepository;
    
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/*socialMediaId   ------	0=Gmail
									1=LinkedIn
									2=Facebook	*/
		 String username = authentication.getName();
		 String password = authentication.getCredentials().toString();

		 int statusFlag=101;
		 //if in credentials, string starts with socialAccessToken
		 //verify accesstoken against respective sites 
		 if(password.startsWith("socialAccessToken")){			 		 
			 
			 String array1[] = password.split("::");
			 System.out.println("password -- "+password);
			 Long socialMediaId = Long.parseLong(array1[1]);
			 String socialMediaUserIdFromAPI = array1[2];
			 String socialMediaEmailIdFromAPI = array1[3];
			 String socialMediaAccessTokenFromAPI = array1[4];
			 String socialMediaIPAddressFromAPI = array1[5];

			 //ObjectMapper mapper = new ObjectMapper();
			 RestTemplate restTemplate = new RestTemplate();

			 System.out.println("Inside socialAccessToken............");
			 String url = " ";
			 
			 if(socialMediaId ==0) //Gmail
				 url = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token="+socialMediaAccessTokenFromAPI;
			 else if(socialMediaId==1) //LinkedIn
				 url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address)?format=json&oauth2_access_token="+socialMediaAccessTokenFromAPI;
			 else if(socialMediaId==2) //Facebook
				 //url = "https://graph.facebook.com/me?fields=email&access_token="+socialMediaAccessTokenFromAPI;
				 url = "https://graph.facebook.com/me/?fields=id,email,first_name,last_name&access_token="+socialMediaAccessTokenFromAPI;
			 else
				 System.out.println("Invalid Media Id ...");
			 
			 String result = "";
			 try{
					result = restTemplate.getForObject(url,String.class);
			 }
			 catch(Exception e){
//				 throw new AccessDeniedException(String.format("201"));
//				 System.out.println("Invalid User !!!!!!!!");
			 }
			 
			if(!result.isEmpty()){
					try {
//						System.out.println("Result -- "+result);
				        JSONObject jsonObj = new JSONObject(result);
						//= mapper.readValue(result, SocialMediaAttributes.class);
				        User user =new User();
				        
						user.setSocialMediaUserIdFromSite(jsonObj.getString("id"));
						user.setIpAddress(socialMediaIPAddressFromAPI);

				        if(socialMediaId==0){ //Gmail					        	
							user.setSocialMediaEmailIdFromSite(jsonObj.getString("email"));					
							user.setFirstName(jsonObj.getString("given_name"));
							user.setLastName(jsonObj.getString("family_name"));	
				        }
				        
				        else if(socialMediaId==1){ //LinkedIn			        	
							user.setSocialMediaEmailIdFromSite(jsonObj.getString("emailAddress"));
							user.setFirstName(jsonObj.getString("firstName"));
							user.setLastName(jsonObj.getString("lastName"));				       
				        }
				        
				        else if(socialMediaId==2){  //Facebook
							user.setSocialMediaEmailIdFromSite(jsonObj.getString("email"));
							user.setFirstName(jsonObj.getString("first_name"));
							user.setLastName(jsonObj.getString("last_name"));
				        }
				        else
				        	System.out.println("Invalid Media Id");
				        
						if(user.getSocialMediaEmailIdFromSite().equals(socialMediaEmailIdFromAPI) 
						   && user.getSocialMediaUserIdFromSite().equals(socialMediaUserIdFromAPI)) {
//							System.out.println("Verification completed successfully!!!!");
							//To save User which is doing login using social media if that username is not present in DB....
		   				        User userData = userRepository.findByUsername(socialMediaEmailIdFromAPI);
						        if(userData==null){       //User not present in DB
						        	userData =new User();
						        	userData.setUsername(socialMediaEmailIdFromAPI);
						        	userData.setEmail(user.getSocialMediaEmailIdFromSite());
						        	userData.setFirstName(user.getFirstName());
						        	userData.setLastName(user.getLastName());
						        	userData.setPassword(" ");
						        	userData.setIpAddress(user.getIpAddress());
						        	userData.setActive(true);					        	
						        	
						            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						        	Date lastAccessTime=java.sql.Timestamp.valueOf(LocalDateTime.now().format(formatter));
						        	userData.setLastAccessTime(lastAccessTime);
						        	userRepository.save(userData);
						        	//System.out.println("Data saved in database for new user!!!");
						        }	else
						        	System.out.println("Data of user is already present in Database");
							
						}
						else{
//							System.out.println("Invalid User");
							statusFlag=102;
						}					
					} catch (Exception e) {
						e.printStackTrace();
					}		
			}else{
				statusFlag=102;
			}
		 }	else if(password!=null) {
			 //System.out.println("Inside CustomAuthenticationProvider......");
		        // Perform the security
			 final Authentication authenticationOfUser  = authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                		username,
		                		password    
		                )
		        );
			 authentication = authenticationOfUser;
		 }	
		 if(statusFlag==101)
			 return authentication;
		 else 
			 return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}
	
}
