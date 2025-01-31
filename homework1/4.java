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