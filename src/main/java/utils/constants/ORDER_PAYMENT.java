package utils.constants;

import java.util.HashMap;

public class ORDER_PAYMENT {
    public static final int PAYPAL = 1;
    public static final int COD = 0;

    public static HashMap<String, Integer> Method = new HashMap<String, Integer>(){
        {
            put("PayPal", PAYPAL);
            put("COD", COD);
        }
    };
}
