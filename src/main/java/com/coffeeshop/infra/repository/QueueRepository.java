package com.coffeeshop.infra.repository;

import com.coffeeshop.infra.repository.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    @Query("SELECT MAX(q.position) FROM Queue q WHERE q.shopId = :shopId")
    Optional<Integer> findMaxPositionByShopId(Long shopId);

    Optional<Queue> findFirstByShopIdAndCustomerIdOrderByPositionAsc(Long shopId, Long customerId);


}
