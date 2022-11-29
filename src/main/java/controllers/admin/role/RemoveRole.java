package controllers.admin.role;

import models.services.role.RoleService;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveRole", value = "/admin/role/delete")
public class RemoveRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roleId = StringUtils.toInt(request.getParameter("roleId"));

        boolean isSuccess = RoleService.getInstance().deleteRole(roleId);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/roles" + error);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
