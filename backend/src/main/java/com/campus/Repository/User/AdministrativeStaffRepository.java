package com.campus.Repository.User;

import com.campus.Entity.User.AdministrativeStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrativeStaffRepository extends JpaRepository<AdministrativeStaff,Integer> {
    Optional<AdministrativeStaff> findByEmailEqualsIgnoreCase(String email);
}
