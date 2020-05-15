package com.js.dto.in;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PositionDtoIn
{
	@NotNull(message = "Title cannot be null")
	@Size(max = 50, message = "Title cannot be longer than 50 characters")
	private String title;

	@NotNull(message = "Location cannot be null")
	@Size(max = 50, message = "Location cannot be longer than 50 characters")
	private String location;

	public PositionDtoIn()
	{
	}

	public PositionDtoIn(@NotNull(message = "Title cannot be null") @Size(max = 50, message = "Title cannot be longer than 50 characters") String title, @NotNull(message = "Location cannot be null") @Size(max = 50, message = "Location cannot be longer than 50 characters") String location)
	{
		super();
		this.title = title;
		this.location = location;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
}
