public class GameState {
    private String[][] board;
    public static final int BOARD_SIZE = 3;
    public static final int BOARD_COLS = 3;
    public static final int BOARD_ROWS = 3;

    public GameState() {
        this.board = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = " ";
            }
        }
    }

    public String[][] getBoard() {
        return this.board;
    }

    public String getBoardPosition(int x, int y) {
        return this.board[x][y];
    }

    public void setBoardPosition(int x, int y, String move) {
        this.board[x][y] = move;
    }
}
