package utils.constants;

import java.util.HashMap;

public class BRAND_STATUS {
    public static final int  ACTIVE = 0;
    public static final int  IN_ACTIVE = 1;

    public static HashMap<String, Integer> Status = new HashMap<String, Integer>(){
        {
            put("Đang dùng", ACTIVE);
            put("Đã xoá", IN_ACTIVE);
        }
    };
}
