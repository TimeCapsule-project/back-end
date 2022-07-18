package com.example.timecapsule.user.service;

import com.example.timecapsule.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserRepository userRepository= Mockito.mock(UserRepository.class);


    @Test
    void register() {
    }
}