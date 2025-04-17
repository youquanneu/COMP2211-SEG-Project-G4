package com.campus.Repository.Resource;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
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
public class EquipmentRepositoryTest {
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    public void saveEquipment() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Equipment equipment = new Equipment(
                "Projector",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "SN12345"
        );

        Equipment savedEquipment = equipmentRepository.save(equipment);

        Assertions.assertThat(savedEquipment).isNotNull();
        Assertions.assertThat(savedEquipment.getResourceName()).isEqualTo("Projector");
        Assertions.assertThat(savedEquipment.getResourceCategory()).isEqualTo(ResourceCategory.Equipment);
        Assertions.assertThat(savedEquipment.getRestriction()).isEqualTo(Restriction.NonRestriction);
        Assertions.assertThat(savedEquipment.getSerialNumber()).isEqualTo("SN12345");
    }
    @Test
    public void findEquipmentByName() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Equipment equipment = new Equipment(
                "Camera",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "SN54321"
        );

        equipmentRepository.save(equipment);

        Equipment foundEquipment = equipmentRepository.findByResourceNameEqualsIgnoreCase("Camera").orElse(null);

        Assertions.assertThat(foundEquipment).isNotNull();
        Assertions.assertThat(foundEquipment.getResourceName()).isEqualTo("Camera");
    }
    @Test
    public void deleteEquipmentByName() {
        LocalTime openTime = LocalTime.of(9, 0);
        LocalTime closeTime = LocalTime.of(18, 0);

        Equipment equipment = new Equipment(
                "Camera",
                openTime,
                closeTime,
                Restriction.NonRestriction,
                "SN54321"
        );

        equipmentRepository.save(equipment);
        equipmentRepository.delete(equipment);

        Equipment deletedEquipment = equipmentRepository.findByResourceNameEqualsIgnoreCase("Camera").orElse(null);

        Assertions.assertThat(deletedEquipment).isNull();
    }
}
