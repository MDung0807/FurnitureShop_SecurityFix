package utils.constants;

import java.util.HashMap;

public class USER_STATUS {
    public static final int  IN_ACTIVE = 0;
    public static final int  ACTIVE = 1;
    public static final int  UN_CONFIRM = 2;

    public static HashMap<String, Integer> Status = new HashMap<String, Integer>(){
        {
            put("Ngưng hoạt động", IN_ACTIVE);
            put("Đang hoạt động", ACTIVE);
        }
    };
}
