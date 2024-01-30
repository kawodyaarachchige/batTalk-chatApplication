package org.example.dto;

import java.util.Arrays;

public class UserDTO {
    private String email;
    private String name;
    private String password;
    private byte[] profilePic;
    public UserDTO(){}

    public UserDTO(String email, String name, String password, byte[] profilePic) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.profilePic = profilePic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", profilePic=" + Arrays.toString(profilePic) +
                '}';
    }
}
