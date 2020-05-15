package com.js.service;

import com.js.domain.Position;
import com.js.dto.in.PositionDtoIn;

public interface PositionService
{
	public String create(PositionDtoIn positionDto);

	public Position findById(String id);
}
