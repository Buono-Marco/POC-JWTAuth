package com.example.pocjwtauth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.pocjwtauth.util.ConstantUtils.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final String[] whitelist = {
            "/auth/externalLogin"
    };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .csrf().disable() // TO ENABLE AND MANAGE CSRF TOKEN
                .authorizeRequests()
                .antMatchers(whitelist).permitAll()
                .antMatchers(HttpMethod.GET, "/**").hasAnyAuthority(ROLE_USER, ROLE_EDITOR, ROLE_ADMIN)
                .anyRequest()
                .authenticated();
    }

}
