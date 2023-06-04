package controllers.client.user;

import common.user.UserUtils;
import models.services.user.UserService;
import models.view_models.users.UserUpdateRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.constants.USER_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "EditInfo", value = "/my-account/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class EditInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        request.setCharacterEncoding("UTF-8");
        UserUpdateRequest reqUpdate = UserUtils.CreateUserUpdateRequest(request);
        boolean isSuccess = false;
        reqUpdate.setStatus(USER_STATUS.ACTIVE);
        UserViewModel userViewModel = (UserViewModel) request.getSession().getAttribute("user");
        //Kiểm tra, nếu như id của user trong sesion giống với user yêu cầu thay đổi thông tin
        if (userViewModel.getId() != reqUpdate.getUserId()){
            ServletUtils.redirect(response, request.getContextPath() + "/my-account?error=true");
            return;
        }
        else if (userViewModel.getPassword().equals(reqUpdate.getPassword()))
            isSuccess= UserService.getInstance().updateUser(reqUpdate); // Thực hiện cập nhật thông tin
        if(!isSuccess){
            request.setAttribute("error", "error");
        }else{
            UserViewModel user = UserService.getInstance().getUserByUserName(request.getParameter("username"));
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            request.setAttribute("user", user);
        }

        ServletUtils.redirect(response, request.getContextPath() + "/my-account?info=true");
    }
}
