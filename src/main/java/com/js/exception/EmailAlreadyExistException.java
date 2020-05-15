package com.js.exception;

public class EmailAlreadyExistException extends RuntimeException
{
	private static final long serialVersionUID = 1151270831515263994L;

	public EmailAlreadyExistException()
	{
		super();
	}
	
	public EmailAlreadyExistException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EmailAlreadyExistException(String message)
	{
		super(message);
	}

	public EmailAlreadyExistException(Throwable cause)
	{
		super(cause);
	}

}