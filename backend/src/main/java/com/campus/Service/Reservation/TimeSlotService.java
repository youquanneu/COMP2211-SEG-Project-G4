package com.campus.Service.Reservation;

import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Reservation.TimeSlot;
import com.campus.Repository.Reservation.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    private List<TimeSlot> getTimeSlot(){
        return timeSlotRepository.findAll();
    }
    private void generateAllTimeSlot(){
        timeSlotRepository.saveAll(initialTimeSlot());
    }
    private List<TimeSlot> initialTimeSlot(){
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            LocalTime start = LocalTime.of(hour, 0);
            LocalTime end = LocalTime.of((hour + 1) % 24, 0);
            TimeSlot timeSlot = new TimeSlot(start, end);
            timeSlots.add(timeSlot);
        }
        return timeSlots;
    }
    public List<TimeSlot> getAvailableTimeSlots(List<Reservation> reservations) {
        List<TimeSlot> allSlots = initialTimeSlot();
        List<TimeSlot> availableSlots = new ArrayList<>();
        for (TimeSlot slot : allSlots) {
            boolean overlaps = false;
            for (Reservation reservation : reservations) {
                LocalTime resourceOpenTime = reservation.getResource().getOpenTime();
                LocalTime resourceCloseTime  = reservation.getResource().getCloseTime();
                if (slot.getStartingTime().isBefore(resourceOpenTime.plusSeconds(1)) ||
                        slot.getEndingTime().isAfter(resourceCloseTime.minusSeconds(1))){
                    overlaps = true;
                    break;
                }
                LocalTime reservationStarting = reservation.getReservationStarting().toLocalTime();
                LocalTime reservationEnding = reservation.getReservationEnding().toLocalTime();
                if (!(slot.getEndingTime().isBefore(reservationStarting.plusSeconds(1))
                        || slot.getStartingTime().isAfter(reservationEnding.minusSeconds(1)))) {
                    overlaps = true;
                    break;
                }
            }
            if (!overlaps) {
                availableSlots.add(slot);
            }
        }
        return availableSlots;
    }
}
