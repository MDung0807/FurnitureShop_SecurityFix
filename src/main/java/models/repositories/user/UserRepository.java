package models.repositories.user;

import common.user.UserUtils;
import models.entities.*;
import models.services.mail.MailJetService;
import models.services.user.UserService;
import models.view_models.user_roles.UserRoleViewModel;
import models.view_models.users.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.FileUtil;
import utils.HibernateUtils;
import utils.constants.USER_GENDER;
import utils.constants.USER_STATUS;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserRepository implements IUserRepository{
    private static UserRepository instance = null;
    public static UserRepository getInstance(){
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }
    @Override
    public int insert(UserCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        User user = new User();
        user.setAddress(request.getAddress());
        user.setDateCreated(DateUtils.dateTimeNow());
        user.setDateUpdated(DateUtils.dateTimeNow());
        user.setStatus(request.getStatus());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        try {
            user.setPassword(UserUtils.hashPassword(request.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            return -1;
        }
        user.setUsername(request.getUsername());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        if(request.getAvatar()!= null && !request.getAvatar().getSubmittedFileName().equals("")){
            user.setAvatar(FileUtil.encodeBase64(request.getAvatar()));
        }
        int userId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(user);
            userId = user.getUserId();

            if(userId != -1){
                for(int roleId: request.getRoleIds()){
                    UserRole ur = new UserRole();
                    ur.setUserId(userId);
                    ur.setRoleId(roleId);
                    session.persist(ur);
                }
                Cart cart = new Cart();
                cart.setUser(user);

                WishList wishList = new WishList();
                wishList.setUser(user);

                Review review = new Review();
                review.setUser(user);

                session.persist(cart);
                session.persist(wishList);
                session.persist(review);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return userId;
    }

    @Override
    public boolean update(UserUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        User user = session.find(User.class, request.getUserId());
        user.setAddress(request.getAddress());
        user.setDateUpdated(DateUtils.dateTimeNow());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setStatus(request.getStatus());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        if(request.getPassword() != null && !Objects.equals(request.getPassword(), "")) {
            try {
                user.setPassword(UserUtils.hashPassword(request.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                return false;
            }
        }
        if(request.getAvatar()!= null && !request.getAvatar().getSubmittedFileName().equals("")){
            user.setAvatar(FileUtil.encodeBase64(request.getAvatar()));
        }
        if(request.getRoleIds() != null && request.getRoleIds().size() > 0){
            boolean res = updateUserRole(session, user, request.getRoleIds());
            if(!res)
                return false;
        }else{
            session.close();
            return HibernateUtils.merge(user);
        }
        session.close();
        return true;
    }
    private String getUserStatus(int i){
        String status = "";
        switch (i){
            case USER_STATUS.IN_ACTIVE:
                status = "Không hoạt động";
                break;
            case USER_STATUS.ACTIVE:
                status = "Đang hoạt động";
                break;
            default:
                status = "Không xác định";
                break;
        }
        return status;
    }
    private String getUserGender(int i){
        String gender = "";
        switch (i){
            case USER_GENDER.MALE:
                gender = "Nam";
                break;
            case USER_GENDER.FEMALE:
                gender = "Nữ";
                break;
            case USER_GENDER.OTHER:
                gender = "Khác";
                break;
            default:
                gender = "Không xác định";
                break;
        }
        return gender;
    }
    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        User user = session.find(User.class, entityId);
        user.setStatus(USER_STATUS.IN_ACTIVE);
        return HibernateUtils.merge(user);
    }
    private UserViewModel getUserViewModel(User user, Session session){
        UserViewModel userViewModel = new UserViewModel();

        userViewModel.setId(user.getUserId());
        userViewModel.setAddress(user.getAddress());
        userViewModel.setDateCreated(DateUtils.dateTimeToStringWithFormat(user.getDateCreated(),"yyyy-MM-dd HH:mm:ss"));
        userViewModel.setStatus(user.getStatus());
        userViewModel.setDateUpdated(DateUtils.dateTimeToStringWithFormat(user.getDateUpdated(),"yyyy-MM-dd HH:mm:ss"));
        userViewModel.setFirstName(user.getFirstName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setGender(user.getGender());
        if(user.getLastLogin() != null)
            userViewModel.setLastLogin(DateUtils.dateTimeToStringWithFormat(user.getLastLogin(),"yyyy-MM-dd HH:mm:ss"));
        userViewModel.setEmail(user.getEmail());
        userViewModel.setPhone(user.getPhone());
        userViewModel.setUsername(user.getUsername());
        userViewModel.setDateOfBirth(user.getDateOfBirth());
        userViewModel.setAvatar(user.getAvatar());
        userViewModel.setPassword(user.getPassword());

        Query q1 = session.createQuery("select sum(oi.quantity) from Order o " +
                "inner join OrderItem oi on o.orderId = oi.order.orderId where o.user.userId=:s1");
        q1.setParameter("s1",user.getUserId());
        Object res1 = q1.getSingleResult();
        userViewModel.setTotalBought(res1 != null ? (long)res1 : 0);

        userViewModel.setStatusCode(getUserStatus(user.getStatus()));
        userViewModel.setGenderCode(getUserGender(user.getGender()));

        Query q2 = session.createQuery("select count(*) from WishItem wi inner join WishList wl on wi.wishList.wishListId = wl.wishListId where wl.user.userId =:s1");
        q2.setParameter("s1",user.getUserId());
        Object res2 = q2.getSingleResult();
        userViewModel.setTotalWishListItem(res2 != null ? (long)res2 : 0);

        Query q3 = session.createQuery("select count(*) from CartItem ci inner join Cart c on ci.cart.cartId = c.cartId where c.user.userId =:s1");
        q3.setParameter("s1",user.getUserId());
        Object res3 = q3.getSingleResult();
        userViewModel.setTotalCartItem(res3 != null ? (long)res3 : 0);

        Query q4 = session.createQuery("select count(*) from User u inner join Order o on u.userId = o.user.userId where o.user.userId=:s1");
        q4.setParameter("s1",user.getUserId());
        Object res4 = q4.getSingleResult();
        userViewModel.setTotalOrders(res4 != null ? (long)res4 : 0);

        Query q5 = session.createQuery("select sum(o.totalPrice) from Order o where o.user.userId=:s1");
        q5.setParameter("s1",user.getUserId());
        Object res5 = q5.getSingleResult();
        userViewModel.setTotalCost(res5 != null ? (BigDecimal)res5 : BigDecimal.valueOf(0));

        userViewModel.setRoles(UserService.getInstance().getUserRoleByUserId(user.getUserId()));

        ArrayList<Integer> roleIds = new ArrayList<>();
        userViewModel.getRoles().forEach(s -> roleIds.add(s.getRoleId()));
        userViewModel.setRoleIds(roleIds);


        return userViewModel;
    }
    @Override
    public UserViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        User user = session.find(User.class, entityId);
        if(user == null){
            session.close();
            return null;
        }
        UserViewModel userViewModel = getUserViewModel(user, session);
        session.close();

        return userViewModel;
    }

    @Override
    public ArrayList<UserViewModel> retrieveAll(UserGetPagingRequest request) {
        ArrayList<UserViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("User", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<User> users = q.list();

        for(User user:users){
            UserViewModel v = getUserViewModel(user, session);
            list.add(v);
        }
        session.close();
        return list;
    }

     private int getUserRoleId(int userId, int roleId) {
        Session session = HibernateUtils.getSession();
        Query q1 = session.createQuery("select userRoleId from UserRole where userId=:s1 and roleId=:s2");
        q1.setParameter("s1",userId);
        q1.setParameter("s2",roleId);
        try{
            return (int)q1.getSingleResult();
        }catch(Exception e){
            return -1;
        }
    }

    private boolean updateUserRole(Session session, User user, ArrayList<Integer> roleIDs) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            HashMap<Integer, Integer> userRoleIds = new HashMap<>();
            for(UserRole ur: user.getUserRoles()){
                userRoleIds.put(ur.getUserRoleId(), ur.getRoleId());
            }
            ArrayList<Integer> addRoleIds = new ArrayList<>();
            for(int roleId:roleIDs){
                if(!userRoleIds.values().contains(roleId)){
                    addRoleIds.add(roleId);
                }
            }
            HashMap<Integer, Integer> removeRoleIds = new HashMap<>();
            for(int userRoleId:userRoleIds.keySet()){
                if(!roleIDs.contains(userRoleIds.get(userRoleId))){
                    removeRoleIds.put(userRoleId, userRoleIds.get(userRoleId));
                }
            }

            removeRoleIds.forEach((userRoleId, roleId) -> {
                session.delete(session.find(UserRole.class, userRoleId));
            });
            addRoleIds.forEach(id -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(id);
                userRole.setUserId(user.getUserId());
                session.persist(userRole);
            });
            if(removeRoleIds.size() == 0)
                session.merge(user);
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private boolean checkContain(String query, String param){
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery(query);
        q.setParameter("s1", param);
        try {
            Object o = q.getSingleResult();
            if(o == null || (long)o == 0)
                return false;
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
    @Override
    public boolean checkUsername(String username) {
        return checkContain("select count(*) from User where username=:s1", username);
    }

    @Override
    public boolean checkEmail(String email) {
        return checkContain("select count(*) from User where email=:s1", email);
    }

    @Override
    public boolean checkPhone(String phone) {
        return checkContain("select count(*) from User where phone=:s1", phone);
    }

    @Override
    public boolean checkPassword(int userId, String password) {
        try {
            return checkContain("select count(*) from User where userId=" + userId +" and password=:s1", UserUtils.hashPassword(password));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
    @Override
    public boolean login(UserLoginRequest request) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("select count(*) from User where username=:s1 and password=:s2");
        q.setParameter("s1", request.getUsername());
        try {
            q.setParameter("s2",  UserUtils.hashPassword(request.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        try {
            Object o = q.getSingleResult();
            if(o == null || (long)o == 0)
                return false;
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<UserViewModel> getTopUserByTotalOrder(int top) {

        ArrayList<UserViewModel> users = UserService.getInstance().retrieveAllUser(new UserGetPagingRequest());

        users.sort((o1, o2) -> (int) (o2.getTotalOrders() - o1.getTotalOrders()));

        ArrayList<UserViewModel> customers = new ArrayList<>();
        for (int i = 0; i< users.size();i++){
            if(i < top){
                customers.add(users.get(i));
            }
        }

        return customers;
    }

    @Override
    public UserViewModel getUserByUserName(String username) {
        Session session = HibernateUtils.getSession();

        Query q = session.createQuery("from User where username=:s1");
        q.setParameter("s1",username);
        Object o = q.getSingleResult();
        User user = null;
        if(o != null){
            user = (User)o;
        }
        if(user != null){
            return getUserViewModel(user, session);
        }
        return null;
    }

    @Override
    public UserViewModel getUserByEmail(String email) {
        try {
            Session session = HibernateUtils.getSession();

            Query q = session.createQuery("from User where email=:s1");
            q.setParameter("s1", email);
            Object o = q.getSingleResult();
            User user = null;
            if (o != null) {
                user = (User) o;
            }
            if (user != null) {
                return getUserViewModel(user, session);
            }
        }catch(Exception e){
            return null;
        }
        return null;
    }

    @Override
    public long getTotalUser() {
        return HibernateUtils.count("User","");
    }
    private UserRoleViewModel getUserRoleViewModel(UserRole userRole, Session session){
        UserRoleViewModel userRoleViewModel = new UserRoleViewModel();
        Query q1 = session.createQuery("select username from User where id = :s1");
        q1.setParameter("s1",userRole.getUserId());

        Query q2 = session.createQuery("select roleName from Role where roleId = :s1");
        q2.setParameter("s1",userRole.getRoleId());
        userRoleViewModel.setRoleId(userRole.getRoleId());
        userRoleViewModel.setUserId(userRole.getUserId());
        userRoleViewModel.setUserName(q1.getSingleResult().toString());
        userRoleViewModel.setRoleName(q2.getSingleResult().toString());

        return userRoleViewModel;
    }
    @Override
    public ArrayList<UserRoleViewModel> getUserRoleByUserId(int userId) {
        ArrayList<UserRoleViewModel> userRoles = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        Query q1 = session.createQuery("from UserRole where userId=:s1");
        q1.setParameter("s1",userId);
        List<UserRole> urs = q1.list();
        if(urs != null){
            for(UserRole ur:urs){
                userRoles.add(getUserRoleViewModel(ur, session));
            }
        }
        return userRoles;
    }

    @Override
    public boolean forgotPassword(String email) {
        String randomPassword = RandomStringUtils.randomAlphabetic(10);
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("from User where email=:s1");
        q.setParameter("s1", email);
        User u = null;
        try {
            u = (User) q.getSingleResult();
            u.setPassword(UserUtils.hashPassword(randomPassword));
        }catch(Exception e){
            return false;
        }
        boolean res = HibernateUtils.merge(u);
        if(!res)
            return false;
        MailJetService.getInstance().sendMail(u.getFirstName() + " " + u.getLastName(), email, "<h2>Chào " + u.getFirstName() + " " + u.getLastName() + ", </h2><h3>Mật khẩu mới cho tài khoản <em>" + u.getUsername() + "</em>: " + randomPassword + " </h3>" + "<h4>Bạn vui lòng đổi mật khẩu sau khi đăng nhập. Xin cảm ơn!!!</h4>", "(FurSshop) Mật khẩu mới ");
        return true;
    }
}
