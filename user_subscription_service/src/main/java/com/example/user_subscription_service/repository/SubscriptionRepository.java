package com.example.user_subscription_service.repository;

import com.example.user_subscription_service.dto.ServiceCountDto;
import com.example.user_subscription_service.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);

    @Query("SELECT new com.example.user_subscription_service.dto.ServiceCountDto(s.serviceName, COUNT(s.user.id)) " +
            "FROM Subscription s GROUP BY s.serviceName ORDER BY COUNT(s.user.id) DESC")
    List<ServiceCountDto> findTopSubscriptions(Pageable pageable);
}