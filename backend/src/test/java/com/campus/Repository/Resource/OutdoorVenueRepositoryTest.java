package com.campus.Repository.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.OutdoorVenue;
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
public class OutdoorVenueRepositoryTest {
    @Autowired
    private OutdoorVenueRepository outdoorVenueRepository;

    @Test
    public void saveOutdoorVenue() {
        LocalTime openTime = LocalTime.of(6, 0);
        LocalTime closeTime = LocalTime.of(21, 0);

        OutdoorVenue outdoorVenue = new OutdoorVenue(
                "Central Park",
                openTime,
                closeTime,
                Restriction.ApprovalRequired,
                "Main Gate"
        );

        OutdoorVenue savedOutdoorVenue = outdoorVenueRepository.save(outdoorVenue);

        Assertions.assertThat(savedOutdoorVenue).isNotNull();
        Assertions.assertThat(savedOutdoorVenue.getResourceName()).isEqualTo("Central Park");
        Assertions.assertThat(savedOutdoorVenue.getResourceCategory()).isEqualTo(ResourceCategory.OutdoorVenue);
        Assertions.assertThat(savedOutdoorVenue.getRestriction()).isEqualTo(Restriction.ApprovalRequired);
        Assertions.assertThat(savedOutdoorVenue.getLocation()).isEqualTo("Main Gate");
    }
    @Test
    public void findOutdoorVenueByName() {
        LocalTime openTime = LocalTime.of(6, 0);
        LocalTime closeTime = LocalTime.of(21, 0);

        OutdoorVenue outdoorVenue = new OutdoorVenue(
                "Central Park",
                openTime,
                closeTime,
                Restriction.ApprovalRequired,
                "Main Gate"
        );

        outdoorVenueRepository.save(outdoorVenue);

        OutdoorVenue foundOutdoorVenue = outdoorVenueRepository.findByResourceNameEqualsIgnoreCase("Central Park").orElse(null);

        Assertions.assertThat(foundOutdoorVenue).isNotNull();
        Assertions.assertThat(foundOutdoorVenue.getResourceName()).isEqualTo("Central Park");
    }
    @Test
    public void deleteOutdoorVenueByName() {
        LocalTime openTime = LocalTime.of(6, 0);
        LocalTime closeTime = LocalTime.of(21, 0);

        OutdoorVenue outdoorVenue = new OutdoorVenue(
                "Central Park",
                openTime,
                closeTime,
                Restriction.ApprovalRequired,
                "Main Gate"
        );

        outdoorVenueRepository.save(outdoorVenue);
        outdoorVenueRepository.delete(outdoorVenue);

        OutdoorVenue deletedOutdoorVenue = outdoorVenueRepository.findByResourceNameEqualsIgnoreCase("Central Park").orElse(null);

        Assertions.assertThat(deletedOutdoorVenue).isNull();
    }
}
