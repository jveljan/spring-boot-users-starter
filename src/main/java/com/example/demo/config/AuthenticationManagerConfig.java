package com.example.demo.config;

import com.example.demo.config.security.SecurityUserDetailsService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class AuthenticationManagerConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationManagerConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new SecurityUserDetailsService(userService)).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // disable security for OPTIONS (needed for cross origin requests)
        //web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers("/public/**");
        web.ignoring().antMatchers("/api/pub/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http
                .authorizeRequests()
			    .antMatchers("/api/**").hasAuthority("API_ACCESS")
			.and()
			.csrf().disable()
			.anonymous().disable()
			.httpBasic()
			//	.authenticationEntryPoint(authenticationEntryPoint)
            .and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // @formatter:on
    }
}