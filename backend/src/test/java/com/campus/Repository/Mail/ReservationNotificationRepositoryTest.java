package com.campus.Repository.Mail;

import com.campus.Repository.Reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ReservationNotificationRepositoryTest {
    @Autowired
    private ReservationNotificationRepository reservationNotificationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
}
