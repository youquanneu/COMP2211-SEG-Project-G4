package com.campus.Repository.User;

import com.campus.Entity.User.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer,Integer> {
    Optional<Lecturer> findByEmailEqualsIgnoreCase(String email);
}
