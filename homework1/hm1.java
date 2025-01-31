class VideoLengthHomeWork {
    public static int minutesToSeconds(String videoLenght) {
      int colonIndex = videoLenght.indexOf(":");
      String minutes = videoLenght.substring(0, colonIndex);
      String seconds = videoLenght.substring(colonIndex + 1);
      if (seconds.compareTo("60") < 0) {
        return (Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds));
      } else {
        return -1;
      }
    }
  }
  
  class DigitNumberCounter {
    public static int countDigits(int number) {
      int counter = 0;
      do {
        number /= 10;
        counter++;
      } while (number > 0);
      return counter;
    }
  }
  
  class NestedArray {
    public static boolean isNestable(int[] a1, int[] a2) {
      if (a1.length == 0 || a2.length == 0) {
        return false;
      }
  
      int minA1 = Integer.MAX_VALUE;
      int maxA1 = Integer.MIN_VALUE;
      for (int num : a1) {
        if (num < minA1) {
          minA1 = num;
        }
        if (num > maxA1) {
          maxA1 = num;
        }
      }
  
      int minA2 = Integer.MAX_VALUE;
      int maxA2 = Integer.MIN_VALUE;
      for (int num : a2) {
        if (num < minA2) {
          minA2 = num;
        }
        if (num > maxA2) {
          maxA2 = num;
        }
      }
      return minA1 > minA2 && maxA1 < maxA2;
    }
  }
  
  class BrokenStringRepair {
    public static String fixString(String s) {
      StringBuilder fixedString = new StringBuilder();
  
      for (int i = 0; i < s.length(); i += 2) {
        if (i + 1 < s.length()) {
          fixedString.append(s.charAt(i + 1));
          fixedString.append(s.charAt(i));
        } else {
          fixedString.append(s.charAt(i));
        }
      }
  
      return fixedString.toString();
    }
  }
  
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
  
    class CycleBitRotator {
      public static int rotateRight(int number, int shift) {
        int bitCount = Integer.SIZE;
        shift %= bitCount;
  
        return (number >>> shift) | (number << (bitCount - shift));
      }
  
      public static int rotateLeft(int number, int shift) {
        int bitCount = Integer.SIZE;
        shift %= bitCount;
  
        return (number << shift) | (number >>> (bitCount - shift));
      }
    }
  }
  
  class KnightBoardSolver {
    public static boolean knightBoardCapture(int[][] board) {
      int n = board.length;
  
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (board[i][j] == 1) {
            if (isUnderAttack(board, i, j)) {
              return false;
            }
          }
        }
      }
      return true;
    }
  
    private static boolean isUnderAttack(int[][] board, int x, int y) {
      int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
      int[] dy = {-1, -2, -2, -1, 1, 2, 2, 1};
  
      for (int i = 0; i < 8; i++) {
        int newX = x + dx[i];
        int newY = y + dy[i];
        if (isWithinBounds(newX, newY) && board[newX][newY] == 1) {
          return true;
        }
      }
      return false;
    }
  
    private static boolean isWithinBounds(int x, int y) {
      return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
  }