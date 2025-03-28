package com.campus.Entity.User;

import com.campus.Classification.UserRole;

public class Lecturer extends User{
    public Lecturer(){}
    public Lecturer(String username, String email, String password){
        super(username, email, password, UserRole.Lecturer);
    }
}
