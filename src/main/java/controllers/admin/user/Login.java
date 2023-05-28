package controllers.admin.user;

import common.user.UserUtils;
import models.services.user.UserService;
import models.view_models.user_roles.UserRoleViewModel;
import models.view_models.users.UserLoginRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.constants.USER_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login", value = "/admin/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        ServletUtils.forward(request,response,"/views/admin/signin/signin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        PrintWriter out = response.getWriter();

        boolean isAdmin = false;
        boolean isBanned = false;
        boolean isLogin = false;
        boolean isUnConfirm = false;
        try{
            UserLoginRequest loginRequest = UserUtils.CreateLoginRequest(request);
            if(UserService.getInstance().login(loginRequest)){
                isLogin = true;
                UserViewModel user = UserService.getInstance().getUserByUserName(loginRequest.getUsername());
                for(UserRoleViewModel role:user.getRoles()){
                    if(role.getRoleName().equalsIgnoreCase("admin")){
                        Cookie c = new Cookie("admin", loginRequest.getUsername());
                        c.setSecure(true);
                        c.setHttpOnly(true);
                        String cookieHeader = c.getName() + c.getValue() + "; Secure; HttpOnly; SameSite=" + "Strict";
                        response.setHeader("Set-Cookie", cookieHeader);
                        response.addCookie(c);
                        isAdmin = true;
                        if(user.getStatus() == USER_STATUS.IN_ACTIVE) {
                            isBanned = true;
                            break;
                        }else if(user.getStatus() == USER_STATUS.UN_CONFIRM){
                            isUnConfirm = true;
                            break;
                        }
                        HttpSession session = request.getSession();
                        session.setAttribute("admin",user);
                        session.setAttribute("Secure", true);
                        break;
                    }
                }
            }
            if(!isLogin){
                out.println("error");
            }
            else if(!isAdmin){
                out.println("unauthorize");
            }
            else if(isBanned){
                out.println("banned");
            }
            else if(isUnConfirm){
                out.println("unconfirm");
            }
            else{
                ServletUtils.redirect(response, request.getContextPath() + "/admin/home");
            }
        }
        catch (Exception e){
            out.println("error");
        }
    }
}
