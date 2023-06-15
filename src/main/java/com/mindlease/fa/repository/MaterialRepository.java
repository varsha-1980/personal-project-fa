package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

}
