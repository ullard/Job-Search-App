package com.js.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "POSITIONS")
public class Position
{
	@Id
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "title", length = 50, nullable = false)
	private String title;

	@Column(name = "location", length = 50, nullable = false)
	private String location;
	
	public Position()
	{
	}

	public Position(String title, String location)
	{
		super();
		this.title = title;
		this.location = location;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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
