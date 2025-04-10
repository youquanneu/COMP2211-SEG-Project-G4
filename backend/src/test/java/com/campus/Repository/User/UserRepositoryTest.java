package com.campus.Repository.User;

import com.campus.Classification.UserRole;
import com.campus.Entity.User.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void saveUser(){
        User user = new User("User1","user1@gmail.com","pwdUser1`",UserRole.Student);
        User saveUser = userRepository.save(user);
        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser.getUserId()).isGreaterThan(0);
    }
}
