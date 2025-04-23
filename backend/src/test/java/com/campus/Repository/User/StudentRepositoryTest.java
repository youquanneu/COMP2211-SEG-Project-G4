package com.campus.Repository.User;

import com.campus.Classification.UserRole;
import com.campus.Entity.User.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Test
    public void saveStudent() {
        Student student = new Student("AliceStudent", "alice@student.com", "password123");
        Student savedStudent = studentRepository.save(student);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getUserId()).isGreaterThan(0);
        Assertions.assertThat(savedStudent.getUserRole()).isEqualTo(UserRole.Student);
    }
    @Test
    public void findStudentByEmail() {
        Student student = new Student("BobStudent", "bob@student.com", "pass456");
        studentRepository.save(student);

        Student found = studentRepository.findByEmailEqualsIgnoreCase("bob@student.com").orElse(null);

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getUsername()).isEqualTo("BobStudent");
    }
    @Test
    public void deleteStudentByEmail() {
        Student student = new Student("CharlieStudent", "charlie@student.com", "charlie123");
        Student savedStudent = studentRepository.save(student);

        studentRepository.delete(savedStudent);

        Student deletedStudent = studentRepository.findByEmailEqualsIgnoreCase("charlie@student.com").orElse(null);
        Assertions.assertThat(deletedStudent).isNull();
    }
}
