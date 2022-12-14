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
        ServletUtils.forward(request,response,"/views/admin/signin/signin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        UserLoginRequest loginRequest = UserUtils.CreateLoginRequest(request);
        boolean isAdmin = false;
        boolean isBanned = false;
        boolean isLogin = false;
        boolean isUnConfirm = false;
        if(UserService.getInstance().login(loginRequest)){
            isLogin = true;
            UserViewModel user = UserService.getInstance().getUserByUserName(loginRequest.getUsername());
            for(UserRoleViewModel role:user.getRoles()){
                if(role.getRoleName().equalsIgnoreCase("admin")){
                    Cookie c = new Cookie("admin", loginRequest.getUsername());
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
}
