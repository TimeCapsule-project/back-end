package com.example.timecapsule.user.repository;

import com.example.timecapsule.user.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findAuthByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Auth SET refreshToken= :refresh WHERE userId= :userId")
    Integer updateAuth(@Param("userId") String userId, @Param("refresh") String refresh);
    //TODO update time 반영안됨
}