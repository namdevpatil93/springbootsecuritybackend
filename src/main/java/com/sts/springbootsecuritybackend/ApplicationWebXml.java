package com.sts.springbootsecuritybackend;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.sts.springbootsecuritybackend.util.Constants;


/**
 * This is a helper Java class that provides an alternative to creating a web.xml.
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    private final Logger log = LoggerFactory.getLogger(ApplicationWebXml.class);

    public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.profiles(addDefaultProfile()).sources(SpringBootSecurityApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
       // servletContext.addListener(new SessionListener());
    }

    /**
     * Set a default profile if it has not been set.
     * <p>
     * Please use -Dspring.profiles.active=dev
     * </p>
     */
    private String addDefaultProfile() {
        String profile = System.getProperty(SPRING_PROFILES_ACTIVE);

        if (profile != null) {
            log.info("Running with Spring profile(s) : {}", profile);
            return profile;
        }

        log.warn("No Spring profile configured, running with default [{}] configuration", Constants.SPRING_PROFILE_DEFAULT );
        log.warn("If you are seeing this in the development, then you need to add a property 'spring.profiles.active=dev' to your local <TOMCAT_HOME>/conf/catalina.properties");
        return Constants.SPRING_PROFILE_DEFAULT;
    }

}
