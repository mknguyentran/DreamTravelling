/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author Mk
 */
public class AccountDTO implements Serializable{
    private String email, name, role, password;

    public AccountDTO(String email, String name, String role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public AccountDTO(String email, String name, String role, String password) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
