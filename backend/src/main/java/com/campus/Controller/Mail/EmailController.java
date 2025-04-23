package com.campus.Controller.Mail;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping
public class EmailController {
    private static final Logger logger = Logger.getLogger(EmailController.class.getName());
}
