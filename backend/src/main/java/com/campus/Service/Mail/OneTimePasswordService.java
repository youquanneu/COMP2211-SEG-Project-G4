package com.campus.Service.Mail;

import com.campus.Entity.Mail.OneTimePassword;
import com.campus.Repository.Mail.OneTimePasswordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OneTimePasswordService {
    @Autowired
    private OneTimePasswordRepository oneTimePasswordRepository;
    public OneTimePassword generateOTP(String email){
        OneTimePassword oneTimePassword = new OneTimePassword(email);
        return oneTimePasswordRepository.save(oneTimePassword);
    }
    @Transactional
    public void matchOneTimePassword(String email, String prefix, String suffix){
        OneTimePassword otpSent = getOneTimePasswordByEmail(email);
        if (!(prefix.equalsIgnoreCase(otpSent.getOtpPrefix())
                && suffix.equalsIgnoreCase(otpSent.getOtpSuffix()))) {
            throw new RuntimeException("OTP not matches");
        }
        System.out.println("Delete by email :" + email);
        deleteAfterVerifyOTP(email);
    }
    @Transactional
    public void deleteAfterVerifyOTP(String email){
        oneTimePasswordRepository.deleteAllByEmail(email);
    }
    @Transactional
    public void deleteExpiredOTP(){
        oneTimePasswordRepository.deleteExpired(LocalDateTime.now().minusMinutes(5));
    }
    @Transactional
    public OneTimePassword getOneTimePasswordByEmail(String email){
        Optional<OneTimePassword> oneTimePassword = oneTimePasswordRepository.getTopByEmailOrderByCreatedTimeDesc(email);
        if (oneTimePassword.isEmpty()){
            throw new RuntimeException("Email not matches or OTP expiry");
        }
        System.out.println(oneTimePassword.get());
        return oneTimePassword.get();
    }
}
