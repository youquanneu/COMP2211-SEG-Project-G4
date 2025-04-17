package com.campus.Repository.Reservation;

import com.campus.Entity.Reservation.TimeSlot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TimeSlotRepositoryTest {
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Test
    public void saveTimeSlot() {
        LocalTime startingTime = LocalTime.of(9, 0);
        LocalTime endingTime = LocalTime.of(10, 0);

        TimeSlot timeSlot = new TimeSlot(startingTime, endingTime);

        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);

        Assertions.assertThat(savedTimeSlot).isNotNull();
        Assertions.assertThat(savedTimeSlot.getStartingTime()).isEqualTo(startingTime);
        Assertions.assertThat(savedTimeSlot.getEndingTime()).isEqualTo(endingTime);
    }
    @Test
    public void findTimeSlotById() {
        LocalTime startingTime = LocalTime.of(9, 0);
        LocalTime endingTime = LocalTime.of(10, 0);

        TimeSlot timeSlot = new TimeSlot(startingTime, endingTime);
        timeSlotRepository.save(timeSlot);

        TimeSlot foundTimeSlot = timeSlotRepository.findById(timeSlot.getTimeSlotId()).orElse(null);

        Assertions.assertThat(foundTimeSlot).isNotNull();
        Assertions.assertThat(foundTimeSlot.getStartingTime()).isEqualTo(startingTime);
        Assertions.assertThat(foundTimeSlot.getEndingTime()).isEqualTo(endingTime);
    }
    @Test
    public void deleteTimeSlotById() {
        LocalTime startingTime = LocalTime.of(9, 0);
        LocalTime endingTime = LocalTime.of(10, 0);

        TimeSlot timeSlot = new TimeSlot(startingTime, endingTime);
        timeSlotRepository.save(timeSlot);

        timeSlotRepository.delete(timeSlot);

        TimeSlot deletedTimeSlot = timeSlotRepository.findById(timeSlot.getTimeSlotId()).orElse(null);

        Assertions.assertThat(deletedTimeSlot).isNull();
    }
}
