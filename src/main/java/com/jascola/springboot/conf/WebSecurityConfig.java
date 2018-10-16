package com.jascola.springboot.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.authorizeRequests().antMatchers("/**").hasRole("ADMIN").and().formLogin().and().httpBasic();
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {

            auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("jascola").
                    password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN");
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);

        }
    }

    @Override
    public void configure(WebSecurity web) {
        try {
            super.configure(web);
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }
}
