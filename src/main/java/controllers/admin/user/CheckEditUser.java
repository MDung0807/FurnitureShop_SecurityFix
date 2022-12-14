package controllers.admin.user;

import common.user.UserUtils;
import models.services.user.UserService;
import utils.StringUtils;
import models.view_models.users.UserViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "CheckEditUser", value = "/users/check-edit")
public class CheckEditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int userId = StringUtils.toInt(request.getParameter("userId"));
        UserViewModel user = UserService.getInstance().retrieveUserById(userId);
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String hasChangePass = request.getParameter("hasChangePass");
        ArrayList<String> exists = new ArrayList<>();
        if(!Objects.equals(user.getUsername(), username) && UserService.getInstance().checkUsername(username)){
            exists.add("user".trim());
        }
        if(!Objects.equals(user.getEmail(), email) && UserService.getInstance().checkEmail(email)){
            exists.add("email".trim());
        }
        if(!Objects.equals(user.getPhone(), phone) && UserService.getInstance().checkPhone(phone)){
            exists.add("phone".trim());
        }
        try {
            if(hasChangePass.equals("true") && !Objects.equals(user.getPassword(), UserUtils.hashPassword(request.getParameter("password")))){
                exists.add("password".trim());
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        PrintWriter out = response.getWriter();
        out.println(exists);
    }
}
