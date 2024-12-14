import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean gameRunning = true;
        Scanner input = new Scanner(System.in);
        String s = new String();
        GameState gameState = new GameState();
        String currentPlayer = "O";
        int[] move = new int[2];

        System.out.println("Game state instantiated. Board Game lengths are: " + gameState.getBoard().length + " and "
                + gameState.getBoard()[0].length);
        while (gameRunning) {
            System.out.println("Would you like to play a game?");
            s = input.nextLine();

            if (s.toUpperCase().equals("YES") || s.toUpperCase().equals("Y") || s.equals("")) {
                System.out.println("The game is being played!");
                input.nextLine();
                while (gameRunning) {
                    currentPlayer = playerSwap(currentPlayer);
                    System.out.println("Player " + currentPlayer + "'s turn.");
                    System.out.println("Enter a co-ordinate, with no spaces, then press Enter. Example: A1, B3");
                    printGameState(gameState);
                    move = collectInput(gameState);
                    selectPosition(gameState, move[0], move[1], currentPlayer);
                    gameRunning = !isGameOver(gameState);
                    System.out.println();
                }
                printGameState(gameState);
                System.out.println("Game Over!");
            } else {
                System.out.println("Bye");
                gameRunning = false;
                break;
            }

        }
        input.close();
    }

    public static void printGameState(GameState gameState) {
        String[][] board = gameState.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < board[i].length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println("");
            if (i < board.length - 1) {
                System.out.print("----------");
            }
            System.out.println("");
        }
    }

    public static void selectPosition(GameState game, int x, int y, String input) {
        if (isValidMove(game, x, y)) {
            game.setBoardPosition(x, y, input);
        } else {
            System.out.println("Invalid input");
        }
    }

    public static boolean isValidMove(GameState game, int x, int y) {
        // Check bounds
        if (x > game.getBoard().length || y > game.getBoard()[0].length) {
            return false;
        }

        if (!game.getBoardPosition(x, y).isBlank()) {
            System.out.println("Position is filled");
            return false;
        }

        return true;
    }

    public static int[] collectInput(GameState game) {
        Scanner input = new Scanner(System.in);
        String s;
        int[] move = new int[game.getBoard().length];
        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("Input: ");
            s = input.nextLine();
            try {
                move[0] = Character.getNumericValue(Character.toUpperCase(s.toCharArray()[0])) - 10;
                System.out.println("Move[0]: " + move[0]);
                move[1] = s.toCharArray()[1] - 49;
                System.out.println("Move[1]: " + move[1]);
                if (!isValidMove(game, move[0], move[1])) {
                    throw new Exception();
                }
                invalidInput = false;
            } catch (Exception e) {
                System.out.println("Something went wrong. Please try again");
                invalidInput = true;
            }
        }
        return move;
    }

    public static boolean isGameOver(GameState gameState) {
        int x = GameState.BOARD_COLS;
        int y = GameState.BOARD_ROWS;

        int counter = 1;
        String checking = "";

        // Checking Horizontal crosses
        for (int i = 0; i < x; i++) {
            checking = gameState.getBoardPosition(i, 0);
            if (checking.isBlank() || checking.isEmpty()) {
                break;
            }
            counter = 1;
            for (int j = 1; j < y; j++) {
                if (checking.equals(gameState.getBoardPosition(i, j))) {
                    counter++;
                    if (counter == GameState.BOARD_ROWS) {
                        return true;
                    }
                }

            }
        }

        // Checking Vertical crosses
        for (int i = 0; i < x; i++) {
            checking = gameState.getBoardPosition(0, i);
            if (checking.isBlank() || checking.isEmpty()) {
                break;
            }
            counter = 1;
            for (int j = 1; j < y; j++) {
                if (checking.equals(gameState.getBoardPosition(j, i))) {
                    counter++;
                    if (counter == GameState.BOARD_COLS) {
                        return true;
                    }
                }
            }
        }

        // Checking diagonal X
        checking = "X";
        if (checkDiagonals(gameState, "X")) {
            return true;
        }
        if (checkDiagonals(gameState, "O")) {
            return true;
        }

        // Checking if board is full
        if (isBoardFull(gameState)) {
            return true;
        }

        return false;
    }

    public static boolean checkDiagonals(GameState gameState, String checking) {
        int n = GameState.BOARD_SIZE;
        boolean downwardDiagonalWin = true;
        boolean upwardDiagonalWin = true;
        for (int i = 0; i < n; i++) {
            if (!gameState.getBoardPosition(i, i).equals(checking)) {
                downwardDiagonalWin = false;
            }

            if (!gameState.getBoardPosition(i, n - 1 - i).equals(checking)) {
                upwardDiagonalWin = false;
            }
        }
        return upwardDiagonalWin || downwardDiagonalWin;
    }

    public static boolean isBoardFull(GameState gameState) {
        for (int i = 0; i < GameState.BOARD_ROWS; i++) {
            for (int j = 0; j < GameState.BOARD_COLS; j++) {
                if (gameState.getBoardPosition(i, j).isBlank()) {
                    return false;
                }
            }
        }
        System.out.println("The board is full");
        return true;
    }

    public static String playerSwap(String player) {
        if (player.equals("X")) {
            return "O";
        } else {
            return "X";
        }
    }
}
