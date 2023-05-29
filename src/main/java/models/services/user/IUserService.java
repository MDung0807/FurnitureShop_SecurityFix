package models.services.user;

import models.entities.User;
import models.view_models.user_roles.UserRoleViewModel;
import models.view_models.users.*;

import javax.servlet.ServletException;
import java.util.ArrayList;

public interface IUserService {
    int insertUser(UserCreateRequest request);
    boolean updateUser(UserUpdateRequest request);
    boolean deleteUser(Integer userId);
    UserViewModel retrieveUserById(Integer userId) throws ServletException;
    ArrayList<UserViewModel> retrieveAllUser(UserGetPagingRequest request) throws ServletException;
    boolean checkUsername(String username);
    boolean checkEmail(String email);
    boolean checkPhone(String phone);
    boolean checkPassword(int userId, String password);
    boolean login(UserLoginRequest request);
    UserViewModel getUserByUserName(String username) throws ServletException;
    User getUserByUserNameUser(String username) throws ServletException;
    UserViewModel getUserByEmail(String email);
    ArrayList<UserViewModel> getTopUserByTotalOrder(int top) throws ServletException;
    long getTotalUser();
    ArrayList<UserRoleViewModel> getUserRoleByUserId(int userId);
    boolean forgotPassword(String email);
}
