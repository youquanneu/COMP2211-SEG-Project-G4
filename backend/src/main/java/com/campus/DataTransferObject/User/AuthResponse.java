package com.campus.DataTransferObject.User;

public class AuthResponse {
    public AuthResponse(){}
    public AuthResponse(String token,UserDTO userDTO){
        setToken(token);
        setUserDTO(userDTO);
    }
    private String token;
    private UserDTO userDTO;
    public String getToken() {
        return token;
    }
    public UserDTO getUserDTO() {
        return userDTO;
    }
    private void setToken(String token) {
        this.token = token;
    }
    private void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
