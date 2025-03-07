package com.campus.Repository;

import com.campus.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsernameEqualsIgnoreCase(String username);
}
