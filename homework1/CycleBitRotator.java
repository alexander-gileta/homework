class KnightBoardSolver {
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
