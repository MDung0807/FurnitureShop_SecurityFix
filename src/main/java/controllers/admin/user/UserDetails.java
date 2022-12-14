package controllers.admin.user;

import models.services.role.RoleService;
import models.services.user.UserService;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.roles.RoleGetPagingRequest;
import models.view_models.roles.RoleViewModel;
import models.view_models.users.UserViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UserDetails", value = "/admin/user/detail")
public class UserDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = StringUtils.toInt(request.getParameter("userId"));

        UserViewModel user = UserService.getInstance().retrieveUserById(userId);

        request.setAttribute("user", user);
        RoleGetPagingRequest reqRole = new RoleGetPagingRequest();
        ArrayList<RoleViewModel> roles = RoleService.getInstance().retrieveAllRole(reqRole);
        request.setAttribute("roles",roles);
        ServletUtils.forward(request,response,"/views/admin/user/user-detail.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
