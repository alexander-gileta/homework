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
}