import java.util.regex.Pattern;

public class LicensePlateValidator {

    public static boolean isValidLicensePlate(String licensePlate) {
        String regex = "^[А-Я]{1}\\d{3}[А-Я]{2}\\d{3}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(licensePlate).matches();
    }

    public static void main(String[] args) {
        System.out.println(isValidLicensePlate("А123ВЕ777"));
        System.out.println(isValidLicensePlate("О777ОО177"));
        System.out.println(isValidLicensePlate("123АВЕ777"));
        System.out.println(isValidLicensePlate("А123ВГ77"));
        System.out.println(isValidLicensePlate("А123ВЕ7777"));
    }
}