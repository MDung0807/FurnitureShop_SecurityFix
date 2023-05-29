package utils.constants;

import java.util.HashMap;

public class USER_STATUS {
    public static final int  IN_ACTIVE = 0;
    public static final int  ACTIVE = 1;
    public static final int  UN_CONFIRM = 2;
    public static final int PASSWORD_HAS_NOT_CHANGED=3;
    public static final int LOCK=4;
    public static HashMap<String, Integer> Status = new HashMap<String, Integer>(){
        {
            put("Ngưng hoạt động", IN_ACTIVE);
            put("Đang hoạt động", ACTIVE);
            put("Password chưa được đổi lại", PASSWORD_HAS_NOT_CHANGED);
            put("Taì khoản bị khóa", LOCK);
        }
    };
}
