package com.campus.Repository.User;

import com.campus.Classification.UserRole;
import com.campus.Entity.User.Lecturer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LecturerRepositoryTest {
    @Autowired
    private LecturerRepository lecturerRepository;
    @Test
    public void saveLecturer() {
        Lecturer lecturer = new Lecturer("DrJohn", "john@lecturer.com", "lecturerPass");
        Lecturer saved = lecturerRepository.save(lecturer);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getUserId()).isGreaterThan(0);
        Assertions.assertThat(saved.getUserRole()).isEqualTo(UserRole.Lecturer);
    }
    @Test
    public void findLecturerByEmail() {
        Lecturer lecturer = new Lecturer("ProfJane", "jane@lecturer.com", "prof123");
        lecturerRepository.save(lecturer);

        Lecturer found = lecturerRepository.findByEmailEqualsIgnoreCase("jane@lecturer.com").orElse(null);

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getUsername()).isEqualTo("ProfJane");
    }
    @Test
    public void deleteLecturerByEmail() {
        Lecturer lecturer = new Lecturer("Prof. Mike", "mike.lecturer@university.com", "lecturerPass789");
        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        lecturerRepository.delete(savedLecturer);

        Lecturer deletedLecturer = lecturerRepository.findByEmailEqualsIgnoreCase("mike.lecturer@university.com").orElse(null);
        Assertions.assertThat(deletedLecturer).isNull();
    }
}
