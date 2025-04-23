package com.campus.Repository.Event;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Classification.Status;
import com.campus.Entity.Event.EmergencyCase;
import com.campus.Entity.Resource.Venue;
import com.campus.Repository.Resource.VenueRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class EmergencyCaseRepositoryTest {
    @Autowired
    private EmergencyCaseRepository emergencyCaseRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Test
    public void saveEmergencyCase() {
        Venue venue = new Venue(
                "Lecture Hall",
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue);
        venueRepository.save(venue);

        EmergencyCase emergencyCase = new EmergencyCase(venue, "Fire", "Small fire in the kitchen", "john.doe@example.com");

        EmergencyCase savedEmergencyCase = emergencyCaseRepository.save(emergencyCase);

        Assertions.assertThat(savedEmergencyCase).isNotNull();
        Assertions.assertThat(savedEmergencyCase.getEmergencyCaseId()).isGreaterThan(0);
        Assertions.assertThat(savedEmergencyCase.getEmergencyCase()).isEqualTo("Fire");
        Assertions.assertThat(savedEmergencyCase.getReporterEmail()).isEqualTo("john.doe@example.com");
        Assertions.assertThat(savedEmergencyCase.getStatus()).isEqualTo(Status.Pending); // Default status
    }

    @Test
    public void findEmergencyCaseById() {
        Venue venue = new Venue(
                "Lab 101",
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue);
        venueRepository.save(venue);

        EmergencyCase emergencyCase = new EmergencyCase(venue, "Power Outage", "Power failure in the laboratory", "jane.doe@example.com");
        emergencyCaseRepository.save(emergencyCase);

        EmergencyCase foundEmergencyCase = emergencyCaseRepository.findById(emergencyCase.getEmergencyCaseId()).orElse(null);

        Assertions.assertThat(foundEmergencyCase).isNotNull();
        Assertions.assertThat(foundEmergencyCase.getEmergencyCase()).isEqualTo("Power Outage");
        Assertions.assertThat(foundEmergencyCase.getReporterEmail()).isEqualTo("jane.doe@example.com");
        Assertions.assertThat(foundEmergencyCase.getStatus()).isEqualTo(Status.Pending);
    }

    @Test
    public void solveEmergencyCase() {
        Venue venue = new Venue(
                "Outdoor Arena",
                LocalTime.of(10, 0),
                LocalTime.of(20, 0),
                Restriction.NonRestriction,
                ResourceCategory.OutdoorVenue);
        venueRepository.save(venue);

        EmergencyCase emergencyCase = new EmergencyCase(venue, "Medical Emergency", "Person collapsed during an event", "alice.smith@example.com");
        emergencyCaseRepository.save(emergencyCase);

        emergencyCase.solvedEmergencyCase();
        EmergencyCase updatedEmergencyCase = emergencyCaseRepository.save(emergencyCase);

        Assertions.assertThat(updatedEmergencyCase).isNotNull();
        Assertions.assertThat(updatedEmergencyCase.getStatus()).isEqualTo(Status.Solved);
    }

    @Test
    public void deleteEmergencyCaseById() {
        Venue venue = new Venue(
                "Conference Room",
                LocalTime.of(8, 30),
                LocalTime.of(18, 0),
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue);
        venueRepository.save(venue);

        EmergencyCase emergencyCase = new EmergencyCase(venue, "Accident", "A person fell from the stairs", "bob.jones@example.com");
        EmergencyCase savedEmergencyCase = emergencyCaseRepository.save(emergencyCase);

        emergencyCaseRepository.delete(savedEmergencyCase);

        EmergencyCase deletedEmergencyCase = emergencyCaseRepository.findById(savedEmergencyCase.getEmergencyCaseId()).orElse(null);
        Assertions.assertThat(deletedEmergencyCase).isNull();
    }
}
