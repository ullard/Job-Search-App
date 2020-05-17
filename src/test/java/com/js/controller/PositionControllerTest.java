package com.js.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.After;
import org.junit.BeforeClass;
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
import com.js.dto.in.PositionDtoIn;
import com.js.repository.PositionRepository;
import com.test.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = JobSearchApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PositionControllerTest
{
	@Autowired
	private MockMvc mvc;

	@Autowired
	private PositionRepository positionRepository;

//	@Autowired
//	private ClientRepository clientRepository;

	@BeforeClass
	public void setUp()
	{
//		Client client = new Client("client", "client@gmail.com", "2f1a9cdc-83f2-441f-a837-e244c1be26f8");
//		clientRepository.saveAndFlush(client);
	}

	@After
	public void resetDb()
	{
		positionRepository.deleteAll();
	}

	@Test
	public void whenValidInputAndAuthenticated_thenCreatePosition() throws IOException, Exception
	{
		PositionDtoIn positionDto = new PositionDtoIn("full stack java", "budapest");
		mvc.perform(post("/positions").header("AUTH_API_KEY", "2f1a9cdc-83f2-441f-a837-e244c1be26f8").header("AUTH_EMAIL", "client@gmail.com").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(positionDto))).andExpect(status().isOk());
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void whenInvalidInputAndAuthenticated_thenMethodArgumentNotValidException() throws IOException, Exception
	{
		PositionDtoIn positionDto = new PositionDtoIn(null, "budapest");
		mvc.perform(post("/positions").header("AUTH_API_KEY", "2f1a9cdc-83f2-441f-a837-e244c1be26f8").header("AUTH_EMAIL", "client@gmail.com").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(positionDto)));
	}
}
