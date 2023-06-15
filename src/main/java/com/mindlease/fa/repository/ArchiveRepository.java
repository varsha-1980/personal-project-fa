package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Archive;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

}
