package com.campus.Repository.User;

import com.campus.Entity.User.User;
import com.campus.EntityClassification.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsernameEqualsIgnoreCase(String username);
    Optional<User> findByEmailEqualsIgnoreCase(String email);
}
