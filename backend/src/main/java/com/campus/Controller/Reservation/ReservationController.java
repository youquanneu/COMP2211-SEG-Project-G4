package com.campus.Controller.Reservation;

import com.campus.DataTransferObject.Reservation.AvailableTimeRequest;
import com.campus.DataTransferObject.Reservation.ReservationDTO;
import com.campus.DataTransferObject.Reservation.ReservationRequest;
import com.campus.DataTransferObject.Reservation.TimeSlotDTO;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Service.Reservation.ReservationService;
import com.campus.Service.Resource.ResourceService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user/reservation")
public class ReservationController {
    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    @GetMapping("/getAvailableTimeSlot")
    public ResponseEntity<?> availableTime(@RequestBody AvailableTimeRequest availableTimeRequest){
        logger.info("processing : " );
        try {
            Resource resource = resourceService.getResourceByID(
                    availableTimeRequest.getResourceDTO().getResourceId());
            List<TimeSlotDTO> availableTime =
                    TimeSlotDTO.mapper(reservationService.availableTime(
                            resource, availableTimeRequest.getLocalDate()));
            return ResponseEntity.ok(availableTime);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/makeReservation")
    public ResponseEntity<?> booking(@RequestBody ReservationRequest reservationRequest) {
        logger.info("processing : " );
        try {
            User booker = userService.getUserByEmail(reservationRequest.getUserEmail());
            Resource resource = resourceService.getResourceByID(reservationRequest.getResourceDTO().getResourceId());
            LocalDateTime reservationStarting =
                    reservationRequest.getTimeSlotDTO().getStartingTime()
                            .atDate(reservationRequest.getReservationDate());
            LocalDateTime reservationEnding =
                    reservationRequest.getTimeSlotDTO().getEndingTime()
                            .atDate(reservationRequest.getReservationDate());
            Reservation reservation = reservationService.createNewReservation(booker,resource,reservationRequest.getPurpose(),reservationStarting,reservationEnding);
            return ResponseEntity.ok(ReservationDTO.mapper(reservation));
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
