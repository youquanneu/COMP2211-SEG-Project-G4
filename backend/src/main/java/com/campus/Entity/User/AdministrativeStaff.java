package com.campus.Entity.User;

import com.campus.Classification.UserRole;
import jakarta.persistence.Entity;

@Entity
public class AdministrativeStaff extends User {
    public AdministrativeStaff(){}
    public AdministrativeStaff(String username, String email, String password){
        super(username, email, password, UserRole.AdministrativeStaff);
    }
}
