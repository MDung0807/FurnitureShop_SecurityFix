package controllers.client.authentication;

import models.services.user.UserService;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ForgotPassword", value = "/forgot-password")
public class ForgotPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.forward(request, response, "views/client/forgot-password.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        PrintWriter out = response.getWriter();

        boolean res = UserService.getInstance().forgotPassword(email);
        String status = "";
        if(!res) {
            out.println("error");
        }else{
            ServletUtils.redirect(response, request.getContextPath() + "/signin?forgot-password=true");
        }
    }
}
