package com.warehouse.repository;

import com.warehouse.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update INVENTORY set stock =:newStock  where ART_ID = :art_id",nativeQuery = true)
    void updateStock(@Param("art_id") Integer art_id, @Param("newStock") Integer newStock);

}
