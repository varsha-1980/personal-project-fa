package com.mindlease.fa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Personal;
import com.mindlease.fa.model.User;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
	@Query("SELECT f FROM Personal f WHERE LOWER(f.pers_mail) = LOWER(:email)")
	Optional<Personal> findByPERS_MAIL(@Param("email")String email);

	@Query("SELECT f FROM Personal f WHERE LOWER(f.pers_short) = LOWER(:name)")
	Optional<Personal> findByPERS_SHORT(@Param("name")String name);
}
