public class RomanConverter {

    public static String convertToRoman(int num) {
        String[] romanNumerals = {
            "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"
        };
        int[] arabicValues = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        
        StringBuilder roman = new StringBuilder();
        
        for (int i = arabicValues.length - 1; i >= 0; i--) {
            while (num >= arabicValues[i]) {
                roman.append(romanNumerals[i]);
                num -= arabicValues[i];
            }
        }
        
        return roman.toString();
    }

    public static void main(String[] args) {
        System.out.println(convertToRoman(2));
        System.out.println(convertToRoman(12));
        System.out.println(convertToRoman(16));
    }
}
