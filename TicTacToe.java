import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private static final int BOARD_SIZE = 3;
    private static final char EMPTY_CELL = '-';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char AI_PLAYER = PLAYER_O;

    private char[][] board;
    private boolean isPlayerXTurn;
    private boolean gameFinished;

    public TicTacToe() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        isPlayerXTurn = true;
        gameFinished = false;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    private void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    private boolean makeMove(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || board[row][col] != EMPTY_CELL) {
            return false; 
        }
        board[row][col] = isPlayerXTurn ? PLAYER_X : PLAYER_O;
        return true;
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGameOver() {
        return checkWin(PLAYER_X) || checkWin(PLAYER_O) || isBoardFull();
    }

    private void switchTurn() {
        isPlayerXTurn = !isPlayerXTurn;
    }

    private void playMove() {
        if (isPlayerXTurn) {
            Scanner scanner = new Scanner(System.in);
            int row, col;
            while (true) {
                try {
                    System.out.println("Player X's turn. Enter row and column (0-2):");
                    row = scanner.nextInt();
                    col = scanner.nextInt();
                    if (makeMove(row, col)) {
                        switchTurn();
                        break;
                    } else {
                        System.out.println("Invalid move. Try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers only.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }
        } else {
            // AI move
            System.out.println("Player O's turn (AI).");
            int row, col;
            do {
                row = (int) (Math.random() * BOARD_SIZE);
                col = (int) (Math.random() * BOARD_SIZE);
            } while (!makeMove(row, col));
      
            switchTurn();
        }
    }

    private void announceResult() {
        if (checkWin(PLAYER_X)) {
            System.out.println("Player X wins!");
        } else if (checkWin(PLAYER_O)) {
            System.out.println("Player O wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public void playGame() {
        while (!gameFinished) {
            printBoard();
            playMove();
            if (isGameOver()) {
                gameFinished = true;
                printBoard();
                announceResult();
            }
        }
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.playGame();
    }
}