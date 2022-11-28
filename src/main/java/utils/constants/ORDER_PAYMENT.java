package utils.constants;

import java.util.HashMap;

public class ORDER_PAYMENT {
    public static final int PAID = 1;
    public static final int COD = 0;

    public static HashMap<String, Integer> Method = new HashMap<String, Integer>(){
        {
            put("Paid", PAID);
            put("COD", COD);
        }
    };
}
