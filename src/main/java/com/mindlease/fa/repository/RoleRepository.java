package com.mindlease.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Role;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
	Role findByName(String email);
}
