package models.services.role;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.roles.RoleCreateRequest;
import models.view_models.roles.RoleGetPagingRequest;
import models.view_models.roles.RoleUpdateRequest;
import models.view_models.roles.RoleViewModel;

import java.util.ArrayList;

public interface IRoleService {
    int insertRole(RoleCreateRequest request);
    boolean updateRole(RoleUpdateRequest request);
    boolean deleteRole(Integer roleId);
    RoleViewModel retrieveRoleById(Integer roleId);
    ArrayList<RoleViewModel> retrieveAllRole(RoleGetPagingRequest request);

}
