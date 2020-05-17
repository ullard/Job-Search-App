package com.js.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.js.dto.in.ClientDtoIn;
import com.js.service.ClientServiceImpl;

@RestController
@RequestMapping("/clients")
public class ClientController
{

	private ClientServiceImpl clientService;

	@Autowired
	public void setClientService(ClientServiceImpl clientService)
	{
		this.clientService = clientService;
	}

	@Transactional
	@PostMapping
	public String register(@Valid @RequestBody ClientDtoIn clientDto)
	{
		return clientService.register(clientDto);
	}
}
