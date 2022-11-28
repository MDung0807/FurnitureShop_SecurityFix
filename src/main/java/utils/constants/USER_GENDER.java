package utils.constants;

import java.util.HashMap;

public class USER_GENDER {
    public static final int MALE = 0;
    public static final int FEMALE = 1;
    public static final int OTHER = 2;

    public static HashMap<String, Integer> Gender = new HashMap<String, Integer>(){
        {
            put("Nam", MALE);
            put("Nữ", FEMALE);
            put("Khác", OTHER);
        }
    };
}
