package Entities;
/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This is the data needed to save the User into the database
 *      it will be called primarily during registration and login
 */

public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String contactNo;
    private Role role;
    public enum Role {
        // only 2 roles can be defined at this point so we will use enum for role
        Admin,
        User
    }

    // getters and setters to access class fields
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
