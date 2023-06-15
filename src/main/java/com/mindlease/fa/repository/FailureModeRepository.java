package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.FailureMode;

@Repository
public interface FailureModeRepository extends JpaRepository<FailureMode, Long> {

}
