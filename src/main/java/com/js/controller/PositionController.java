package com.js.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.js.domain.Position;
import com.js.dto.in.PositionDtoIn;
import com.js.dto.in.SearchDtoIn;
import com.js.dto.out.PositionDtoOut;
import com.js.service.PositionServiceImpl;

@RestController
@RequestMapping("/positions")
public class PositionController
{
	private PositionServiceImpl positionService;

	@Autowired
	public PositionController(PositionServiceImpl positionService)
	{
		this.positionService = positionService;
	}

	@Transactional
	@PostMapping
	public String create(@Valid @RequestBody PositionDtoIn positionDto)
	{
		return positionService.create(positionDto);
	}

	@Transactional
	@GetMapping
	public List<PositionDtoOut> search(@Valid SearchDtoIn searchDto)
	{
		return positionService.search(searchDto);
	}

	@Transactional
	@GetMapping(path = "/{id}")
	public Position findById(@PathVariable("id") String id)
	{
		return positionService.findById(id);
	}
}
