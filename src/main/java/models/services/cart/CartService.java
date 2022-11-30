package models.services.cart;

import models.repositories.cart.CartIRepository;
import models.view_models.cart_items.CartItemCreateRequest;
import models.view_models.cart_items.CartItemGetPagingRequest;
import models.view_models.cart_items.CartItemUpdateRequest;
import models.view_models.cart_items.CartItemViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartService implements ICartService {
    private static CartService instance = null;
    public static CartService getInstance(){
        if(instance == null)
            instance = new CartService();
        return instance;
    }
    @Override
    public int insertCartItem(CartItemCreateRequest request) {
        return CartIRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateCartItem(CartItemUpdateRequest request) {
        return CartIRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteCartItem(Integer cartItemId) {
        return CartIRepository.getInstance().delete(cartItemId);
    }
    @Override
    public CartItemViewModel retrieveCartItemById(Integer cartItemId) {
        return CartIRepository.getInstance().retrieveById(cartItemId);
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveAllCartItem(CartItemGetPagingRequest request) {
        return CartIRepository.getInstance().retrieveAll(request);
    }

    @Override
    public boolean deleteCartByUserId(int userId) {
        return CartIRepository.getInstance().deleteCartByUserId(userId);
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveCartByUserId(int userId) {
        return CartIRepository.getInstance().retrieveCartByUserId(userId);
    }

    @Override
    public int getCartIdByUserId(int userId) {
        return CartIRepository.getInstance().getCartIdByUserId(userId);
    }

    @Override
    public CartItemViewModel getCartItemContain(int cartId, int productId) {
        return CartIRepository.getInstance().getCartItemContain(cartId, productId);
    }

    @Override
    public int canUpdateQuantity(int cartItemId, int quantity) {
        return CartIRepository.getInstance().canUpdateQuantity(cartItemId, quantity);
    }

    @Override
    public void updateQuantityByProductId(int productId, int quantity) {
        CartIRepository.getInstance().updateQuantityByProductId(productId, quantity);
    }

    @Override
    public String addProductToCart(int productId, int quantity, int userId) {
        return CartIRepository.getInstance().addProductToCart(productId, quantity, userId);
    }

    @Override
    public BigDecimal getTotalCartItemPriceByUserId(int userId) {
        return CartIRepository.getInstance().getTotalCartItemPriceByUserId(userId);
    }
}
