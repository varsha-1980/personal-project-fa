package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.ElectricError;

@Repository
public interface ElectricErrorRepository extends JpaRepository<ElectricError, Long> {

}
