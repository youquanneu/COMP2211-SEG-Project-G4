package com.campus.Repository.User;

import com.campus.Entity.User.User;
import com.campus.EntityClassification.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministrativeStaffRepository extends JpaRepository<User,Integer> {
    List<User> findUserByUserRole(UserRole userRole);
    Optional<User> findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(String username, String email);
}
