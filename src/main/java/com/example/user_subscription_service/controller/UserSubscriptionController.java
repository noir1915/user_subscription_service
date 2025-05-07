package com.example.user_subscription_service.controller;

import com.example.user_subscription_service.dto.SubscriptionDto;
import com.example.user_subscription_service.model.Subscription;
import com.example.user_subscription_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{userId}/subscriptions")
public class UserSubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> addSubscription(@PathVariable Long userId,
                                                        @RequestBody Subscription subscription) {
        Subscription createdSubscription = subscriptionService.addSubscription(userId, subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions(@PathVariable Long userId) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @PutMapping
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDto subscriptionDto) {
        subscriptionDto.setId(id);
        subscriptionService.update(subscriptionDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long userId, @PathVariable Long subId) {
        subscriptionService.delete(subId);
        return ResponseEntity.noContent().build();
    }
}
