package utils.constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ORDER_STATUS {
    public static final int PENDING = 0;
    public static final int READY_TO_SHIP = 1;
    public static final int ON_THE_WAY = 2;
    public static final int DELIVERED = 3;
    public static final int CANCEL = 4;
    public static final int RETURN = 5;
    public static String isCompleted(int orderStatus, int status){
        if(orderStatus >= status)
            return "completed";
        return "";
    }
    public static HashMap<String, Integer> Status = new HashMap<String, Integer>(){
        {
            put("Đang đợi", PENDING);
            put("Sẵn sàng chuyển đi", READY_TO_SHIP);
            put("Đang giao", ON_THE_WAY);
            put("Đã giao thành công", DELIVERED);
            put("Đã huỷ", CANCEL);
            put("Hoàn trả", RETURN);
        }
    };
}
