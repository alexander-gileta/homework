import java.util.regex.Pattern;

public class BinaryStringValidator {

    public static boolean hasAtLeastThreeCharsThirdIsZero(String input) {
        String regex = "^[01]{2}0[01]*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }

    public static boolean startsAndEndsWithSameChar(String input) {
        String regex = "^(0|1).*(0|1)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches() && input.charAt(0) == input.charAt(input.length() - 1);
    }

    public static boolean lengthBetweenOneAndThree(String input) {
        String regex = "^[01]{1,3}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }

    public static void main(String[] args) {
        System.out.println(hasAtLeastThreeCharsThirdIsZero("110"));
        System.out.println(hasAtLeastThreeCharsThirdIsZero("100")); 
        System.out.println(hasAtLeastThreeCharsThirdIsZero("111")); 

        System.out.println(startsAndEndsWithSameChar("010"));
        System.out.println(startsAndEndsWithSameChar("101")); 
        System.out.println(startsAndEndsWithSameChar("10")); 

        System.out.println(lengthBetweenOneAndThree("0"));
        System.out.println(lengthBetweenOneAndThree("01")); 
        System.out.println(lengthBetweenOneAndThree("0000")); 
    }
}