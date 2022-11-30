package models.repositories.role;

import models.entities.Role;
import models.view_models.roles.RoleCreateRequest;
import models.view_models.roles.RoleGetPagingRequest;
import models.view_models.roles.RoleUpdateRequest;
import models.view_models.roles.RoleViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;
import utils.constants.BRAND_STATUS;
import utils.constants.ROLE_STATUS;

import java.util.ArrayList;
import java.util.List;

public class RoleRepository implements IRoleRepository{
    private static RoleRepository instance = null;
    public static RoleRepository getInstance(){
        if(instance == null)
            instance = new RoleRepository();
        return instance;
    }
    @Override
    public int insert(RoleCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Role role = new Role();
        role.setRoleName(request.getRoleName());
        role.setStatus(request.getStatus());
        int roleId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(role);
            roleId = role.getRoleId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return roleId;
    }

    @Override
    public boolean update(RoleUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Role role = session.find(Role.class, request.getRoleId());
        role.setRoleName(request.getRoleName());
        role.setStatus(request.getStatus());
        return HibernateUtils.merge(role);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Query q3 = session.createQuery("select roleId from UserRole where roleId=:s1");
        q3.setParameter("s1",entityId);
        List<Integer> roleIds = q3.list();
        if(roleIds.size() > 0)
            return false;
        Role role = session.find(Role.class, entityId);
        role.setStatus(ROLE_STATUS.IN_ACTIVE);
        session.close();
        return HibernateUtils.merge(role);
    }
    private String getStatus(int i){
        String status = "";
        switch (i){
            case ROLE_STATUS.ACTIVE:
                status = "Đang dùng";
                break;
            case ROLE_STATUS.IN_ACTIVE:
                status = "Đã xoá";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private RoleViewModel getRoleViewModel(Role role, Session session){
        RoleViewModel roleViewModel = new RoleViewModel();

        roleViewModel.setRoleId(role.getRoleId());
        roleViewModel.setRoleName(role.getRoleName());
        roleViewModel.setStatus(role.getStatus());
        roleViewModel.setStatusCode(getStatus(role.getStatus()));
        return roleViewModel;
    }
    @Override
    public RoleViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Role role = session.find(Role.class, entityId);

        RoleViewModel roleViewModel = getRoleViewModel(role, session);
        session.close();

        return roleViewModel;
    }

    @Override
    public ArrayList<RoleViewModel> retrieveAll(RoleGetPagingRequest request) {
        ArrayList<RoleViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Role", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Role> roles = q.list();

        for(Role role:roles){
            RoleViewModel v = getRoleViewModel(role, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
