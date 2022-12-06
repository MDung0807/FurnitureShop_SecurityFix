package controllers.client.authentication;

import common.user.UserUtils;
import models.repositories.user.UserRepository;
import models.view_models.users.UserLoginRequest;
import models.view_models.users.UserViewModel;
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
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel)session.getAttribute("user");
        if(user != null){
            ServletUtils.redirect(response, request.getContextPath() + "/home");
        }
        else
            ServletUtils.forward(request,response, "/views/client/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        UserLoginRequest loginRequest = UserUtils.CreateLoginRequest(request);

        if(UserRepository.getInstance().login(loginRequest)){
            UserViewModel user = UserRepository.getInstance().getUserByUserName(loginRequest.getUsername());
            if(user.getStatus() == USER_STATUS.IN_ACTIVE){
                out.println("banned".trim());
            }else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                ServletUtils.redirect(response, request.getContextPath() + "/home");
            }
        }else{
            out.println("error".trim());
        }

    }
}
