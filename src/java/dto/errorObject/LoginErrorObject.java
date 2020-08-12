/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.errorObject;

/**
 *
 * @author Mk
 */
public class LoginErrorObject {
    private String mainError, usernameError, passwordError;

    public String getMainError() {
        return mainError;
    }

    public void setMainError(String mainError) {
        this.mainError = mainError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }
    
    
}
