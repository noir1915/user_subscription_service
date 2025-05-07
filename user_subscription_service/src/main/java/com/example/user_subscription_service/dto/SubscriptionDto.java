package com.example.user_subscription_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubscriptionDto {
    private Long id;
    private String serviceName;
    private Long userId;
}