package com.js.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.js.domain.Client;
import com.js.exception.ClientNotFoundException;
import com.js.repository.ClientRepository;

@RunWith(SpringRunner.class)
public class ClientServiceImplTest
{
	@TestConfiguration
	static class ClientServiceImplTestContextConfiguration
	{
		@Bean
		public ClientService clientService()
		{
			return new ClientServiceImpl();
		}
	}

	@Autowired
	private ClientServiceImpl clientService;

	@MockBean
	private ClientRepository clientRepository;

	@Before
	public void setUp()
	{
		Client client = new Client("client", "client@gmail.com", "2f1a9cdc-83f2-441f-a837-e244c1be26f8");
		client.setId("05d91a4e-db62-4dbc-878f-d6f0d366b565");

		Mockito.when(clientRepository.findByEmail(client.getEmail())).thenReturn(client);
		Mockito.when(clientRepository.findByEmail("wrong_email")).thenThrow(new ClientNotFoundException("There is no client with the given email"));
	}

	@Test
	public void whenValidEmail_thenClientShouldBeFound()
	{
		String email = "client@gmail.com";
		Client found = clientService.findByEmail(email);

		assertThat(found.getEmail()).isEqualTo(email);
	}

	@Test(expected = ClientNotFoundException.class)
	public void whenInValidEmail_thenClientNotFoundException()
	{
		clientService.findByEmail("wrong_email");
	}

}
