package com.js.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.js.domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, String>
{
	@Query(value = "SELECT * FROM positions WHERE LOWER(title) LIKE %:keyword% AND LOWER(location) LIKE %:location%", nativeQuery = true)
	List<Position> findByTitleAndLocation(@Param("keyword") String keyword, @Param("location") String location);
	
	@Query(value = "SELECT * FROM positions WHERE id = :id", nativeQuery = true)
	Position findByID(@Param("id") String id);
}
