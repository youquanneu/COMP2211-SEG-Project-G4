package com.campus.Repository.User;

import com.campus.Entity.User.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer,Integer> {
}
