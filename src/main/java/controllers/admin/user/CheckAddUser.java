package controllers.admin.user;


import models.repositories.user.UserRepository;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "CheckAddUser", value = "/users/check-add")
public class CheckAddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<String> exists = new ArrayList<>();
        if(UserRepository.getInstance().checkUsername(request.getParameter("username"))){
            exists.add("user".trim());
        }
        if(UserRepository.getInstance().checkEmail(request.getParameter("email"))){
            exists.add("email".trim());
        }
        if(UserRepository.getInstance().checkPhone(request.getParameter("phone"))){
            exists.add("phone".trim());
        }
        PrintWriter out = response.getWriter();
        out.println(exists);
    }
}
