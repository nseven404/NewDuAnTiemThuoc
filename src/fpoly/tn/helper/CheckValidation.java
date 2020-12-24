package fpoly.tn.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckValidation {
    
    public static boolean checkStringTrong(String s_data) {
        return ((s_data == null) || s_data.trim().equals(""));
    }
    
    public static boolean checkEmailValid(String emailStr) {
        Pattern pattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher matcher = pattern.matcher(emailStr);
        return matcher.matches();
    }
    
    
}
