package com.internous.ecsite.model.dao;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.internous.ecsite.model.entity.Purchase;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	
	@Query(value="select * from purchase p " +
			"inner join goods g " +
			"on p.goods_id = g.id " +
			"where created_at = ( " +
			"select max(created_at) from purchase p where p.user_id = :userId)",
			nativeQuery=true)
	List<Purchase> findHistory(@Param("userId") long userId);
	
	@Query(value="insert into purchase (user_id, goods_id, goods_name, item_count, total, created_at )" +
				"values (?1, ?2, ?3, ?4, ?5, now())", nativeQuery=true)
	@Transactional
	@Modifying
	void persist(@Param("userId") long userId,
				 @Param("goodsId") long goodsId,
				 @Param("goodsName") String goodsName,
				 @Param("itemCount") long itemCount,
				 @Param("total") long total);
}
