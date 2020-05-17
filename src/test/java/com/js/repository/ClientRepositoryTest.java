package com.js.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.js.domain.Client;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest
{
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ClientRepository clientRepository;

	@Test
	public void whenFindByEmail_thenReturnClient()
	{
		Client client = new Client("client", "email@gmail.com", "3cd5ec03-ff38-4d8d-8bd9-6c1f12b5ff75");
		entityManager.persistAndFlush(client);

		Client found = clientRepository.findByEmail(client.getEmail());

		assertThat(found.getEmail()).isEqualTo(client.getEmail());
	}
}
