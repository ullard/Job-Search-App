package com.js.dto.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientDtoIn
{
	@NotNull(message = "Name cannot be null")
	@Size(max = 100, message = "Name cannot be longer than 100 characters")
	private String name;

	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be valid")
	private String email;

	public ClientDtoIn()
	{
	}

	public ClientDtoIn(@NotNull(message = "Name cannot be null") @Size(max = 100, message = "Name cannot be longer than 100 characters") String name, @NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email)
	{
		super();
		this.name = name;
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

}
