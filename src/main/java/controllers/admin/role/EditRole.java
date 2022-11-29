package controllers.admin.role;

import models.services.role.RoleService;
import models.services.role.RoleService;
import models.view_models.roles.RoleUpdateRequest;
import models.view_models.roles.RoleUpdateRequest;
import models.view_models.roles.RoleViewModel;
import utils.DateUtils;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditRole", value = "/admin/role/edit")
public class EditRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roleId = StringUtils.toInt(request.getParameter("roleId"));

        RoleViewModel role = RoleService.getInstance().retrieveRoleById(roleId);

        request.setAttribute("role", role);

        ServletUtils.forward(request,response,"/admin/roles");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        RoleUpdateRequest updateReq = new RoleUpdateRequest();

        int roleId = StringUtils.toInt(request.getParameter("roleId"));
        updateReq.setRoleId(roleId);

        updateReq.setRoleName(request.getParameter("roleName"));

        boolean isSuccess = RoleService.getInstance().updateRole(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/roles" + error);
    }
}
