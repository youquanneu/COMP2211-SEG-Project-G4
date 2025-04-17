package com.campus.Repository.Mail;

import com.campus.Repository.Event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class EventNotificationRepositoryTest {
    @Autowired
    private EventNotificationRepositoryTest eventNotificationRepositoryTest;
    @Autowired
    private EventRepository eventRepository;
}
