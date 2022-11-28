package models.repositories.role;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.roles.RoleCreateRequest;
import models.view_models.roles.RoleGetPagingRequest;
import models.view_models.roles.RoleUpdateRequest;
import models.view_models.roles.RoleViewModel;

public interface IRoleRepository  extends IModifyEntity<RoleCreateRequest, RoleUpdateRequest, Integer>,
        IRetrieveEntity<RoleViewModel, RoleGetPagingRequest, Integer> {
}
