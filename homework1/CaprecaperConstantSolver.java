  class CaprecaperConstantSolver {
    public static int countK(int number) {
        private static String generateDescendant(String str) {
            StringBuilder descendant = new StringBuilder();
            for (int i = 0; i < str.length() - 1; i++) {
              int sum = (str.charAt(i) - '0') + (str.charAt(i + 1) - '0');
              descendant.append(sum);
            }
            return descendant.toString();
          }
        
          private static int countK(int number, int steps) {
            if (number == 6174) {
              return steps;
            }
        
            String numStr = String.format("%04d", number);
            String ascending = sortDigits(numStr, true);
            String descending = sortDigits(numStr, false);
        
            int larger = Integer.parseInt(descending);
            int smaller = Integer.parseInt(ascending);
        
            int nextNumber = larger - smaller;
        
            return countK(nextNumber, steps + 1);
          }
        
          private static String sortDigits(String str, boolean ascending) {
            char[] chars = str.toCharArray();
            java.util.Arrays.sort(chars);
            return ascending
                ? new String(chars)
                : new StringBuilder(new String(chars)).reverse().toString();
          }
        
          private static boolean hasSameDigits(int number) {
            String numStr = String.valueOf(number);
            char firstChar = numStr.charAt(0);
            for (char c : numStr.toCharArray()) {
              if (c != firstChar) {
                return false;
              }
            }
            return true;
          }
    }
}
