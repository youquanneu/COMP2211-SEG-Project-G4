package com.campus.DataTransferObject.Main;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailDTO {
    public EmailDTO(){}
    public EmailDTO(String email){
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
