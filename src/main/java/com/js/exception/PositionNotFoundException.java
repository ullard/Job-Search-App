package com.js.exception;

public class PositionNotFoundException extends RuntimeException
{
	
	private static final long serialVersionUID = 7031592631849778907L;

	public PositionNotFoundException()
	{
		super();
	}
	
	public PositionNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PositionNotFoundException(String message)
	{
		super(message);
	}

	public PositionNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
