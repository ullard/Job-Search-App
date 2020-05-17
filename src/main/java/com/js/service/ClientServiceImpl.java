package com.js.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.js.domain.Client;
import com.js.dto.in.ClientDtoIn;
import com.js.exception.ClientNotFoundException;
import com.js.exception.EmailAlreadyExistException;
import com.js.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService
{
	private ClientRepository clientRepository;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public void setClientRepository(ClientRepository clientRepository)
	{
		this.clientRepository = clientRepository;
	}

	@Autowired
	public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Client findByEmail(String email) throws ClientNotFoundException
	{
		Client client = clientRepository.findByEmail(email);

		if (client == null)
		{
			throw new ClientNotFoundException("There is no client with the given email");
		}

		return client;
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
