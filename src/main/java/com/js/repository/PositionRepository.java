package com.js.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.js.domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, String>
{	
	@Query(value = "SELECT * FROM positions WHERE id = :id", nativeQuery = true)
	Position findByID(@Param("id") String id);
}
