package com.example.user_subscription_service.repository;

import com.example.user_subscription_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
