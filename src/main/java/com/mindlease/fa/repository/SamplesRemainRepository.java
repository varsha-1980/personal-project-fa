package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Location;
import com.mindlease.fa.model.SamplesRemain;


@Repository
public interface SamplesRemainRepository  extends JpaRepository<SamplesRemain, Long> {

}
