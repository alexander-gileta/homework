public class PalindromSolver {

    public static int countK(int number) {
        if (number == 6174) {
            return 0; 
        }

        
        String numStr = String.valueOf(number);


        String asc = sortDigits(numStr);
        String desc = new StringBuilder(asc).reverse().toString();

        int high = Integer.parseInt(desc);
        int low = Integer.parseInt(asc);
        int difference = high - low;

        return 1 + countK(difference); 
    }

    private static boolean allDigitsSame(String str) {
        char firstDigit = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != firstDigit) {
                return false; 
            }
        }
        return true;
    }

    private static String sortDigits(String str) {
        char[] digits = str.toCharArray();
        java.util.Arrays.sort(digits); 
        return new String(digits);
    }
}
