package utils;

import models.view_models.users.UserViewModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletUtils {
    public static void forward(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
//        response.setHeader("Content-Security-Policy",
//                    "default-src 'self' https: 'unsafe-inline';" +
//                        "script-src 'self' 'unsafe-inline';" +
//                        "font-src 'self' https: data: ; " +
//                        "img-src 'self' https: data: ;");
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
    public static void redirect(HttpServletResponse response, String url)
            throws ServletException, IOException {
//        response.setHeader("Content-Security-Policy",
//                "default-src 'self' https: 'unsafe-inline';" +
//                        "script-src 'self' 'unsafe-inline';" +
//                        "font-src 'self' https: data: ; " +
//                        "img-src 'self' https: data: ;");
         response.sendRedirect(url);
    }

    public static boolean isAuthenticateAdmin(HttpServletRequest request){
        HttpSession session = request.getSession();

        UserViewModel user = (UserViewModel)session.getAttribute("admin");
        if(user == null)
            return false;
        return true;
    }
}
