package maze;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        Maze maze = new Maze(rows, columns);
        maze.createMaze();
    }
}
