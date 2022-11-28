package utils.constants;

import java.util.HashMap;

public class SORT_BY {
    public static final int  BY_NAME_AZ = 0;
    public static final int  BY_NAME_ZA = 1;
    public static final int  BY_PRICE_AZ = 2;
    public static final int  BY_PRICE_ZA = 3;

    public static HashMap<String, Integer> SortBy = new HashMap<String, Integer>(){
        {
            put("Theo tên A-Z", BY_NAME_AZ);
            put("Theo tên Z-A", BY_NAME_ZA);
            put("Theo giá tăng dần", BY_PRICE_AZ);
            put("Theo giá giảm dần", BY_PRICE_ZA);
        }
    };
}
