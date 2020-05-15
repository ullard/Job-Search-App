package com.js.dto.in;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchDtoIn
{
	@NotNull(message = "Keyword cannot be null")
	@Size(max = 50, message = "Keyword cannot be longer than 50 characters")
	private String keyword;

	@NotNull(message = "Location cannot be null")
	@Size(max = 50, message = "Location cannot be longer than 50 characters")
	private String location;

	public SearchDtoIn()
	{
	}

	public SearchDtoIn(@Size(max = 50, message = "Keyword cannot be longer than 50 characters") String keyword, @Size(max = 50, message = "Location cannot be longer than 50 characters") String location)
	{
		super();
		this.keyword = keyword;
		this.location = location;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
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
