package com.example.timecapsule.user.repository;

import com.example.timecapsule.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByUserNickname(String userNickname);
    Optional<User> findUserByUserEmail(String email);

    List<User> findByUserNicknameContaining(String userNickname);
    List<User> findByUserNicknameContainsIgnoreCase(String userNickname);

    boolean existsByUserNickname(String userNickname);
    boolean existsByUserId(String userId);
}
