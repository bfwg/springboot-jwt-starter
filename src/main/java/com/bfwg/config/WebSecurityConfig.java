package com.bfwg.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bfwg.security.TokenHelper;
import com.bfwg.security.auth.RestAuthenticationEntryPoint;
import com.bfwg.security.auth.TokenAuthenticationFilter;
import com.bfwg.service.impl.CustomUserDetailsService;

/**
 * Created by fan.jin on 2016-10-19.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Autowired
	TokenHelper tokenHelper;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService),
						BasicAuthenticationFilter.class)
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(
								antMatcher(HttpMethod.GET, "/"),
								antMatcher(HttpMethod.GET, "/auth/**"),
								antMatcher(HttpMethod.GET, "/webjars/**"),
								antMatcher(HttpMethod.GET, "/*.html"),
								antMatcher(HttpMethod.GET, "/favicon.ico"),
								antMatcher(HttpMethod.GET, "/**/*.html"),
								antMatcher(HttpMethod.GET, "/**/*.css"),
								antMatcher(HttpMethod.GET, "/**/*.js"))
						.permitAll()
						.requestMatchers("/auth/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(sec -> sec.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(
						exceptionHandler -> exceptionHandler.authenticationEntryPoint(restAuthenticationEntryPoint))
				.csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// TokenAuthenticationFilter will ignore the below paths
		return (web) -> {
			web.ignoring()
					.requestMatchers(HttpMethod.POST, "/auth/login")
					.requestMatchers(
							antMatcher(HttpMethod.GET, "/"),
							antMatcher(HttpMethod.GET, "/webjars/**"),
							antMatcher(HttpMethod.GET, "/*.html"),
							antMatcher(HttpMethod.GET, "/favicon.ico"),
							antMatcher(HttpMethod.GET, "/**/*.html"),
							antMatcher(HttpMethod.GET, "/**path/*.css"),
							antMatcher(HttpMethod.GET, "/**path/*.js"));
		};
	}
}
