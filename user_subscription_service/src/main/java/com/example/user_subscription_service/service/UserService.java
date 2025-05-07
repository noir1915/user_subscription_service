package com.example.user_subscription_service.service;

import com.example.user_subscription_service.dto.SubscriptionDto;
import com.example.user_subscription_service.dto.UserDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements CRUDService<UserDto> {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public UserDto getById(Long id) {
        log.info("Get User by id: " + id);
        return mapToDto(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    @Override
    public Collection<UserDto> getAll() {
        log.info("Get all Users");
        return userRepository.findAll()
                .stream()
                .map(UserService::mapToDto)
                .toList();
    }

    @Override
    public void create(UserDto userDto) {
        log.info("Create User: " + userDto);
        User user = mapToEntity(userDto);
        User savedUser = userRepository.save(user);
        if (userDto.getSubscriptions() != null) {
            for (SubscriptionDto subscriptionDto : userDto.getSubscriptions()) {
                Subscription subscription = new Subscription();
                subscription.setServiceName(subscriptionDto.getServiceName());
                subscription.setUser(savedUser);
                subscriptionRepository.save(subscription);
            }
        }
    }

    @Override
    public void update(UserDto userDto) {
        userRepository.save(mapToEntity(userDto));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public static User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());

        if (userDto.getSubscriptions() != null) {
            List<Subscription> subscriptions = userDto.getSubscriptions()
                    .stream()
                    .map(subscriptionDto -> {
                        Subscription subscription = new Subscription();
                        subscription.setServiceName(subscriptionDto.getServiceName());
                        return subscription;
                    })
                    .collect(Collectors.toList());
            user.setSubscriptions(subscriptions);
        }

        return user;
    }

    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;

    }
}
