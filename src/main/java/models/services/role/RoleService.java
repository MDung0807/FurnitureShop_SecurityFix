package models.services.role;

import models.repositories.role.RoleRepository;
import models.view_models.roles.RoleCreateRequest;
import models.view_models.roles.RoleGetPagingRequest;
import models.view_models.roles.RoleUpdateRequest;
import models.view_models.roles.RoleViewModel;

import java.util.ArrayList;

public class RoleService implements IRoleService{
    private static RoleService instance = null;
    public static RoleService getInstance(){
        if(instance == null)
            instance = new RoleService();
        return instance;
    }
    @Override
    public int insertRole(RoleCreateRequest request) {
        return RoleRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateRole(RoleUpdateRequest request) {
        return RoleRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteRole(Integer roleId) {
        return RoleRepository.getInstance().delete(roleId);
    }
    @Override
    public RoleViewModel retrieveRoleById(Integer roleId) {
        return RoleRepository.getInstance().retrieveById(roleId);
    }

    @Override
    public ArrayList<RoleViewModel> retrieveAllRole(RoleGetPagingRequest request) {
        return RoleRepository.getInstance().retrieveAll(request);
    }
}
