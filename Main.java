package maze;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[][] maze = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                maze[i][j] = 0;
            }
        }
        Arrays.fill(maze[0], 1);
        Arrays.fill(maze[9], 1);
        for (int i = 0; i < 10; i++) {
            maze[i][0] = 1;
            maze[i][9] = 1;
        }
        Random random = new Random();
        int exit = random.nextInt(7) + 1;
        int entrance = random.nextInt(7) + 1;
        maze[entrance][0] = 0;
        maze[exit][9] = 0;
        for (int i = 2; i <= 7; i++) {
            for (int j = 0; j <= 1; j++) {
                maze[random.nextInt(8) + 1][i] = 1;
            }
            maze[i][random.nextInt(5) + 2] = 1;
        }
//        print a maze
        for (var i : maze) {
            for (var j : i) {
                System.out.print(j == 1 ? "\u2588\u2588" : "  ");
            }
            System.out.println();
        }
    }
}
