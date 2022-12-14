package controllers.client.authentication;

import models.services.google.GoogleModel;
import models.services.google.GoogleService;
import models.services.user.UserService;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.constants.USER_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GoogleLogin", value = "/login-google")
public class GoogleLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            ServletUtils.redirect(response, request.getContextPath() + "/signin?error=true");
        } else {
            String url = request.getRequestURL().toString();
            String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
            String accessToken = GoogleService.getInstance().getToken(code, baseURL);
            GoogleModel googleUserInfo = GoogleService.getInstance().getGoogleAccountInfo(accessToken);
            UserViewModel user = UserService.getInstance().getUserByEmail(googleUserInfo.getEmail());
            if(user == null) {
                request.setAttribute("googleUser", googleUserInfo);
                ServletUtils.forward(request, response, "/register");
            }else{
                PrintWriter out = response.getWriter();
                if(user.getStatus() == USER_STATUS.IN_ACTIVE){
                    ServletUtils.redirect(response, request.getContextPath() + "/signin?banned=true");
                }else if(user.getStatus() == USER_STATUS.UN_CONFIRM){
                    ServletUtils.redirect(response, request.getContextPath() + "/signin?unconfirm=true");
                }
                else {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    ServletUtils.redirect(response, request.getContextPath() + "/home");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
