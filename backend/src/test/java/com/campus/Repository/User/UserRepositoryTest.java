package com.campus.Repository.User;

import com.campus.Classification.UserRole;
import com.campus.Entity.User.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void saveUser(){
        User user = new User("Hello","hello@gmail.com","pwdUser1`",UserRole.Student);
        try {
            User saveUser = userRepository.save(user);
            Assertions.assertThat(saveUser).isNotNull();
            Assertions.assertThat(saveUser.getUserId()).isGreaterThan(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
