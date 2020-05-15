package com.js.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.js.filter.APIKeyAuthFilter;
import com.js.service.ClientServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Value("${js.http.auth.credential}")
	private String credentialRequestHeader;
	
	@Value("${js.http.auth.principal}")
	private String principalRequestHeader;
	
	private ClientServiceImpl clientService;
	
	@Autowired
	public SecurityConfig(ClientServiceImpl clientService)
	{
		this.clientService = clientService;
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(12);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		//
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.headers().disable();
		// 

		APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader, credentialRequestHeader);

		filter.setAuthenticationManager(new AuthenticationManager()
		{
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException
			{
				String principal = (String) authentication.getPrincipal();
				
				String credential = (String) authentication.getCredentials();
				
				if (principal == null)
				{
					principal = "";
				}
				
				if (credential == null)
				{
					credential = "";
				}
				
				if (!passwordEncoder().matches(credential, clientService.findByEmail(principal).getApiKey()))
				{
					throw new BadCredentialsException("The API key is not valid.");
				}

				authentication.setAuthenticated(true);

				return authentication;
			}
		});

		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(SecurityConfig::handleException).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);		
	}

	private static void handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException
	{
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/clients", "/db**", "/db/**");
	}
}
