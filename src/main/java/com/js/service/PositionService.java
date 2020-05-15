package com.js.service;

import java.util.List;

import com.js.domain.Position;
import com.js.dto.in.PositionDtoIn;
import com.js.dto.in.SearchDtoIn;
import com.js.dto.out.PositionDtoOut;
import com.js.exception.PositionNotFoundException;

public interface PositionService
{
	public String create(PositionDtoIn positionDto);
	
	public List<PositionDtoOut> search(SearchDtoIn searchDto);
	
	public Position findById(String id) throws PositionNotFoundException;
}
