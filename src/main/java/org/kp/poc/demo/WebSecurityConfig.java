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
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
//import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
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
		
		// Java translation for: Spring Security applicationContext-security.xml
		/*
		 <security:http auto-config="true" access-denied-page="/auth/denied">
    	<security:intercept-url pattern="/admin/*" access="ROLE_ADMIN"/>
    	<security:intercept-url pattern="/import/*" access="ROLE_ADMIN"/>
    	<security:intercept-url pattern="/user/*" access="ROLE_USER"/>
    	<security:intercept-url pattern="/auth/login" access="IS_AUTHENTICATED_ANONYMOUSLY"/>
    	<security:intercept-url pattern="/auth/register" access="IS_AUTHENTICATED_ANONYMOUSLY"/>
    	<security:intercept-url pattern="/**" access="IS_AUTHENTICATED_ANONYMOUSLY"/>
    	<security:form-login login-page="/auth/login" 
	   	authentication-failure-url="/auth/login?login_error=true"
    	default-target-url="/user"/>
    	<security:logout logout-url="/auth/logout" logout-success-url="/" invalidate-session="true"/>
		</security:http>
		 */
		
		// Get URL Registry
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
		
		// Create configuration for Authorized URLS. Allows non-authenticated access to pre-defined URL in Constants.AUTHORIZED_URLS
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrls = urlRegistry.antMatchers(Constants.AUTHORIZED_URLS).permitAll().anyRequest();
		
		// Forward to "/" for authenticated User. Non authenticated to be redirected to Login Page "/login
		FormLoginConfigurer<HttpSecurity> loginConfigurer = authorizedUrls.authenticated().
				and().
				formLogin().loginPage("/login").successForwardUrl("/");
	
		// Login error redirects to /login?error and log the user out		
		// After logout, then delete cookies. Logout successful then redirects to /login
		loginConfigurer.failureUrl("/login?error").usernameParameter("username").permitAll().
				and().
				logout().logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll();
		
		/*http.authorizeRequests()
				.antMatchers(Constants.AUTHORIZED_URLS)
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").successForwardUrl("/")
				.failureUrl("/login?error").usernameParameter("username").permitAll().and().logout()
				.logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll();*/
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
