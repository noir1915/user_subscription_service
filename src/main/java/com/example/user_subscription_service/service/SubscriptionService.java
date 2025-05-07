package com.example.user_subscription_service.service;

import com.example.user_subscription_service.dto.ServiceCountDto;
import com.example.user_subscription_service.dto.SubscriptionDto;
import com.example.user_subscription_service.exception.ResourceNotFoundException;
import com.example.user_subscription_service.model.Subscription;
import com.example.user_subscription_service.model.User;
import com.example.user_subscription_service.repository.SubscriptionRepository;
import com.example.user_subscription_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService implements CRUDService<SubscriptionDto> {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public Subscription addSubscription(Long userId, Subscription subscription) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        subscription.setUser(user);

        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getSubscriptions(Long userId) {

        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public SubscriptionDto getById(Long id) {
        Subscription subscription =
                subscriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        return mapToDto(subscription);
    }

    @Override
    public Collection<SubscriptionDto> getAll() {
        log.info("Get all subscriptions");
        return subscriptionRepository.findAll()
                .stream()
                .map(SubscriptionService::mapToDto)
                .toList();
    }

    @Override
    public void create(SubscriptionDto subscriptionDto) {
        log.info("Create");
        Subscription subscription = mapToEntity(subscriptionDto);
        Long userId = subscriptionDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void update(SubscriptionDto subscriptionDto) {
        log.info("Update " + subscriptionDto.getId());
        Subscription subscription = subscriptionRepository.findById(subscriptionDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionDto.getId()));
        Long userId = subscriptionDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        subscription.setServiceName(subscriptionDto.getServiceName());
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete " + id);
        subscriptionRepository.deleteById(id);
    }

    public List<ServiceCountDto> getTopSubscriptions() {
        return subscriptionRepository.findTopSubscriptions(PageRequest.of(0, 3));
    }


    public static SubscriptionDto mapToDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setServiceName(subscription.getServiceName());
        subscriptionDto.setUserId(subscription.getUser().getId());
        return subscriptionDto;
    }

    public static Subscription mapToEntity(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionDto.getId());
        subscription.setServiceName(subscriptionDto.getServiceName());
        return subscription;
    }
}