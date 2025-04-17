package com.campus.Repository.User;

import com.campus.Entity.User.User;
import com.campus.Classification.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findUserByUserRole(UserRole userRole);
    Optional<User> findByEmailEqualsIgnoreCase(String email);
    Optional<User> findByUsernameEqualsIgnoreCase(String username);
    Optional<User> findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(String username, String email);
    @Query("select user from User user "+
            "where  (:userId    is null or user.userId          = :userId)  "+
            "and    (:username  is null or upper(user.username) like concat('%',upper(:username),'%'))"+
            "and    (:email     is null or upper(user.email)    like concat('%',upper(:email),'%'))"+
            "and    (:userRole  is null or user.userRole        = :userRole)"
    )
    List<User> findUserByFilter(@Param("userId")    Integer userId  ,
                                @Param("username")  String username ,
                                @Param("email")     String email    ,
                                @Param("userRole")  UserRole userRole);
}