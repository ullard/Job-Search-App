package com.js.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.js.JobSearchApplication;
import com.js.domain.Client;
import com.js.dto.in.ClientDtoIn;
import com.js.exception.EmailAlreadyExistException;
import com.js.repository.ClientRepository;
import com.test.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = JobSearchApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ClientControllerTest
{
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ClientRepository clientRepository;

	@After
	public void resetDb()
	{
		clientRepository.deleteAll();
	}

	@Test
	public void whenValidInput_thenCreateClient() throws IOException, Exception
	{
		ClientDtoIn clientDto = new ClientDtoIn("client", "client@gmail.com");
		mvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(clientDto)));

		Client found = clientRepository.findByEmail(clientDto.getEmail());

		assertNotNull(found);
		assertEquals(clientDto.getEmail(), found.getEmail());
	}
	
	@Test(expected = MethodArgumentNotValidException.class)
	public void whenInvalidInput_thenMethodArgumentNotValidException() throws IOException, Exception
	{
		ClientDtoIn clientDto = new ClientDtoIn(null, "client@gmail.com");
		mvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(clientDto)));
	}
	
	@Test(expected = EmailAlreadyExistException.class)
	public void whenAlreadyUsedEmail_thenEmailAlreadyExistException() throws IOException, Exception
	{
		createTestClient("client1", "client@gmail.com", "778243c4-afe0-40ab-97de-8aef6b1a0938");
		
		ClientDtoIn clientDto = new ClientDtoIn("client", "client@gmail.com");
		mvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(clientDto)));
	}

	private void createTestClient(String name, String email, String apiKey)
	{
		Client client = new Client(name, email, apiKey);
		clientRepository.saveAndFlush(client);
	}
	
}
