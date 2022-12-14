package models.services.mail_verify_token;

import models.entities.MailConfirmToken;
import models.repositories.mail_verified_token.VerifyTokenRepository;
import models.repositories.user.UserRepository;
import models.services.mail.MailJetService;
import models.services.user.UserService;
import models.view_models.users.UserViewModel;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.servlet.http.HttpServletRequest;

public class VerifyTokenService implements IVerifyTokenService{

    private static VerifyTokenService instance = null;
    public static VerifyTokenService getInstance(){
        if(instance == null)
            instance = new VerifyTokenService();
        return instance;
    }
    @Override
    public boolean sendVerifyTokenMail(int userId, HttpServletRequest request) {
        String title = "Xác nhận đăng ký tài khoản";
        UserViewModel user = UserService.getInstance().retrieveUserById(userId);
        int verifyTokenId = VerifyTokenRepository.getInstance().insertToken(userId);
        if(verifyTokenId == -1)
            return false;
        String verifyToken = VerifyTokenRepository.getInstance().getVerifyTokenById(verifyTokenId);
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
        String content = "<h2>Chào " + user.getFirstName() + " " + user.getLastName() + ", </h2><h3>Link xác nhận đăng ký cho tài khoản <em>" + user.getUsername() + "</em>: <a href = \"" + baseURL + "/register/confirm?verifyToken=" + verifyToken + "\">Confirm account</a> </h3>" + "<h4>Bạn vui lòng xác nhận ngay khi thấy tin nhắn này (Thời hạn token là 2 phút). Xin cảm ơn!!!</h4>";
        return MailJetService.getInstance().sendMail(user.getFirstName() + " " + user.getLastName(),  user.getEmail(), content, title);
    }

    @Override
    public boolean resendVerifyTokenMail(String token, HttpServletRequest request) {
        int userId = VerifyTokenRepository.getInstance().deleteToken(token);
        if(userId == -1)
            return false;
        return sendVerifyTokenMail(userId, request);
    }

    @Override
    public String verifyToken(String token) {
        return VerifyTokenRepository.getInstance().verifyToken(token);
    }
}
