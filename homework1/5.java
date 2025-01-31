class PalindromSolver {
    public static boolean isPalindromeDescendant(int number) {
      String strNumber = String.valueOf(number);
      if (isPalindrome(strNumber)) {
        return true;
      }
  
      while (strNumber.length() > 1) {
        strNumber = generateDescendant(strNumber);
        if (isPalindrome(strNumber)) {
          return true;
        }
      }
  
      return false;
    }
  
    private static boolean isPalindrome(String str) {
      int left = 0;
      int right = str.length() - 1;
      while (left < right) {
        if (str.charAt(left) != str.charAt(right)) {
          return false;
        }
        left++;
        right--;
      }
      return true;
    }