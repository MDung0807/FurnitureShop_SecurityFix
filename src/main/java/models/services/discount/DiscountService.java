package models.services.discount;

import models.entities.Discount;
import models.repositories.discount.DiscountRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import utils.constants.DISCOUNT_STATUS;
import models.view_models.discounts.DiscountViewModel;
import models.view_models.discounts.DiscountCreateRequest;
import models.view_models.discounts.DiscountGetPagingRequest;
import models.view_models.discounts.DiscountUpdateRequest;

import java.util.ArrayList;
import java.util.List;

public class DiscountService implements IDiscountService{

    private static DiscountService instance = null;
    public static DiscountService getInstance(){
        if(instance == null)
            instance = new DiscountService();
        return instance;
    }
    @Override
    public int insertDiscount(DiscountCreateRequest request) {
        return DiscountRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateDiscount(DiscountUpdateRequest request) {
        return DiscountRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteDiscount(Integer discountId) {
        return DiscountRepository.getInstance().delete(discountId);
    }

    @Override
    public DiscountViewModel retrieveDiscountById(Integer discountId) {
        return DiscountRepository.getInstance().retrieveById(discountId);
    }

    @Override
    public ArrayList<DiscountViewModel> retrieveAllDiscount(DiscountGetPagingRequest request) {
        return DiscountRepository.getInstance().retrieveAll(request);
    }

    @Override
    public DiscountViewModel getByDiscountCode(String discountCode) {
        return DiscountRepository.getInstance().getByDiscountCode(discountCode);
    }

    @Override
    public boolean updateQuantity(int discountId) {
        return DiscountRepository.getInstance().updateQuantity(discountId);
    }
}
