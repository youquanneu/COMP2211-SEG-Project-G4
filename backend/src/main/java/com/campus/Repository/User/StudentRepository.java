package com.campus.Repository.User;

import com.campus.Entity.User.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
