package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Costs;

@Repository
public interface CostsRepository extends JpaRepository<Costs, Long> {

}
