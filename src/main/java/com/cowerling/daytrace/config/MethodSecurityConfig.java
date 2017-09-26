package com.cowerling.daytrace.config;

import com.cowerling.daytrace.security.UserAccessPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private UserAccessPermissionEvaluator userAccessPermissionEvaluator;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setPermissionEvaluator(userAccessPermissionEvaluator);
        return defaultMethodSecurityExpressionHandler;
    }
}
