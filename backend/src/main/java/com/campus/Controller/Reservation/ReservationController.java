package com.campus.Controller.Reservation;

import com.campus.DataTransferObject.Reservation.AvailableTimeRequest;
import com.campus.DataTransferObject.Reservation.TimeSlotDTO;
import com.campus.Service.Reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user/reservation")
public class ReservationController {
    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());
    @Autowired
    private ReservationService reservationService;
    @GetMapping("getAvailableTimeSlot")
    public ResponseEntity<?> availableTime(){
        logger.info("processing : " );
        try {
            List<TimeSlotDTO> availableTime = new ArrayList<>();
            availableTime.add(new TimeSlotDTO(LocalTime.of(7,0),LocalTime.of(8,0)));
            return ResponseEntity.ok(availableTime);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("")
    public ResponseEntity<?> booking() {
        logger.info("processing : " );
        try {
            return ResponseEntity.ok("");
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
