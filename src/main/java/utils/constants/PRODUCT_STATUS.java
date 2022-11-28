package utils.constants;

import java.util.HashMap;

public class PRODUCT_STATUS {
    public static final int  IN_STOCK = 1;
    public static final int  OUT_STOCK = 0;
    public static final int  SUSPENDED = 2;

    public static HashMap<String, Integer> Status = new HashMap<String, Integer>(){
        {
            put("Còn hàng", IN_STOCK);
            put("Hết hàng", OUT_STOCK);
            put("Ngừng kinh doanh", SUSPENDED);
        }
    };
}
