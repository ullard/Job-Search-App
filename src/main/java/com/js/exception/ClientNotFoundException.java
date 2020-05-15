package com.js.exception;

public class ClientNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1380709777991979537L;

	public ClientNotFoundException()
	{
		super();
	}

	public ClientNotFoundException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ClientNotFoundException(final String message)
	{
		super(message);
	}

	public ClientNotFoundException(final Throwable cause)
	{
		super(cause);
	}
}
