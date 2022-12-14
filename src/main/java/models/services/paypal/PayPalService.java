package models.services.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import models.repositories.user.UserRepository;
import models.view_models.cart_items.CartItemViewModel;
import models.view_models.order_items.OrderItemViewModel;
import models.view_models.orders.OrderCreateRequest;
import models.view_models.orders.OrderViewModel;
import models.view_models.users.UserViewModel;
import utils.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PayPalService implements IPayPalService{
    private static final String CLIENT_ID = "AR52hDoJM7wVzALLe_nPlzKxMS8CTJfoUAeRt9IocXy4c4EDG0T2KPBwG4f38RtLYz9Pem_DDPkT0-ID";
    private static final String CLIENT_SECRET = "EHv86VipUu7nzvBRTw1Sff3BeTjPkmIjekg_Uemr-T3im9MzUJQusec2B8boB2MxwklloYQNL0RfpQqD";
    private static final String MODE = "sandbox";
    private static PayPalService instance;
    public static PayPalService getInstance(){
        if(instance == null)
            instance = new PayPalService();
        return instance;
    }
    @Override
    public String authorizePayment(ArrayList<CartItemViewModel> cartItems, OrderCreateRequest orderCreateRequest, String context) throws PayPalRESTException {

        Payer payer = getPayerInformation(orderCreateRequest);
        RedirectUrls redirectUrls = getRedirectUrls(context);
        ArrayList<Transaction> transactions = getTransactionInformation(cartItems, orderCreateRequest);
        Payment reqPayment = new Payment();
        reqPayment.setIntent("authorize");
        reqPayment.setRedirectUrls(redirectUrls);
        reqPayment.setPayer(payer);
        reqPayment.setTransactions(transactions);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = reqPayment.create(apiContext);

        return getApprovalLink(approvedPayment);
    }

    @Override
    public Payer getPayerInformation(OrderCreateRequest orderCreateRequest) {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        UserViewModel user = UserRepository.getInstance().retrieveById(orderCreateRequest.getUserId());
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setEmail(orderCreateRequest.getEmail());
        payerInfo.setFirstName(user.getFirstName());
        payerInfo.setLastName(user.getLastName());
        if(orderCreateRequest.getDiscountId() != 0)
            payerInfo.setTaxId(String.valueOf(orderCreateRequest.getDiscountId()));
        ShippingAddress shippingAddress = new ShippingAddress();

        shippingAddress.setRecipientName(orderCreateRequest.getName());
        shippingAddress.setPhone(orderCreateRequest.getPhone());
        shippingAddress.setCountryCode("VN");
        shippingAddress.setCity(orderCreateRequest.getAddress());
        shippingAddress.setLine1(orderCreateRequest.getAddress());
        //payerInfo.setBillingAddress(shippingAddress);
        payerInfo.setShippingAddress(shippingAddress);
        payer.setPayerInfo(payerInfo);
        return payer;
    }

    @Override
    public RedirectUrls getRedirectUrls(String context) {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(context + "/checkout?cancel=true");
        redirectUrls.setReturnUrl(context + "/checkout/third-party-payment");
        return redirectUrls;
    }

    @Override
    public ArrayList<Transaction> getTransactionInformation(ArrayList<CartItemViewModel> cartItems, OrderCreateRequest orderCreateRequest) {
        Details details = new Details();
        final long VND_TO_USD = 24455;
        details.setShipping(orderCreateRequest.getShipping().divide(BigDecimal.valueOf(VND_TO_USD), 2, RoundingMode.HALF_UP).toString());
        details.setTax("0");

        Amount amount = new Amount();
        amount.setCurrency("USD");

        Transaction transaction = new Transaction();

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        BigDecimal subTotal = BigDecimal.valueOf(0);
        for (CartItemViewModel ci:cartItems){
            if(ci.getQuantity() == 0)
                continue;
            Item item = new Item();
            item.setCurrency("USD");
            item.setName(ci.getProductName());
            item.setPrice(ci.getUnitPrice().divide(BigDecimal.valueOf(VND_TO_USD), 2, RoundingMode.HALF_UP).toString());
            item.setTax("0");
            item.setQuantity(String.valueOf(ci.getQuantity()));
            items.add(item);
            subTotal = subTotal.add(StringUtils.toBigDecimal(item.getPrice()).multiply(StringUtils.toBigDecimal(item.getQuantity())));
        }
        details.setSubtotal(subTotal.toString());
        amount.setTotal(subTotal.add(orderCreateRequest.getShipping()).toString());

        amount.setDetails(details);

        itemList.setItems(items);

        transaction.setAmount(amount);
        transaction.setItemList(itemList);

        ArrayList<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    @Override
    public String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

    @Override
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    @Override
    public Payment handlePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }
}
