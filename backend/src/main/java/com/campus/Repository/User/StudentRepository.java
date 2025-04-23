package com.campus.Repository.User;

import com.campus.Entity.User.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findByEmailEqualsIgnoreCase(String email);
}
