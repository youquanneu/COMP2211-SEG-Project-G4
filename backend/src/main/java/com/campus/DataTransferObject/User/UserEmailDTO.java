package com.campus.DataTransferObject.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEmailDTO {
    public UserEmailDTO(){}
    public UserEmailDTO(String email){
        setEmail(email);
    }
    @JsonProperty
    private String email;
    public String getEmail() {
        return email;
    }
    private void setEmail(String email) {
        this.email = email;
    }

}
