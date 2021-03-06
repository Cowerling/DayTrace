package com.cowerling.daytrace.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class DayTraceWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final String ACTIVE_PROFILES = "development";
    private static final int MEGA_BYTE_SIZE = 1024 * 1024;
    private static final String MULTIPART_LOCATION = "E:\\IdeaProjects\\DayTrace\\target\\DayTrace\\resources\\image";

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext context = super.createRootApplicationContext();
        ConfigurableEnvironment environment = (ConfigurableEnvironment) context.getEnvironment();
        environment.setActiveProfiles(ACTIVE_PROFILES);
        return context;
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(MULTIPART_LOCATION, 2 * MEGA_BYTE_SIZE, 4 * MEGA_BYTE_SIZE, 0));
    }
}
