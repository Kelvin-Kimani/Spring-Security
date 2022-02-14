package com.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.springsecurity.security.UserPermissions.*;
import static com.springsecurity.security.UserRole.*;

@Configuration
@EnableWebSecurity

//To enable our preauthorize methods to work
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        var studentUser = User.builder()
                .username("Anna")
                .password(passwordEncoder().encode("password"))
//                .roles(STUDENT.name()) //ROLE_STUDENT
                .authorities(STUDENT.getCustomRoles())
                .build();


        var adminUser = User.builder()
                .username("Kimani")
                .password(passwordEncoder().encode("password"))
//                .roles(ADMIN.name()) //ROLE_ADMIN
                .authorities(ADMIN.getCustomRoles())
                .build();


        var adminTrainee = User.builder()
                .username("Jeff")
                .password(passwordEncoder().encode("password"))
//                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getCustomRoles())
                .build();

        return new InMemoryUserDetailsManager(studentUser, adminUser, adminTrainee);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Configuring CSRF
                .csrf().disable()
                .authorizeRequests()

                //White list urls you don't want authenticated
                .antMatchers("/", "index").permitAll()

                //Use Permission based authorization
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())

                //Use Role Based Authorization
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest().authenticated()
                .and()

                //We want to use BAsic Authentication
//                .httpBasic()

                .formLogin();
    }
}
