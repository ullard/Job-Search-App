package com.js.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.js.domain.Position;
import com.js.dto.in.PositionDtoIn;
import com.js.repository.PositionRepository;

@Service
public class PositionServiceImpl implements PositionService
{
	private PositionRepository positionRepository;

	@Value("${server.port}")
	private String serverPort;

	@Autowired
	public PositionServiceImpl(PositionRepository positionRepository)
	{
		this.positionRepository = positionRepository;
	}

	@Override
	public String create(PositionDtoIn positionDto)
	{
		Position position = new Position(positionDto.getTitle(), positionDto.getLocation());

		Position saved = positionRepository.save(position);

		return "http://localhost:" + serverPort + "/positions/" + saved.getId();
	}

	@Override
	public Position findById(String id)
	{
		return positionRepository.findByID(id);
	}

}
