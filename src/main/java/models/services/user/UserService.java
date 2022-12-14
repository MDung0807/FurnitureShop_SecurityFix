package models.services.user;

import models.repositories.user.UserRepository;
import models.view_models.user_roles.UserRoleViewModel;
import models.view_models.users.*;

import java.util.ArrayList;

public class UserService implements IUserService{
    private static UserService instance = null;
    public static UserService getInstance(){
        if(instance == null)
            instance = new UserService();
        return instance;
    }
    @Override
    public int insertUser(UserCreateRequest request) {
        return UserRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateUser(UserUpdateRequest request) {
        return UserRepository.getInstance().update(request);
    }
    @Override
    public boolean deleteUser(Integer userId) {
        return UserRepository.getInstance().delete(userId);
    }
    @Override
    public UserViewModel retrieveUserById(Integer userId) {
        return UserRepository.getInstance().retrieveById(userId);
    }

    @Override
    public ArrayList<UserViewModel> retrieveAllUser(UserGetPagingRequest request) {
        return UserRepository.getInstance().retrieveAll(request);
    }

    @Override
    public boolean checkUsername(String username) {
        return UserRepository.getInstance().checkUsername(username);
    }

    @Override
    public boolean checkEmail(String email) {
        return UserRepository.getInstance().checkEmail(email);
    }

    @Override
    public boolean checkPhone(String phone) {
        return UserRepository.getInstance().checkPhone(phone);
    }

    @Override
    public boolean checkPassword(int userId, String password) {
        return UserRepository.getInstance().checkPassword(userId, password);
    }

    @Override
    public boolean login(UserLoginRequest request) {
        return UserRepository.getInstance().login(request);
    }
    @Override
    public UserViewModel getUserByUserName(String username) {
        return UserRepository.getInstance().getUserByUserName(username);
    }

    @Override
    public UserViewModel getUserByEmail(String email) {
        return UserRepository.getInstance().getUserByEmail(email);
    }

    @Override
    public ArrayList<UserViewModel> getTopUserByTotalOrder(int top) {
        return UserRepository.getInstance().getTopUserByTotalOrder(top);
    }

    @Override
    public long getTotalUser() {
        return UserRepository.getInstance().getTotalUser();
    }

    @Override
    public ArrayList<UserRoleViewModel> getUserRoleByUserId(int userId) {
        return UserRepository.getInstance().getUserRoleByUserId(userId);
    }

    @Override
    public boolean forgotPassword(String email) {
        return UserRepository.getInstance().forgotPassword(email);
    }

}
