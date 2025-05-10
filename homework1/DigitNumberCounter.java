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
