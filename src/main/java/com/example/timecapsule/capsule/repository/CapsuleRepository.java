package com.example.timecapsule.capsule.repository;

import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule,Long> {
    Optional<Capsule> findCapsuleByCapsuleId(Long capsuleId);
    List<Capsule> findCapsulesByRecipient_RecipientIdOrderByCreatedAtDesc(Long recipientid);
    List<Capsule> findCapsulesBySenderId(String senderId);
}
