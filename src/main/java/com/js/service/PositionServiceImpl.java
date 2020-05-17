package com.js.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.js.domain.Position;
import com.js.dto.in.PositionDtoIn;
import com.js.dto.in.SearchDtoIn;
import com.js.dto.out.PositionDtoOut;
import com.js.exception.PositionNotFoundException;
import com.js.repository.PositionRepository;

@Service
public class PositionServiceImpl implements PositionService
{
	private PositionRepository positionRepository;

	private RestTemplate restTemplate;

	@Value("${server.port}")
	private String serverPort;

	@Autowired
	public void setPositionRepository(PositionRepository positionRepository)
	{
		this.positionRepository = positionRepository;
	}

	@Autowired
	public void setRestTemplate(RestTemplateBuilder restTemplateBuilder)
	{
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public String create(PositionDtoIn positionDto)
	{
		Position position = new Position(positionDto.getTitle(), positionDto.getLocation());

		Position saved = positionRepository.save(position);

		return "http://localhost:" + serverPort + "/positions/" + saved.getId();
	}

	@Override
	public List<PositionDtoOut> search(SearchDtoIn searchDto)
	{
		List<PositionDtoOut> positions = new ArrayList<PositionDtoOut>();

		for (PositionDtoOut position : getJobsFromGitHub(searchDto))
		{
			positions.add(position);
		}

		for (Position position : positionRepository.findByTitleAndLocation(searchDto.getKeyword().toLowerCase(), searchDto.getLocation().toLowerCase()))
		{
			String url = "http://localhost:" + serverPort + "/positions/" + position.getId();

			PositionDtoOut positionDto = new PositionDtoOut(position.getTitle(), position.getLocation(), url);

			positions.add(positionDto);
		}

		return positions;
	}

	private PositionDtoOut[] getJobsFromGitHub(SearchDtoIn searchDto)
	{
		String url = "https://jobs.github.com/positions.json?description=" + searchDto.getKeyword() + "&location=" + searchDto.getLocation();

		ResponseEntity<PositionDtoOut[]> responseEntity = restTemplate.getForEntity(url, PositionDtoOut[].class);
		PositionDtoOut[] response = responseEntity.getBody();

		return response;
	}

	@Override
	public Position findById(String id) throws PositionNotFoundException
	{
		Position position = positionRepository.findByID(id);

		if (position == null)
		{
			throw new PositionNotFoundException("There is no position with the given id");
		}

		return position;
	}

}
