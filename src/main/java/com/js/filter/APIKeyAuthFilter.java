package com.js.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter
{

	private String principalRequestHeader;
	
	private String credentialRequestHeader;

	public APIKeyAuthFilter(String principalRequestHeader, String credentialRequestHeader)
	{
		this.principalRequestHeader = principalRequestHeader;
		this.credentialRequestHeader = credentialRequestHeader;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request)
	{
		return request.getHeader(principalRequestHeader);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request)
	{
		return request.getHeader(credentialRequestHeader);
	}

}
