package models.services.mail_verify_token;

import javax.servlet.http.HttpServletRequest;

public interface IVerifyTokenService {
    boolean sendVerifyTokenMail(int userId, HttpServletRequest request);
    boolean resendVerifyTokenMail(String token, HttpServletRequest request);
    String verifyToken(String token);
}
