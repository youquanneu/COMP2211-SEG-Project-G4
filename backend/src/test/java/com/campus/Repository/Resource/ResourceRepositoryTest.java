package com.campus.Repository.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.OutdoorVenue;
import com.campus.Entity.Resource.Resource;
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
public class ResourceRepositoryTest {
    @Autowired
    private ResourceRepository resourceRepository;
    @Test
    public void saveResource() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Resource resource = new Resource(
                "GeneralResource",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                ResourceCategory.IndoorVenue
        );
        Resource savedResource = resourceRepository.save(resource);

        Assertions.assertThat(savedResource).isNotNull();
        Assertions.assertThat(savedResource.getResourceName()).isEqualTo("GeneralResource");
        Assertions.assertThat(savedResource.getResourceCategory()).isEqualTo(ResourceCategory.IndoorVenue);
        Assertions.assertThat(savedResource.getRestriction()).isEqualTo(Restriction.NonRestriction);
        Assertions.assertThat(savedResource.getOpenTime()).isEqualTo(openTime);
        Assertions.assertThat(savedResource.getCloseTime()).isEqualTo(closeTime);
    }
    @Test
    public void saveIndoorVenueThroughResourceRepository() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        IndoorVenue indoorVenue = new IndoorVenue(
                "ConferenceRoom A",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "Building 1",
                "Room 101"
        );

        Resource savedResource = resourceRepository.save(indoorVenue);

        Assertions.assertThat(savedResource).isNotNull();
        Assertions.assertThat(savedResource.getResourceName()).isEqualTo("ConferenceRoom A");
        Assertions.assertThat(savedResource.getResourceCategory()).isEqualTo(ResourceCategory.IndoorVenue);
        Assertions.assertThat(savedResource.getRestriction()).isEqualTo(Restriction.NonRestriction);
        Assertions.assertThat(savedResource.getOpenTime()).isEqualTo(openTime);
        Assertions.assertThat(savedResource.getCloseTime()).isEqualTo(closeTime);
    }
    @Test
    public void saveOutdoorVenueThroughResourceRepository() {
        LocalTime openTime = LocalTime.of(6, 0);
        LocalTime closeTime = LocalTime.of(21, 0);

        OutdoorVenue outdoorVenue = new OutdoorVenue(
                "Central Park",
                openTime,
                closeTime,
                Restriction.ApprovalRequired,
                "Main Gate"
        );

        Resource savedResource = resourceRepository.save(outdoorVenue);

        Assertions.assertThat(savedResource).isNotNull();
        Assertions.assertThat(savedResource.getResourceName()).isEqualTo("Central Park");
        Assertions.assertThat(savedResource.getResourceCategory()).isEqualTo(ResourceCategory.OutdoorVenue);
        Assertions.assertThat(savedResource.getRestriction()).isEqualTo(Restriction.ApprovalRequired);
        Assertions.assertThat(savedResource.getOpenTime()).isEqualTo(openTime);
        Assertions.assertThat(savedResource.getCloseTime()).isEqualTo(closeTime);
    }
    @Test
    public void saveEquipmentThroughResourceRepository() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Equipment equipment = new Equipment(
                "Projector",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "PRJ12345"
        );

        Resource savedResource = resourceRepository.save(equipment);

        Assertions.assertThat(savedResource).isNotNull();
        Assertions.assertThat(savedResource.getResourceName()).isEqualTo("Projector");
        Assertions.assertThat(savedResource.getResourceCategory()).isEqualTo(ResourceCategory.Equipment);
        Assertions.assertThat(savedResource.getRestriction()).isEqualTo(Restriction.NonRestriction);
        Assertions.assertThat(savedResource.getOpenTime()).isEqualTo(openTime);
        Assertions.assertThat(savedResource.getCloseTime()).isEqualTo(closeTime);
    }
}
