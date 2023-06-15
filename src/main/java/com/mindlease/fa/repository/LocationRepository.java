package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Location;

@Repository
public interface LocationRepository  extends JpaRepository<Location, Long> {

}

