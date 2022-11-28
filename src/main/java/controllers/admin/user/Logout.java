package controllers.admin.user;

import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/admin/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie c = new Cookie("admin","");
        c.setMaxAge(0);
        response.addCookie(c);
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") != null){
            session.removeAttribute("admin");
        }
        ServletUtils.forward(request, response, "/admin/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
