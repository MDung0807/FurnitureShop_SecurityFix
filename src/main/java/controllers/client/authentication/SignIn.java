package controllers.client.authentication;

import common.user.UserUtils;
import models.entities.User;
import models.services.google.GoogleService;
import models.services.user.UserService;
import models.view_models.users.UserLoginRequest;
import models.view_models.users.UserUpdateRequest;
import models.view_models.users.UserViewModel;
import utils.HibernateUtils;
import utils.ServletUtils;
import utils.constants.USER_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignIn", value = "/signin")
public class SignIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("myCookie", "cookieValue");

        response.setHeader("X-Content-Type-Options", "nosniff");
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel)session.getAttribute("user");
        if(user != null){
            if (user.getStatus() == USER_STATUS.PASSWORD_HAS_NOT_CHANGED)
                ServletUtils.redirect(response, request.getContextPath() + "/my-account?info=true");
            else
                ServletUtils.redirect(response, request.getContextPath() + "/home");
        }
        else {
            String url = request.getRequestURL().toString();
            String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
            String scope = "profile%20email";
            String googleLoginLink = "https://accounts.google.com/o/oauth2/auth?scope=" + scope + "&redirect_uri=" + baseURL +"/login-google&response_type=code&client_id=" + GoogleService.GOOGLE_CLIENT_ID +"&approval_prompt=force";
            request.setAttribute("urlGoogleLogin",googleLoginLink);
            ServletUtils.forward(request, response, "/views/client/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try{
            UserLoginRequest loginRequest = UserUtils.CreateLoginRequest(request);

            if(UserService.getInstance().login(loginRequest)){
                UserViewModel user = UserService.getInstance().getUserByUserName(loginRequest.getUsername());
                if(user.getStatus() == USER_STATUS.IN_ACTIVE){
                    out.println("banned".trim());

                }else if (user.getStatus() == USER_STATUS.UN_CONFIRM){
                    out.println("unconfirm".trim());
                }
                else if (user.getStatus() == USER_STATUS.PASSWORD_HAS_NOT_CHANGED){
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("Secure", true);
                    ServletUtils.redirect(response, request.getContextPath()+ "/my-account?info=true");
                } else if (user.getStatus() == USER_STATUS.LOCK) {
                    out.println("locked".trim());
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("loginAttempts", 0);
                    session.setAttribute("user", user);
                    session.setAttribute("Secure", true);
                    Cookie c = new Cookie("user", loginRequest.getUsername());
                    c.setSecure(true);
                    c.setHttpOnly(true);
//                    String cookieHeader = c.getName() + c.getValue() + "; Secure; HttpOnly; SameSite=" + "strict";
//                    response.setHeader("Set-Cookie", cookieHeader);
                    response.addCookie(c);
                    if (user.getStatus() == USER_STATUS.PASSWORD_HAS_NOT_CHANGED){

                        ServletUtils.redirect(response, request.getContextPath()+ "/my-account?info=true");
                    }
                    else {
                        ServletUtils.redirect(response, request.getContextPath() + "/home");
                    }
                }
            }else{
                // Xác thực thất bại
                HttpSession session = request.getSession();
                Integer loginAttempts = (Integer) session.getAttribute("loginAttempts");

                if (loginAttempts == null) {
                    // Lần đầu tiên nhập sai mật khẩu
                    session.setAttribute("loginAttempts", 1);
                } else {
                    // Cập nhật số lần nhập sai mật khẩu
                    loginAttempts++;
                    session.setAttribute("loginAttempts", loginAttempts);
                    System.out.println("loginAttempts"+loginAttempts);
                    if (loginAttempts >= 5) {
                        // Khóa tài khoản sau 5 lần nhập sai
                        User user = UserService.getInstance().getUserByUserNameUser(loginRequest.getUsername());
                        user.setStatus(USER_STATUS.LOCK);
                        HibernateUtils.merge(user);
                        out.println("locked".trim());
                        return;
                    }
                }

                out.println("error".trim());
            }
        }
        catch (Exception e){
            out.println("error".trim());
        }
    }
}
