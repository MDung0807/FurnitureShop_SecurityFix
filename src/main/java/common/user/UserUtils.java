package common.user;

import models.view_models.users.UserCreateRequest;
import models.view_models.users.UserLoginRequest;
import models.view_models.users.UserUpdateRequest;
import utils.DateUtils;
import utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class UserUtils {
    public static UserCreateRequest CreateRegisterRequest(HttpServletRequest request) throws ServletException, IOException {
        UserCreateRequest reqCreate = new UserCreateRequest();
        reqCreate.setAvatar(request.getPart("avatar"));
        reqCreate.setUsername(request.getParameter("username"));
        reqCreate.setFirstName(request.getParameter("firstName"));
        reqCreate.setLastName(request.getParameter("lastName"));
        reqCreate.setEmail(request.getParameter("email"));
        reqCreate.setPhone(request.getParameter("phone"));
        reqCreate.setDateOfBirth(DateUtils.stringToLocalDate(request.getParameter("dob"), "MM/dd/yyyy"));
        reqCreate.setAddress(request.getParameter("address"));
        reqCreate.setGender(StringUtils.toInt(request.getParameter("gender")));
        reqCreate.setPassword(request.getParameter("password"));
        reqCreate.setStatus(StringUtils.toInt(request.getParameter("status")));
        String[] values = request.getParameterValues("roleCheckBox");

        if(values == null)
            return reqCreate;
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (String v : values) {
            roleIds.add(StringUtils.toInt(v));
        }
        reqCreate.setRoleIds(roleIds);

        return reqCreate;
    }

    public static UserLoginRequest CreateLoginRequest(HttpServletRequest request) throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        return loginRequest;
    }
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        return DatatypeConverter.printHexBinary(hash);
    }
    public static UserUpdateRequest CreateUserUpdateRequest(HttpServletRequest request) throws ServletException, IOException{
        UserUpdateRequest reqUpdate = new UserUpdateRequest();

        reqUpdate.setAvatar(request.getPart("avatar"));

        reqUpdate.setUserId(StringUtils.toInt(request.getParameter("userId")));
        reqUpdate.setFirstName(request.getParameter("firstName"));
        reqUpdate.setLastName(request.getParameter("lastName"));
        reqUpdate.setEmail(request.getParameter("email"));
        reqUpdate.setPhone(request.getParameter("phone"));
        reqUpdate.setDateOfBirth(DateUtils.stringToLocalDate(request.getParameter("dob"), "MM/dd/yyyy"));
        reqUpdate.setAddress(request.getParameter("address"));
        reqUpdate.setGender(StringUtils.toInt(request.getParameter("gender")));
        reqUpdate.setPassword(request.getParameter("newPassword"));
        reqUpdate.setStatus(StringUtils.toInt(request.getParameter("status")));
        reqUpdate.setUsername(request.getParameter("username"));
        String[] values = request.getParameterValues("roleCheckBox");
        if(values == null)
            return reqUpdate;
        ArrayList<Integer> roleIds = new ArrayList<>();
        for(String v:values){
            roleIds.add(StringUtils.toInt(v));
        }
        reqUpdate.setRoleIds(roleIds);

        return reqUpdate;
    }
}
