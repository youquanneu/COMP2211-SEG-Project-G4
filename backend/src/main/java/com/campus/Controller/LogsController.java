package com.campus.Controller;

import com.campus.DataTransferObject.Config.LogAction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class LogsController {
    private static final Logger logger = Logger.getLogger(LogsController.class.getName());
    @PostMapping("/logs")
    public ResponseEntity<?> logs(@RequestBody LogAction logAction) {
        logger.info("Logs : " + logAction);
//        try {
//            UserDTO userDTO = UserDTO.mapper(
//                    userService.login(
//                            loginRequest.getEmail(),
//                            loginRequest.getPassword()));
//            logger.info("Get userDTO" + userDTO.getUsername());
//            return ResponseEntity.ok(userDTO);
//        }catch (Exception e){
//            logger.info("Get exception : " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
        return ResponseEntity.ok("Received");
    }
}
