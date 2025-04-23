package com.campus.Repository.User;

import com.campus.Classification.UserRole;
import com.campus.Entity.User.AdministrativeStaff;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AdministrativeStaffRepositoryTest {
    @Autowired
    private AdministrativeStaffRepository administrativeStaffRepository;
    @Test
    public void saveAdministrativeStaff() {
        AdministrativeStaff adminStaff = new AdministrativeStaff("AdminJohn", "adminjohn@campus.com", "adminPass789");
        AdministrativeStaff savedAdminStaff = administrativeStaffRepository.save(adminStaff);

        Assertions.assertThat(savedAdminStaff).isNotNull();
        Assertions.assertThat(savedAdminStaff.getUserId()).isGreaterThan(0);
        Assertions.assertThat(savedAdminStaff.getUserRole()).isEqualTo(UserRole.AdministrativeStaff);
    }
    @Test
    public void findAdministrativeStaffByEmail() {
        AdministrativeStaff adminStaff = new AdministrativeStaff("AdminLucy", "adminlucy@campus.com", "adminPass101");
        administrativeStaffRepository.save(adminStaff);

        AdministrativeStaff found = administrativeStaffRepository.findByEmailEqualsIgnoreCase("adminlucy@campus.com").orElse(null);

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getUsername()).isEqualTo("AdminLucy");
    }
    @Test
    public void deleteAdministrativeStaffByEmail() {
        AdministrativeStaff staff = new AdministrativeStaff("Eve Admin", "eve.admin@university.com", "adminPass123");
        AdministrativeStaff savedStaff = administrativeStaffRepository.save(staff);

        administrativeStaffRepository.delete(savedStaff);

        AdministrativeStaff deletedStaff = administrativeStaffRepository.findByEmailEqualsIgnoreCase("eve.admin@university.com").orElse(null);
        Assertions.assertThat(deletedStaff).isNull();
    }
}
