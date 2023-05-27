package models.services.mail_verify_token;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IVerifyTokenService {
    boolean sendVerifyTokenMail(int userId, HttpServletRequest request) throws ServletException;
    boolean resendVerifyTokenMail(String token, HttpServletRequest request) throws ServletException;
    String verifyToken(String token);
}
