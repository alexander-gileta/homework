public class PalindromSolver {

    static boolean isPalindrome(int n) {
        String s = "" + n;

        if (s.length() % 2 == 0) {
            for (int i = 0; i < s.length()/2; i++) {
                if (s.charAt(i) != s.charAt(s.length()-i-1)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < (s.length()-1)/2; i++) {
                if (s.charAt(i) != s.charAt(s.length()-i-1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPalindromeDescendant(int number) {
        if (((int) Math.floor(Math.log10(number)) + 1) < 2) {
            return false;
        }

        if (isPalindrome(number)) {
            return true;
        }

        String s = "" + number;
        StringBuilder str = new StringBuilder(); 

        if (s.length() % 2 == 1) {
            for (int i = 0; i < s.length()-1; i+=2) {
                addSum(str, s, i);
            }

            str.append(s.charAt(s.length()-1));
        } else {
            for (int i = 0; i < s.length(); i+=2) {
                addSum(str, s, i);
            }
        }

        return isPalindromeDescendant(Integer.parseInt(str.toString()));
    }

    static void addSum(StringBuilder str, String s, int i) {
        str.append(Integer.parseInt ("" + s.charAt(i)) + Integer.parseInt ("" + s.charAt(i+1)));
    }
}
