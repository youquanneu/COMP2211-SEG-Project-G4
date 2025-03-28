package com.campus.Entity.User;

import com.campus.Classification.UserRole;

public class Student extends User{
    public Student(){}
    public Student(String username, String email, String password){
        super(username, email, password,UserRole.Student);
    }
}
