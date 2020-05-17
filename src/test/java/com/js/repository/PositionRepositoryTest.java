package com.js.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.js.domain.Position;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PositionRepositoryTest
{
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PositionRepository positionRepository;

	@Test
	public void whenFindById_thenReturnPosition()
	{
		Position position = new Position("title", "location");

		entityManager.persistAndFlush(position);

		Position found = positionRepository.findByID(position.getId());

		assertThat(found.getId()).isEqualTo(position.getId());
	}

	@Test
	public void whenfindByTitleAndLocation_thenReturnPositions()
	{
		Position position1 = new Position("finance", "new york");
		Position position2 = new Position("java", "bucharest");
		Position position3 = new Position("java script", "budapest");

		entityManager.persist(position1);
		entityManager.persist(position2);
		entityManager.persist(position3);
		entityManager.flush();

		List<Position> searchResult = positionRepository.findByTitleAndLocation("java", "bu");

		assertThat(searchResult).hasSize(2).extracting(Position::getTitle).containsOnly(position2.getTitle(), position3.getTitle());
	}

}
