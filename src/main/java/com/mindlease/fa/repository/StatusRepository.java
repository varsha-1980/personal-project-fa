package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
