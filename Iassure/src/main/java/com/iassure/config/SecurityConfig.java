package com.iassure.config;

import com.iassure.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


/**
 * @author Naveen Kumar Chintala
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {


	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	@Value("${app.security.enabled}")
	private boolean securityEnabled;

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**",
			"/api/v1/user/login",
			"/api/auth/signin"
	};


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.build();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		log.info("Is security Enabled : {}", securityEnabled);

		if(securityEnabled) {
			http.csrf(AbstractHttpConfigurer::disable).
					authorizeHttpRequests(auth ->  auth
							.requestMatchers(AUTH_WHITELIST).
							permitAll().
							anyRequest().
							authenticated()).sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
							.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
					       .exceptionHandling(
							e->e.accessDeniedHandler(
											(request, response, accessDeniedException)->response.setStatus(403)
									)
									.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

			/*http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
					.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));*/


		}else{
			http.csrf(AbstractHttpConfigurer::disable).
					authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		}


		return http.build();
	}
}