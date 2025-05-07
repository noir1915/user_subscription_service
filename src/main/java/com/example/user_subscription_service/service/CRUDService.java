package com.example.user_subscription_service.service;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface CRUDService<T> {

    T getById(Long id);

    Collection<T> getAll();

    void create(T t);

    void update(T t);

    void delete(Long id);
}