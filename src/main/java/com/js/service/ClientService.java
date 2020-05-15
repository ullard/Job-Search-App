package com.js.service;

import com.js.dto.in.ClientDtoIn;
import com.js.exception.EmailAlreadyExistException;

public interface ClientService
{
	public String register(ClientDtoIn clientDto) throws EmailAlreadyExistException;
}
