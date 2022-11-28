package utils;

import utils.constants.ORDER_STATUS;
import utils.constants.PRODUCT_STATUS;

public class HtmlClassUtils {
    public static String generateProductStatusClass(int status){
        String s = "";
        switch (status){
            case PRODUCT_STATUS.IN_STOCK:
                s = "badge badge-success";
                break;
            case PRODUCT_STATUS.OUT_STOCK:
                s = "badge badge-warning";
                break;
            case PRODUCT_STATUS.SUSPENDED:
                s = "badge badge-danger";
                break;
            default:
                s = "";
                break;
        }
        return s;
    }
    public static String generateOrderStatusClass(int status){
        String s = "";
        switch (status){
            case ORDER_STATUS.PENDING:
                s = "badge badge-secondary";
                break;
            case ORDER_STATUS.READY_TO_SHIP:
                s = "badge badge-warning";
                break;
            case ORDER_STATUS.ON_THE_WAY:
                s = "badge badge-info";
                break;
            case ORDER_STATUS.DELIVERED:
                s = "badge badge-success";
                break;
            case ORDER_STATUS.CANCEL:
                s = "badge badge-danger";
                break;
            case ORDER_STATUS.RETURN:
                s = "badge badge-dark";
                break;
            default:
                s = "";
                break;
        }
        return s;
    }
}
