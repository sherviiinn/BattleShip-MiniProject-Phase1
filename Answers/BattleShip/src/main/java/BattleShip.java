import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;
public class BattleShip {

    // Grid size for the game
    static final int GRID_SIZE = 10;

    // Player 1's main grid containing their ships
    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];

    // Player 2's main grid containing their ships
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];

    // Player 1's tracking grid to show their hits and misses
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    // Player 2's tracking grid to show their hits and misses
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    // Scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize grids for both players
        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);

        // Place ships randomly on each player's grid
        placeShips(player1Grid);
        placeShips(player2Grid);
        // Variable to track whose turn it is
        boolean player1Turn = true;
        int x = 1;
        // Main game loop, runs until one player's ships are all sunk
        while (!isGameOver(x)) {
            if (player1Turn) {
                x=1;
                System.out.println("Player 1's turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid);
            } else {
                x=2;
                System.out.println("Player 2's turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }

        System.out.println("Game Over!");
    }

    static void initializeGrid(char[][] grid) {
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = '~';
            }
        }
    }

    static void placeShips(char[][] grid) {
        Random rand = new Random();
        int y = 2;
        while (y <= 5) {
            int x = rand.nextInt(10);//satr
            int w = rand.nextInt(10);//soton
            int z = rand.nextInt(3) + 1;
            boolean place = true;
            if (z == 1) {
                if (w - y < 0) {
                    continue;
                } else {
                    for (int j = w; j > w - y; j--) {
                        if (grid[x][j] == '~') {
                            place = true;
                        }else{
                            place = false;
                            break;
                        }
                    }
                    if(place) {
                        for (int j = w; j > w - y; j--){
                            grid[x][j] = 'S';
                        }
                    }else{
                        continue;
                    }
                }
            } else if (z == 2) {
                if(x + y >= GRID_SIZE) {
                    continue;
                }
                else {
                    for (int j = x; j < x + y; j++) {
                        if (grid[j][w] == '~') {
                            place = true;
                        } else {
                            place = false;
                            break;
                        }
                    }
                    if (place) {
                        for (int j = x; j < x + y; j++) {
                            grid[j][w] = 'S';
                        }
                    }else{
                        continue;
                    }
                }

            } else if (z == 3) {
                if (w + y >= GRID_SIZE) {
                    continue;
                }
                else {
                    for (int j = w; j < w + y; j++) {
                        if (grid[x][j] == '~') {
                            place = true;
                        } else {
                            place = false;
                            break;
                        }
                    }
                    if (place) {
                        for (int j = w; j < w + y; j++) {
                            grid[x][j] = 'S';
                        }
                    }else{
                        continue;
                    }
                }
            } else if (z == 4) {
                if (x - y >= GRID_SIZE) {
                    continue;
                }
                else {
                    for (int j = x; j > x - y; j--) {
                        if (grid[j][w] == '~') {
                            place = true;
                        } else {
                            place = false;
                            break;
                        }
                    }
                    if (place) {
                        for (int j = x; j > x - y; j--) {
                            grid[j][w] = 'S';
                        }
                    }else{
                        continue;
                    }
                }
            }
            y++;
        }
    }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        System.out.println("\nEnter Target (For Example A5) : ");
        String opponentPlayer = scanner.nextLine();
        char[] opponentPlayerArray = opponentPlayer.toCharArray();
        if (!isValidInput(opponentPlayer)) {
            return;
        }
        int x = (int)opponentPlayerArray[0]-65;
        int y = (int)opponentPlayerArray[1]-48;
        if(trackingGrid[y][x] == 'O' || trackingGrid[y][x] == 'X') {
            System.out.println("You Cannot Have a Duplicate Selection");
            return;
        }
        else if(opponentGrid[y][x] == '~') {
            System.out.println("Miss!");
            trackingGrid[y][x] = 'O';
        }else if(opponentGrid[y][x] == 'S'){
            System.out.println("Hit");
            trackingGrid[y][x] = 'X';
        }

    }

    static boolean isGameOver(int y) {
        if (y == 1) {
            if(allShipsSunk(player1TrackingGrid)){
                System.out.println("Player 1 has Won");
                printGrid(player1TrackingGrid);
                return true;
            }else{
                return false;
            }
        }else if(y == 2) {
            if(allShipsSunk(player2TrackingGrid)){
                System.out.println("Player 2 has Won");
                printGrid(player2TrackingGrid);
                return true;
            }else{
                return false;
            }
        }
            return false;
    }

    static boolean allShipsSunk(char[][] grid) {
        int x =0;
        for( int y = 0; y < GRID_SIZE; y++){
            for(int j = 0; j < GRID_SIZE; j++){
                if(grid[y][j] == 'X'){
                    x++;
                }
            }
        }
        if(x == 14){
            return true;
        }else{
            return false;
        }

    }

    static boolean isValidInput(String input) {
        char[] chars = input.toCharArray();
        if (chars.length > 2) {
            System.out.println("Invalid Input Please try again");
            return false;
        } else if ((int) chars[0] < 65 || (int) chars[0] > 74 || (int) chars[1] < 48 || (int) chars[1] > 57) {
            System.out.println("Invalid Input Please try again");
            return false;
        }
        return true;
    }

    static void printGrid(char[][] grid) {
        System.out.print("  ");
        for(int i = 0; i < GRID_SIZE; i++) {
            System.out.print((char)((int)'A'+i));
            System.out.print(" ");
        }
        System.out.println("");
        for(int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i+" ");
            for(int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
