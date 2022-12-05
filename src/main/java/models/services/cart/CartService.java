package models.services.cart;

import models.repositories.cart.CartRepository;
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
        return CartRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateCartItem(CartItemUpdateRequest request) {
        return CartRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteCartItem(Integer cartItemId) {
        return CartRepository.getInstance().delete(cartItemId);
    }
    @Override
    public CartItemViewModel retrieveCartItemById(Integer cartItemId) {
        return CartRepository.getInstance().retrieveById(cartItemId);
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveAllCartItem(CartItemGetPagingRequest request) {
        return CartRepository.getInstance().retrieveAll(request);
    }

    @Override
    public boolean deleteCartByUserId(int userId) {
        return CartRepository.getInstance().deleteCartByUserId(userId);
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveCartByUserId(int userId) {
        return CartRepository.getInstance().retrieveCartByUserId(userId);
    }

    @Override
    public int getCartIdByUserId(int userId) {
        return CartRepository.getInstance().getCartIdByUserId(userId);
    }

    @Override
    public CartItemViewModel getCartItemContain(int cartId, int productId) {
        return CartRepository.getInstance().getCartItemContain(cartId, productId);
    }

    @Override
    public int canUpdateQuantity(int cartItemId, int quantity) {
        return CartRepository.getInstance().canUpdateQuantity(cartItemId, quantity);
    }

    @Override
    public void updateQuantityByProductId(int productId, int quantity) {
        CartRepository.getInstance().updateQuantityByProductId(productId, quantity);
    }

    @Override
    public String addProductToCart(int productId, int quantity, int userId) {
        return CartRepository.getInstance().addProductToCart(productId, quantity, userId);
    }

    @Override
    public BigDecimal getTotalCartItemPriceByUserId(int userId) {
        return CartRepository.getInstance().getTotalCartItemPriceByUserId(userId);
    }
}
