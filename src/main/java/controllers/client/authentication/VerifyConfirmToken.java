package controllers.client.authentication;

import models.services.mail_verify_token.VerifyTokenService;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "VerifyConfirmToken", value = "/register/confirm")
public class VerifyConfirmToken extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String verifyToken = request.getParameter("verifyToken");
        String res = VerifyTokenService.getInstance().verifyToken(verifyToken);
        String status = "";
        if(Objects.equals(res, "expired")){
            status = "?token-expired=true";
            VerifyTokenService.getInstance().resendVerifyTokenMail(verifyToken, request);
        }else if(Objects.equals(res, "error")){
            status = "?token-error=true";
        }else if(Objects.equals(res, "success")) {
            status = "?token-verify-success=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/signin" + status);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
