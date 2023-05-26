package utils.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidate {
    private static final String PATTERN_PASS = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,30}$";

    public static boolean inCorrectFormat(String password){
        if (password.length()<8 || password.length()>=30)
            return true;
        Pattern pattern = Pattern.compile(PATTERN_PASS);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }
}
