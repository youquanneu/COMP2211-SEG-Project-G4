package com.campus.Controller.Resource;

import com.campus.DataTransferObject.Resource.VenueDTO;
import com.campus.Service.Resource.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("user/venue")
public class VenueController {
    private static final Logger logger = Logger.getLogger(VenueController.class.getName());
    @Autowired
    private VenueService venueService;
    @GetMapping("/getAllVenue")
    public ResponseEntity<?> getAllResource() {
        logger.info("Processing getAllVenue ");
        try {
            List<VenueDTO> venueDTOS = VenueDTO.venueListMapper(venueService.getAllVenue());
            logger.info("Get venues");
            return ResponseEntity.ok(venueDTOS);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
