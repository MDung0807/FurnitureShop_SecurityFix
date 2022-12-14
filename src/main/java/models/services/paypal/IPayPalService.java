package models.services.paypal;

import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import models.view_models.cart_items.CartItemViewModel;
import models.view_models.order_items.OrderItemViewModel;
import models.view_models.orders.OrderCreateRequest;

import java.util.ArrayList;

public interface IPayPalService {
    String authorizePayment(ArrayList<CartItemViewModel> cartItems, OrderCreateRequest orderCreateRequest, String context) throws PayPalRESTException;
    Payer getPayerInformation(OrderCreateRequest orderCreateRequest);
    RedirectUrls getRedirectUrls(String context);
    ArrayList<Transaction> getTransactionInformation(ArrayList<CartItemViewModel> orderItems, OrderCreateRequest orderCreateRequest);
    String getApprovalLink(Payment approvedPayment);
    Payment getPaymentDetails(String paymentId) throws PayPalRESTException;
    Payment handlePayment(String paymentId, String payerId)
            throws PayPalRESTException;
}
