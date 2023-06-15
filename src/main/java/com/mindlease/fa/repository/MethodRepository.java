package com.mindlease.fa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Method;

@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {
	
	List<Method> findByGeneral(boolean general);
	List<Method> findByPack(boolean pack);
	List<Method> findByWfr(boolean wfr);

}
