package com.campus.Service.Event;

import com.campus.Entity.Event.EmergencyCase;
import com.campus.Repository.Event.EmergencyCaseRepository;
import com.campus.Service.Mail.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmergencyCaseService {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmergencyCaseRepository emergencyCaseRepository;
    public EmergencyCase getEmergencyCaseById(Integer id){
        Optional<EmergencyCase> emergencyCase = emergencyCaseRepository.findById(id);
        if (emergencyCase.isEmpty()){
            throw new RuntimeException("Emergency case not found");
        }
        return emergencyCase.get();
    }
    public EmergencyCase reportNewCase(EmergencyCase emergencyCase){
        emailSenderService.sendEmergencyCase(emergencyCase);
        return emergencyCaseRepository.save(emergencyCase);
    }
}
