package controllers.client.authentication;

import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignOut", value = "/signout")
public class SignOut extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        Cookie c = new Cookie("user","");
        c.setSecure(true);
        c.setMaxAge(0);
        c.setHttpOnly(true);
        response.addCookie(c);
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null){
            session.removeAttribute("user");
        }
        ServletUtils.redirect(response, request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
