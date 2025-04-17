package com.campus.Repository.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Venue;
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
public class VenueRepositoryTest {
    @Autowired
    private VenueRepository venueRepository;

    @Test
    public void saveVenue() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Venue venue = new Venue(
                "Main Auditorium",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue
        );

        Venue savedVenue = venueRepository.save(venue);

        Assertions.assertThat(savedVenue).isNotNull();
        Assertions.assertThat(savedVenue.getResourceName()).isEqualTo("Main Auditorium");
        Assertions.assertThat(savedVenue.getResourceCategory()).isEqualTo(ResourceCategory.IndoorVenue);
        Assertions.assertThat(savedVenue.getRestriction()).isEqualTo(Restriction.NonRestriction);
    }
    @Test
    public void findVenueByName() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Venue venue = new Venue(
                "Main Auditorium",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue
        );

        venueRepository.save(venue);

        Venue foundVenue = venueRepository.findByResourceNameEqualsIgnoreCase("Main Auditorium").orElse(null);

        Assertions.assertThat(foundVenue).isNotNull();
        Assertions.assertThat(foundVenue.getResourceName()).isEqualTo("Main Auditorium");
    }
    @Test
    public void deleteVenueByName() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Venue venue = new Venue(
                "Main Auditorium",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue
        );

        venueRepository.save(venue);
        venueRepository.delete(venue);

        Venue deletedVenue = venueRepository.findByResourceNameEqualsIgnoreCase("Main Auditorium").orElse(null);

        Assertions.assertThat(deletedVenue).isNull();
    }
}
