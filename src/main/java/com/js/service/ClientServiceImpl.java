package com.js.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.js.domain.Client;
import com.js.dto.in.ClientDtoIn;
import com.js.exception.EmailAlreadyExistException;
import com.js.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService
{
	private ClientRepository clientRepository;
	
	private PasswordEncoder passwordEncoder;

	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder)
	{
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public String register(ClientDtoIn clientDto) throws EmailAlreadyExistException
	{
		if (isEmailExists(clientDto.getEmail()))
		{
			throw new EmailAlreadyExistException("The given email is already in use: " + clientDto.getEmail());
		}
		
		UUID apiKey = UUID.randomUUID();
		
		Client client = new Client(clientDto.getName(), clientDto.getEmail(), passwordEncoder.encode(apiKey.toString()));
		
		clientRepository.save(client);
		
		return apiKey.toString();
	}
	
	private boolean isEmailExists(String email)
	{
		Client client = clientRepository.findByEmail(email);

		if (client != null)
		{
			return true;
		}

		return false;
	}
}
