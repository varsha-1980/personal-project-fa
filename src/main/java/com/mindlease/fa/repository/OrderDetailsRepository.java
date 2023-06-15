package com.mindlease.fa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindlease.fa.model.Company;
import com.mindlease.fa.model.OrderDetails;

@Repository
public interface OrderDetailsRepository  extends PagingAndSortingRepository<OrderDetails, Long> {
	/*
	OrderDetails findByOrderNumber(@Param("orderNumber") String orderNumber);
	
	@Query("SELECT d FROM OrderDetails d INNER JOIN d.priority p  INNER JOIN d.material m   INNER JOIN d.location l   INNER JOIN d.customer c where d.id=:id")
	OrderDetails getById(@Param("id") Long id);
	
	@Query("SELECT d FROM OrderDetails d INNER JOIN d.priority p  INNER JOIN d.material m   INNER JOIN d.location l   INNER JOIN d.customer c ")
	List<OrderDetails> fetchOrderDetailsInnerJoin();
	
	@Query("SELECT d FROM OrderDetails d INNER JOIN d.priority p  INNER JOIN d.material m   INNER JOIN d.location l   INNER JOIN d.customer c ")
	Page<OrderDetails> fetchOrderDetailsInnerJoin(Pageable pageable);  

	
	@Query("SELECT d FROM OrderDetails d INNER JOIN d.priority p  INNER JOIN d.material m   INNER JOIN d.location l   INNER JOIN d.customer c where d.company=:company")
	List<OrderDetails> fetchOrderDetailsInnerJoin(@Param("company") Company company);  
	
	*/
	@Query("SELECT d FROM OrderDetails d  where d.user.id=:user_id")
	Page<OrderDetails> findAllByUserId(Pageable pageable, @Param("user_id") Integer userId);
	
	
	@Query("SELECT d FROM OrderDetails d  where d.id=:id")
	OrderDetails getById(@Param("id") Long id);
	
	@Query("SELECT d FROM OrderDetails d  where d.id IN (:ids)")
	Page<OrderDetails> findAllByIds(Pageable pageable, @Param("ids") List<Long> ids);
	
	@Query("SELECT d FROM OrderDetails d order by d.id desc ")
	List<OrderDetails> fetchOrderDetailsInnerJoin();
	
	@Query("SELECT d FROM OrderDetails d  order by d.id desc  ")
	Page<OrderDetails> fetchOrderDetailsInnerJoin(Pageable pageable);  
	
	@Query("SELECT d FROM OrderDetails d  where (d.dbs_status!='Auftrag abgeschlosse' or d.dbs_status!='Order completed')")
	Page<OrderDetails> findAllOpenOrders(Pageable pageable);
	
	@Query("SELECT d FROM OrderDetails d  where (d.dbs_status!='Auftrag abgeschlosse' or d.dbs_status!='Order completed') and (d.dbs_status='FA in Bearbeitung' or d.dbs_status='FA in progress')")
	Page<OrderDetails> findAllOpenFAOrders(Pageable pageable);  
	
	@Query("SELECT d FROM OrderDetails d  where (d.dbs_status='FA erledigt - Bitte Ergebnis bewerte' or d.dbs_status='FA done - Please rate the result')")
	Page<OrderDetails> findEvaluationOrders(Pageable pageable);  

	@Query("SELECT d FROM OrderDetails d  where (d.dbs_status='FA erledigt - Bitte Ergebnis bewerte' or d.dbs_status='FA done - Please rate the result')")
	Page<OrderDetails> findEvaluationOrders(@Param("sCustomername") String sCustomername,
			@Param("sStatus") String sStatus, @Param("sPriority") String sPriority,
			@Param("sMaterial") String sMaterial, @Param("sElectricalError") String sElectricalError,
			@Param("sFailureMode") String sFailureMode, 
			@Param("sArchWaferBox") String sArchWaferBox, @Param("sArchPolyBox") String sArchPolyBox,
			Pageable pageable);
	
//	@Modifying
//	@Query("update OrderDetails d set d.user_id = :userid where d.id = :id")
//	int setUserIdForOrderDetails(@Param("userid") Integer userid, @Param("id") Long id);

	//dbs_material,dbs_prio,dbs_ag_name,dbs_status,dbs_elee,dbs_famo
}
