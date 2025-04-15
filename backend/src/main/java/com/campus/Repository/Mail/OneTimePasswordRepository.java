package com.campus.Repository.Mail;

import com.campus.Entity.Mail.OneTimePassword;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword,Integer> {
    @Transactional
    void deleteAllByEmail(String email);
    @Modifying
    @Transactional
    @Query("DELETE FROM OneTimePassword otp WHERE otp.createdTime <= :time")
    void deleteExpired(@Param("time") LocalDateTime time);
    Optional<OneTimePassword> getTopByEmailOrderByCreatedTimeDesc(String email);
}
