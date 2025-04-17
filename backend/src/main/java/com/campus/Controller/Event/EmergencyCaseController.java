package com.campus.Controller.Event;

import com.campus.DataTransferObject.Event.EmergencyCaseDTO;
import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.campus.Service.Event.EmergencyCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/admin/emergency")
public class EmergencyCaseController {
    private static final Logger logger = Logger.getLogger(EmergencyCaseController.class.getName());
    @Autowired
    private EmergencyCaseService emergencyCaseService;
    @GetMapping("/getAllEmergency")
    public ResponseEntity<?> getAllEmergency() {
        logger.info("Processing getAllEmergency");
        try {
            List<EmergencyCaseDTO> emergencyCaseDTOS = EmergencyCaseDTO.listMapper(emergencyCaseService.getAllEmergencyCase());
            logger.info("Get get resources" + emergencyCaseDTOS);
            return ResponseEntity.ok(emergencyCaseDTOS);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
