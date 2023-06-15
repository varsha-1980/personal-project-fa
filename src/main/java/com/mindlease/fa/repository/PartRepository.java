package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
	@Query("SELECT d FROM Part d  where d.name=:name")
	Part findByName(@Param("name") String name);
	
}
