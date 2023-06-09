package models.view_models.users;

import utils.validate.PasswordValidate;

import javax.servlet.ServletException;

public class UserLoginRequest {
    private String username;
    private String password;
    private boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws ServletException {
        if (PasswordValidate.inCorrectFormat(password))
            throw new ServletException();
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
