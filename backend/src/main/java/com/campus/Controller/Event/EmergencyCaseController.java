package com.campus.Controller.Event;

import com.campus.DataTransferObject.Event.EmergencyCaseDTO;
import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.campus.Entity.Event.EmergencyCase;
import com.campus.Entity.Resource.Venue;
import com.campus.Service.Event.EmergencyCaseService;
import com.campus.Service.Resource.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("")
public class EmergencyCaseController {
    private static final Logger logger = Logger.getLogger(EmergencyCaseController.class.getName());
    @Autowired
    private EmergencyCaseService emergencyCaseService;
    @Autowired
    private VenueService venueService;
    @GetMapping("/admin/emergency/getAllEmergency")
    public ResponseEntity<?> getAllEmergency() {
        logger.info("Processing getAllEmergency");
        try {
            List<EmergencyCaseDTO> emergencyCaseDTOS = EmergencyCaseDTO.listMapper(emergencyCaseService.getAllEmergencyCase());
            logger.info("Get emergencies" + emergencyCaseDTOS);
            return ResponseEntity.ok(emergencyCaseDTOS);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/emergency/reportEmergency")
    public ResponseEntity<?> reportEmergency(@RequestBody EmergencyCaseDTO emergencyCaseDTO){
        logger.info("Processing reportEmergency");
        try {
            EmergencyCase emergencyCase = new EmergencyCase(
                    (Venue) venueService.getResourceByID(emergencyCaseDTO.getLocation().getResourceId()),
                    null, emergencyCaseDTO.getDescription(), emergencyCaseDTO.getReporterEmail());
            emergencyCaseService.reportNewCase(emergencyCase);
            logger.info("Report emergency" + emergencyCaseDTO);
            return ResponseEntity.ok(emergencyCaseDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(e.getMessage());
        }
    }
}
