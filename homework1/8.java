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