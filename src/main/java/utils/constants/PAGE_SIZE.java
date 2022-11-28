package utils.constants;

import java.util.HashMap;

public class PAGE_SIZE {

    public static HashMap<Integer, Integer> PageSize = new HashMap<Integer, Integer>(){
        {
            put(1, 1);
            put(2, 2);
            put(3, 5);
            put(4, 10);
        }
    };
}
