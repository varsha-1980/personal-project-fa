package com.mindlease.fa.repository;

import com.mindlease.fa.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("SELECT m FROM Material m where m.name=:name")
    Material getByName(@Param("name") String name);

    @Query("SELECT m FROM Material m where m.nameDe=:name")
    Material getByNameDe(@Param("name") String name);

}
