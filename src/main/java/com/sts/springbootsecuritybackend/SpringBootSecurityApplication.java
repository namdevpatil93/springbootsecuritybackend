package com.sts.springbootsecuritybackend;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.sts.springbootsecuritybackend.core.config.ApplicationProperties;


@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "com.sts")
@EnableConfigurationProperties({ ApplicationProperties.class })
public class SpringBootSecurityApplication    {
	   public static void main(String[] args) {
	        SpringApplication.run(SpringBootSecurityApplication.class, args);
	        
	    }
}
