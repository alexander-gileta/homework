import java.util.regex.Pattern;

public class PasswordValidator {

    public static boolean isValidPassword(String password) {
        String regex = ".*[~!@#$%^&*|].*";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }

    public static void main(String[] args) {
        System.out.println(isValidPassword("password123"));
        System.out.println(isValidPassword("password!"));
        System.out.println(isValidPassword(""));
    }
}