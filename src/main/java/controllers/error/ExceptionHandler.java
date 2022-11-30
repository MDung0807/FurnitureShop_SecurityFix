package controllers.error;

import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ExceptionHandler", value = "/ExceptionHandler")
public class ExceptionHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/views/client/common/error/error.jsp";
        if(request.getAttribute("javax.servlet.forward.request_uri").toString().contains("admin")){
            url = "/views/admin/common/error/error.jsp";
        }

        ServletUtils.forward(request,response,url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
