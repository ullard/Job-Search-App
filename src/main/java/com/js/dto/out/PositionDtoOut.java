package com.js.dto.out;

public class PositionDtoOut
{
	private String title;

	private String location;

	private String url;

	public PositionDtoOut()
	{
	}

	public PositionDtoOut(String title, String location, String url)
	{
		super();
		this.title = title;
		this.location = location;
		this.url = url;
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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

}
