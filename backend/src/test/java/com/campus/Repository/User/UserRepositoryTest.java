package com.campus.Repository.User;

import com.campus.Classification.UserRole;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.Lecturer;
import com.campus.Entity.User.Student;
import com.campus.Entity.User.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void saveUser(){
        User user = new User("StudentAssociation","stdAcss@gmail.com","Password",UserRole.Organization);
        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser.getUserId()).isGreaterThan(0);
    }
    @Test
    public void findUserByEmail() {
        User userToSave = new User("TestUser", "test@example.com", "SecurePass", UserRole.Organization);
        userRepository.save(userToSave);
        User foundUser = userRepository.findByEmailEqualsIgnoreCase("test@example.com").orElse(null);

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("TestUser");
        Assertions.assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
        Assertions.assertThat(foundUser.getUserRole()).isEqualTo(UserRole.Organization);
    }
    @Test
    public void deleteUserById() {
        User user = new User("DeleteUser", "delete@domain.com", "pass123", UserRole.Student);
        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getUserId());
        boolean exists = userRepository.findById(savedUser.getUserId()).isPresent();

        Assertions.assertThat(exists).isFalse();
    }
    @Test
    public void saveStudentThroughUserRepository() {
        Student student = new Student("StudentUser", "student@mail.com", "s3cret");
        User savedUser = userRepository.save(student);

        Assertions.assertThat(savedUser).isInstanceOf(Student.class);
        Assertions.assertThat(savedUser.getUserRole()).isEqualTo(UserRole.Student);
    }
    @Test
    public void saveLecturerThroughUserRepository() {
        Lecturer lecturer = new Lecturer("LecturerUser", "lecturer@mail.com", "teachStrong");
        User savedUser = userRepository.save(lecturer);

        Assertions.assertThat(savedUser).isInstanceOf(Lecturer.class);
        Assertions.assertThat(savedUser.getUserRole()).isEqualTo(UserRole.Lecturer);
    }
    @Test
    public void saveAdministrativeStaffThroughUserRepository() {
        AdministrativeStaff adminStaff = new AdministrativeStaff("AdminUser", "admin@mail.com", "adminPass123");
        User savedUser = userRepository.save(adminStaff);

        Assertions.assertThat(savedUser).isInstanceOf(AdministrativeStaff.class);
        Assertions.assertThat(savedUser.getUserRole()).isEqualTo(UserRole.AdministrativeStaff);
    }
}
