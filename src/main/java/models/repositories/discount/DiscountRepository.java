package models.repositories.discount;

import models.entities.Discount;
import models.view_models.discounts.DiscountCreateRequest;
import models.view_models.discounts.DiscountGetPagingRequest;
import models.view_models.discounts.DiscountUpdateRequest;
import models.view_models.discounts.DiscountViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import utils.constants.DISCOUNT_STATUS;

import java.util.ArrayList;
import java.util.List;

public class DiscountRepository implements IDiscountRepository{
    private static DiscountRepository instance = null;
    public static DiscountRepository getInstance(){
        if(instance == null)
            instance = new DiscountRepository();
        return instance;
    }
    @Override
    public int insert(DiscountCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Discount discount = new Discount();
        discount.setDiscountCode(request.getDiscountCode());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStatus(request.getStatus());
        discount.setDateStart(request.getStartDate());
        discount.setDateEnd(request.getEndDate());
        discount.setQuantity(request.getQuantity());
        if(request.getQuantity() == 0){
            discount.setStatus(DISCOUNT_STATUS.IN_ACTIVE);
        }
        if(request.getStatus() == DISCOUNT_STATUS.IN_ACTIVE){
            discount.setQuantity(0);
        }
        int discountId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(discount);
            discountId = discount.getDiscountId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return discountId;
    }

    @Override
    public boolean update(DiscountUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Discount discount = session.find(Discount.class, request.getDiscountId());

        discount.setDiscountCode(request.getDiscountCode());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setDateStart(request.getStartDate());
        discount.setDateEnd(request.getEndDate());
        discount.setQuantity(request.getQuantity());
        discount.setStatus(request.getStatus());
        if(request.getQuantity() == 0){
            discount.setStatus(DISCOUNT_STATUS.IN_ACTIVE);
        }
        if(request.getStatus() == DISCOUNT_STATUS.IN_ACTIVE){
            discount.setQuantity(0);
        }
        return HibernateUtils.merge(discount);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Discount discount = session.find(Discount.class, entityId);
        discount.setStatus(DISCOUNT_STATUS.SUSPENDED);
        discount.setQuantity(0);
        session.close();
        return HibernateUtils.merge(discount);
    }
    private String getStatus(int i){
        String status = "";
        switch (i){
            case DISCOUNT_STATUS.EXPIRED:
                status = "Hết hạn";
                break;
            case DISCOUNT_STATUS.ACTIVE:
                status = "Còn mã";
                break;
            case DISCOUNT_STATUS.IN_ACTIVE:
                status = "Hết mã";
                break;
            case DISCOUNT_STATUS.SUSPENDED:
                status = "Ngưng áp dụng";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private DiscountViewModel getDiscountViewModel(Discount discount, Session session){
        DiscountViewModel discountViewModel = new DiscountViewModel();

        discountViewModel.setDiscountId(discount.getDiscountId());
        discountViewModel.setDiscountCode(discount.getDiscountCode());
        discountViewModel.setDiscountValue(discount.getDiscountValue());
        discountViewModel.setStartDate(DateUtils.dateTimeToStringWithFormat(discount.getDateStart(),"yyyy-MM-dd HH:mm"));
        discountViewModel.setEndDate(DateUtils.dateTimeToStringWithFormat(discount.getDateEnd(),"yyyy-MM-dd HH:mm"));
        discountViewModel.setStatus(discount.getStatus());
        discountViewModel.setQuantity(discount.getQuantity());
        discountViewModel.setStatusCode(getStatus(discount.getStatus()));

        return discountViewModel;
    }
    @Override
    public DiscountViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Discount discount = session.find(Discount.class, entityId);

        DiscountViewModel discountViewModel = getDiscountViewModel(discount, session);
        session.close();

        return discountViewModel;
    }

    @Override
    public ArrayList<DiscountViewModel> retrieveAll(DiscountGetPagingRequest request) {
        ArrayList<DiscountViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Discount", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Discount> discounts = q.list();

        for(Discount discount:discounts){
            DiscountViewModel v = getDiscountViewModel(discount, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public DiscountViewModel getByDiscountCode(String discountCode) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("from Discount where discountCode=:s1");
        q.setParameter("s1", discountCode);
        try {
            Object discount = q.getSingleResult();
            if (discount == null)
                return null;
            return getDiscountViewModel((Discount) discount, session);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean updateQuantity(int discountId) {
        Session session = HibernateUtils.getSession();
        Discount discount = session.find(Discount.class, discountId);
        if(discount.getQuantity() == 0)
            return false;
        discount.setQuantity(discount.getQuantity() - 1);
        if(discount.getQuantity() == 0){
            discount.setQuantity(0);
            discount.setStatus(DISCOUNT_STATUS.IN_ACTIVE);
        }
        session.close();

        return HibernateUtils.merge(discount);
    }
}
