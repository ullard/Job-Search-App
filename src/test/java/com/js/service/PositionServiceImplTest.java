package com.js.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.js.JobSearchApplication;
import com.js.domain.Position;
import com.js.dto.in.SearchDtoIn;
import com.js.dto.out.PositionDtoOut;
import com.js.exception.PositionNotFoundException;
import com.js.repository.PositionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = JobSearchApplication.class)
public class PositionServiceImplTest
{
	@TestConfiguration
	static class PositionServiceImplTestContextConfiguration
	{
		@Bean
		public PositionService positionService()
		{
			return new PositionServiceImpl();
		}
	}

	@Autowired
	private PositionServiceImpl positionService;

	@MockBean
	private PositionRepository positionRepository;

	@Before
	public void setUp()
	{
		Position position1 = new Position("finance", "new york");
		position1.setId("0ba8719c-bb27-44ae-9e70-33d444b10a38");

		Position position2 = new Position("java", "bucharest");
		position2.setId("e68c78f6-edaa-4fff-bedb-73ca5d6917d6");

		Position position3 = new Position("java script", "budapest");
		position3.setId("beefde61-28d5-4609-b865-98959c38e9e5");

		List<Position> searchResult = Arrays.asList(position2, position3);

		Mockito.when(positionRepository.findByTitleAndLocation("java", "bu")).thenReturn(searchResult);
		Mockito.when(positionRepository.findByID(position1.getId())).thenReturn(position1);
	}

	@Test
	public void whenValidId_thenPositionShouldBeFound()
	{
		Position fromDb = positionService.findById("0ba8719c-bb27-44ae-9e70-33d444b10a38");
		assertThat(fromDb.getTitle()).isEqualTo("finance");
	}

	@Test(expected = PositionNotFoundException.class)
	public void whenInValidId_thenPositionShouldNotBeFound()
	{
		positionService.findById("1");
	}

	@Test
	public void whenSearch_thenReturnRecordsIncludeUploadedMatches()
	{
		PositionDtoOut position2 = new PositionDtoOut("java", "bucharest", "http://localhost:0/positions/e68c78f6-edaa-4fff-bedb-73ca5d6917d6");
		PositionDtoOut position3 = new PositionDtoOut("java script", "budapest", "http://localhost:0/positions/beefde61-28d5-4609-b865-98959c38e9e5");

		SearchDtoIn searchDto = new SearchDtoIn("java", "bu");

		List<PositionDtoOut> searchResult = positionService.search(searchDto);

		assertThat(searchResult).extracting(PositionDtoOut::getUrl).contains(position2.getUrl(), position3.getUrl());
	}

}
