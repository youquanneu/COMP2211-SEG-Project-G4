package com.campus.Repository.Mail;

import com.campus.Entity.Mail.OneTimePassword;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class OneTimePasswordRepositoryTest {
    @Autowired
    private OneTimePasswordRepository oneTimePasswordRepository;
    @Test
    public void saveOneTimePassword() {
        String email = "user@example.com";
        OneTimePassword otp = new OneTimePassword(email);

        OneTimePassword savedOtp = oneTimePasswordRepository.save(otp);

        Assertions.assertThat(savedOtp).isNotNull();
        Assertions.assertThat(savedOtp.getOtpID()).isGreaterThan(0);
        Assertions.assertThat(savedOtp.getEmail()).isEqualTo(email);
        Assertions.assertThat(savedOtp.getOtpPrefix()).isNotNull();
        Assertions.assertThat(savedOtp.getOtpPrefix()).hasSize(3); // Prefix length should be 3
        Assertions.assertThat(savedOtp.getOtpSuffix()).isNotNull();
        Assertions.assertThat(savedOtp.getOtpSuffix()).hasSize(6); // Suffix length should be 6
    }
    @Test
    public void findOneTimePasswordByEmail() {
        String email = "user@example.com";
        OneTimePassword otp = new OneTimePassword(email);
        oneTimePasswordRepository.save(otp);

        OneTimePassword foundOtp = oneTimePasswordRepository.findByEmail(email).orElse(null);

        Assertions.assertThat(foundOtp).isNotNull();
        Assertions.assertThat(foundOtp.getEmail()).isEqualTo(email);
    }
    @Test
    public void deleteOneTimePasswordByEmail() {
        String email = "user@example.com";
        OneTimePassword otp = new OneTimePassword(email);
        OneTimePassword savedOtp = oneTimePasswordRepository.save(otp);

        oneTimePasswordRepository.delete(savedOtp);

        OneTimePassword deletedOtp = oneTimePasswordRepository.findByEmail(email).orElse(null);
        Assertions.assertThat(deletedOtp).isNull();
    }
}
