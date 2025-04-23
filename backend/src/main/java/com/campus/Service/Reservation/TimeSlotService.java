package com.campus.Service.Reservation;

import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Reservation.TimeSlot;
import com.campus.Entity.Resource.Resource;
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
    public TimeSlot saveTimeSlot(TimeSlot timeSlot){
        return timeSlotRepository.save(timeSlot);
    }
    public List<TimeSlot> getTimeSlot(){
        return timeSlotRepository.findAll();
    }
    public List<TimeSlot> getAvailableTimeSlots(Resource resource, List<Reservation> reservations) {
        List<TimeSlot> openSlots = openTime(resource);
        if(reservations == null){
            return openSlots;
        }
        else{
        List<TimeSlot> availableSlots = new ArrayList<>();
        for (TimeSlot slot : openSlots) {
            boolean overlaps = false;
            for (Reservation reservation : reservations) {
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
    private List<TimeSlot> openTime(Resource resource){
        List<TimeSlot> allSlots = getTimeSlot();
        if (resource.getOpenTime() == null){
            return allSlots;
        }else {
            List<TimeSlot> openSlots = new ArrayList<>();
            for (TimeSlot slot : allSlots) {
                boolean timeSlotBeforeOpen = slot.getEndingTime().isBefore(resource.getOpenTime().plusSeconds(1));
                boolean timeSlotAfterClose = slot.getStartingTime().isAfter(resource.getCloseTime().minusSeconds(1));
                if (!(timeSlotBeforeOpen || timeSlotAfterClose)) {
                    openSlots.add(slot);
                }
            }
            return openSlots;
        }
    }
}
