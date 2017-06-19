/**
 * 
 */
package org.kp.poc.demo;

import org.kp.poc.demo.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Robert Tu  06/18/2017
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("Initialize Web Security");
		http.authorizeRequests()
				.antMatchers(Constants.AUTHORIZED_URLS)
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").successForwardUrl("/")
				.failureUrl("/login?error").usernameParameter("username").permitAll().and().logout()
				.logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll();
		http.csrf().disable();
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
    	return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080", "http://localhost:8080/rest/{*}")
                        .allowedMethods("POST", "PUT", "GET")
                        .allowedHeaders("Authorization") 
                        .allowCredentials(true);
            }
        };
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
