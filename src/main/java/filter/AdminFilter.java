package filter;

import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.constants.USER_STATUS;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        httpReq.setCharacterEncoding("UTF-8");
        httpResp.setCharacterEncoding("UTF-8");
        HttpSession session = httpReq.getSession(false);
        UserViewModel user = null;
        String url = httpReq.getRequestURI();
        if(session != null)
            user = (UserViewModel) session.getAttribute("admin");
        if(user != null){
            if (user.getStatus() != USER_STATUS.PASSWORD_HAS_NOT_CHANGED)
                chain.doFilter(request, response);
            else {
                httpReq.setAttribute("error","error");
                ServletUtils.forward(httpReq, httpResp, "/admin/login");
            }
        }else{
            httpReq.setAttribute("error","error");
            ServletUtils.forward(httpReq, httpResp, "/admin/login");
        }
    }
}
