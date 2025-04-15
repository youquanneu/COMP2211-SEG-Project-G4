package com.campus.Controller;

import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Service.Resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user/resource")
public class ResourceController {
    private static final Logger logger = Logger.getLogger(ResourceController.class.getName());
    @Autowired
    private ResourceService resourceService;
    @GetMapping("/getAllResource")
    public ResponseEntity<?> login() {
        logger.info("Getting resources processing : ");
        try {
            List<ResourceDTO> resourceDTOS = resourceService.getAllResourceDTO();
            logger.info("Get get resources" + resourceDTOS);
            return ResponseEntity.ok(resourceDTOS);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
