package com.mindlease.fa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Method;
import com.mindlease.fa.model.MethodX;

@Repository
public interface MethodXRepository extends JpaRepository<MethodX, Long> {

	/*
	@Query("SELECT d FROM MethodX d INNER JOIN d.method p where d.orderDetails.id=:orderId and d.general=true")
	List<MethodX> findAllGeneralMethods(@Param("orderId") Long orderId);

	@Query("SELECT d FROM MethodX d INNER JOIN d.method p where d.orderDetails.id=:orderId and d.pack=true")
	List<MethodX> findAllHouseMethods(@Param("orderId") Long orderId);

	@Query("SELECT d FROM MethodX d INNER JOIN d.method p where d.orderDetails.id=:orderId and d.wfr=true")
	List<MethodX> findAllWaferMethods(@Param("orderId") Long orderId);
	
	MethodX findByMethod(Method method);
	
	*/
	
	
	@Query("SELECT d FROM MethodX d  where d.order_id=:orderId and d.general=true")
	List<MethodX> findAllGeneralMethods(@Param("orderId") Long orderId);

	@Query("SELECT d FROM MethodX d where d.order_id=:orderId and d.pack=true")
	List<MethodX> findAllHouseMethods(@Param("orderId") Long orderId);

	@Query("SELECT d FROM MethodX d where d.order_id=:orderId and d.wfr=true")
	List<MethodX> findAllWaferMethods(@Param("orderId") Long orderId);
	
	@Query("SELECT d FROM MethodX d where d.name=:name and d.wfr=true")
	MethodX findByName(String name);

	@Query("SELECT d FROM MethodX d  where d.order_id=:orderId")
	List<MethodX> findAllGeneralMethodsByOrderId(@Param("orderId") Long orderId);
	
	
}
