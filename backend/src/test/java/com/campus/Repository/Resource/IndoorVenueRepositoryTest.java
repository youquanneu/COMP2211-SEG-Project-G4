package com.campus.Repository.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.IndoorVenue;
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
public class IndoorVenueRepositoryTest {
    @Autowired
    private IndoorVenueRepository indoorVenueRepository;

    @Test
    public void saveIndoorVenue() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        IndoorVenue indoorVenue = new IndoorVenue(
                "Conference Room A",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "Building 1",
                "Room 101"
        );

        IndoorVenue savedIndoorVenue = indoorVenueRepository.save(indoorVenue);

        Assertions.assertThat(savedIndoorVenue).isNotNull();
        Assertions.assertThat(savedIndoorVenue.getResourceName()).isEqualTo("Conference Room A");
        Assertions.assertThat(savedIndoorVenue.getResourceCategory()).isEqualTo(ResourceCategory.IndoorVenue);
        Assertions.assertThat(savedIndoorVenue.getRestriction()).isEqualTo(Restriction.NonRestriction);
        Assertions.assertThat(savedIndoorVenue.getBuilding()).isEqualTo("Building 1");
        Assertions.assertThat(savedIndoorVenue.getRoomNumber()).isEqualTo("Room 101");
    }
    @Test
    public void findIndoorVenueByName() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        IndoorVenue indoorVenue = new IndoorVenue(
                "Conference Room A",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "Building 1",
                "Room 101"
        );

        indoorVenueRepository.save(indoorVenue);

        IndoorVenue foundIndoorVenue = indoorVenueRepository.findByResourceNameEqualsIgnoreCase("Conference Room A").orElse(null);

        Assertions.assertThat(foundIndoorVenue).isNotNull();
        Assertions.assertThat(foundIndoorVenue.getResourceName()).isEqualTo("Conference Room A");
    }
    @Test
    public void deleteIndoorVenueByName() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        IndoorVenue indoorVenue = new IndoorVenue(
                "Conference Room A",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "Building 1",
                "Room 101"
        );

        indoorVenueRepository.save(indoorVenue);
        indoorVenueRepository.delete(indoorVenue);

        IndoorVenue deletedIndoorVenue = indoorVenueRepository.findByResourceNameEqualsIgnoreCase("Conference Room A").orElse(null);

        Assertions.assertThat(deletedIndoorVenue).isNull();
    }
}
