package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

}
