package com.cowerling.daytrace.config;

import com.cowerling.daytrace.data.UserRepository;
import com.cowerling.daytrace.security.PasswordEncoderService;
import com.cowerling.daytrace.security.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;
    
    private static final int TOKEN_TIME = 3600;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new SecurityUserService(userRepository)).passwordEncoder(passwordEncoderService.getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .httpBasic().realmName("DayTrace")
                .and()
                .authorizeRequests().antMatchers("/resources/**", "/login", "/user/register", "/user/registerSuccess").permitAll().anyRequest().authenticated()
                .and()
                .rememberMe().tokenValiditySeconds(TOKEN_TIME).key("dayTraceKey");
    }
}
