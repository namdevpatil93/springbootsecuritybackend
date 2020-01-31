package com.sts.springbootsecuritybackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    
    @Autowired
    private UserDetailsService userDetailsService;

    private final static String permitURLS = "/,/venn,/*.html,/favicon.ico,/**/*.html,/**/*.css,/**/*.js,/**/*.svg,/**/*.eot,/**/*.woff2,/**/*.ttf,/**/*.woff,/**/*.jpg,/**/*.map,/assets/**,/chartiq_library/**,/app/**,/api/authenticate/**,/saveUser,/manifest.json,firebase-messaging-sw.js,/assets/img/favicon.ico";
   
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) 
    		throws Exception {
    	System.out.println("************called configureAuthentication------");
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter(permitURLS);
    }

    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

    		httpSecurity
             // we don't need CSRF because our token is invulnerable
             .csrf().disable()
             .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
             // don't create session
             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
             .authorizeRequests()
             .antMatchers(
            	        HttpMethod.GET,
            	        "/",
            	        "/venn",
            	        "/*.html",
            	        "/favicon.ico",
            	        "/**/*.html",
            	        "/**/*.css",
            	        "/**/*.js",
            	        "/**/*.svg",
            	        "/**/*.eot",
            	        "/**/*.woff2",
            	        "/**/*.ttf",
            	        "/**/*.woff",
            	        "/**/*.jpg",
            	        "/**/*.map",
            	        "/**/*.cur",
            	        "/**/*.png",
            	        "/manifest.json",
            	        "firebase-messaging-sw.js"
            	).permitAll()
            	.antMatchers("/api/authenticate/**").permitAll()
            	.antMatchers("/api/files/**").permitAll()
            	//.antMatchers("/api/users").hasAuthority("ROLE_ADMIN") works use simplegranted authority and add 
            	//.antMatchers("/logoutUser").permitAll()
            	.antMatchers("/saveUser").permitAll()
            	.anyRequest().authenticated()
     // Custom JWT based security filter
             
            .and().addFilterBefore(
         		   authenticationTokenFilterBean(),
                             UsernamePasswordAuthenticationFilter.class);
    	//}     	

        // disable page caching
        httpSecurity.headers().cacheControl();        
		
    }
}