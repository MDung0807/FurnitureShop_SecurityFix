package utils;

import java.math.BigDecimal;

public class StringUtils {
    public static String removeNonDigit(String str) {
        if (str == null || str.isEmpty()) {
            str = "";
        }

        str = str.replaceAll("[^0-9]", "");

        if (str == null || str.isEmpty()) {
            str = "";
        }
        return str;
    }
    public static int toInt(String s){
        int res = 0;
        try {
            res = Integer.parseInt(s);
        }catch(Exception e){

        }
        return res;
    }
    public static double toDouble(String s){
        double res = 0;
        try {
            res = Double.parseDouble(s);
        }catch(Exception e){

        }
        return res;
    }
    public static long toLong(String s){
        long res = 0;
        try {
            res = Long.parseLong(s);
        }catch(Exception e){

        }
        return res;
    }
    public static BigDecimal toBigDecimal(String str) {
        if(str == null)
            return BigDecimal.valueOf(0);
        BigDecimal result;
        //str = removeNonDigit(str);

        try {
            result = new BigDecimal(str);
        } catch (NumberFormatException e) {
            result = BigDecimal.valueOf(0);
        }

        return result;
    }
}
