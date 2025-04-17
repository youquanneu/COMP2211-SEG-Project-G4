package com.campus.Repository.Reservation;

import com.campus.Classification.Purpose;
import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Classification.Status;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import com.campus.Repository.Resource.ResourceRepository;
import com.campus.Repository.User.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.LocalTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    public void saveReservation() {
        User booker = new Student("testUser1", "testuser1@mail.com", "password");
        userRepository.save(booker);

        Resource resource = new Resource(
                "TestResource",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue);
        resourceRepository.save(resource);

        LocalDateTime startTime = LocalDateTime.of(2025, 5, 1, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 1, 11, 0);

        Reservation reservation = new Reservation(booker, resource, Purpose.Meeting, startTime, endTime);

        Reservation savedReservation = reservationRepository.save(reservation);

        Assertions.assertThat(savedReservation).isNotNull();
        Assertions.assertThat(savedReservation.getReservationId()).isGreaterThan(0);
        Assertions.assertThat(savedReservation.getBooker()).isEqualTo(booker);
        Assertions.assertThat(savedReservation.getResource()).isEqualTo(resource);
        Assertions.assertThat(savedReservation.getPurpose()).isEqualTo(Purpose.Meeting);
        Assertions.assertThat(savedReservation.getReservationStarting()).isEqualTo(startTime);
        Assertions.assertThat(savedReservation.getReservationEnding()).isEqualTo(endTime);
        Assertions.assertThat(savedReservation.getStatus()).isEqualTo(Status.Approved);
    }

    // Test case for finding a reservation by ID
    @Test
    public void findReservationById() {
        User booker = new Student("testUser2", "testuser2@mail.com", "password");
        userRepository.save(booker);

        Resource resource = new Resource("TestResource", LocalTime.of(9, 0), LocalTime.of(17, 0), Restriction.NonRestriction, ResourceCategory.IndoorVenue);
        resourceRepository.save(resource);

        LocalDateTime startTime = LocalDateTime.of(2025, 5, 1, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 1, 11, 0);

        Reservation reservation = new Reservation(booker, resource, Purpose.Meeting, startTime, endTime);
        Reservation savedReservation = reservationRepository.save(reservation);

        Reservation foundReservation = reservationRepository.findById(savedReservation.getReservationId()).orElse(null);

        Assertions.assertThat(foundReservation).isNotNull();
        Assertions.assertThat(foundReservation.getReservationId()).isEqualTo(savedReservation.getReservationId());
    }
    @Test
    public void deleteReservation() {
        User booker = new Student("testUser3", "testuser3@mail.com", "password");
        userRepository.save(booker);

        Resource resource = new Resource("TestResource", LocalTime.of(9, 0), LocalTime.of(17, 0), Restriction.NonRestriction, ResourceCategory.IndoorVenue);
        resourceRepository.save(resource);

        LocalDateTime startTime = LocalDateTime.of(2025, 5, 1, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 1, 11, 0);

        Reservation reservation = new Reservation(booker, resource, Purpose.Meeting, startTime, endTime);
        Reservation savedReservation = reservationRepository.save(reservation);

        reservationRepository.delete(savedReservation);

        Reservation deletedReservation = reservationRepository.findById(savedReservation.getReservationId()).orElse(null);

        Assertions.assertThat(deletedReservation).isNull();
    }
}
