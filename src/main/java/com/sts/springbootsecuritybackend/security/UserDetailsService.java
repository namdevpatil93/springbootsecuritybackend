package com.sts.springbootsecuritybackend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import com.sts.springbootsecuritybackend.domain.Module;
import com.sts.springbootsecuritybackend.domain.ModuleOperation;
import com.sts.springbootsecuritybackend.domain.Role;
import com.sts.springbootsecuritybackend.domain.User;
import com.sts.springbootsecuritybackend.service.ModuleService;
import com.sts.springbootsecuritybackend.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserService userService;


    @Autowired
    private ModuleService moduleService;

	@Override
	public UserDetails loadUserByUsername(final String loginName) throws UsernameNotFoundException, DataAccessException {

        log.debug("Authenticating : {}", loginName);

        SecurityUser securityUser = null;

		
		
		try {
			
                securityUser = authenticateUser(loginName, securityUser);
               // securityUser.setParentRoleId((long) UserRole.APPLICANT.getId());
        } catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return securityUser;
	}

    private SecurityUser authenticateUser(String userName, SecurityUser securityUser) {
       
    	  User user = userService.findByUsername(userName);
          
    	  
    	
    	
    	if (user  != null) {
            log.debug("User with the applicant ID {} FOUND ", userName);
            
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().getTitle())); //ADMIN can see but super============
            
            
        /*    if(user.getRole().getTitle().equals("ROLE_ADMIN"))
            {
            	authorities.add(new SimpleGrantedAuthority("/api/users"));
                
            }*/
            
            
            securityUser = new SecurityUser(user.getUsername(), user.getPassword(), user.getRole(),authorities);
            securityUser.setUser(user);
        } else {
            log.debug("User with the username {}  NOT FOUND", userName);
            throw new UsernameNotFoundException("Username not found.");
        }

        return securityUser;
    }


    	    
    /* this will not work for JWT
     * 
     * private List<GrantedAuthority> getGrantedAuthorities(Role role) {
        log.debug("Populating user authorities");

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<Module> allModules = moduleService.findAllModules();
		Map<Long, Module> moduleMap = new HashMap<Long, Module>();

        authorities.add(new SimpleGrantedAuthority(role.getTitle()));
        
        
    
		for(Module module : allModules){
			moduleMap.put(module.getModuleId(), module);
		}

		List<ModuleOperation> moduleOperationList = moduleService.findModuleOperationsByRoleId(role.getId());

		
		 moduleOperationList.stream().map(op-> new SimpleGrantedAuthority(op.getModuleOperationName())).forEach(authorities::add);;
		
		
		for (ModuleOperation moduleOperation : moduleOperationList) {
			moduleOperation.setModuleName(moduleMap.get(moduleOperation.getModuleId()).getModuleName());
			authorities.add(moduleOperation);
		}

		return authorities;
	}*/
}
