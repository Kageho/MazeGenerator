package maze;

import java.util.Arrays;

public class MazeSolver {
    private boolean[][] maze;
    private boolean[][] solution;
    private final int ROWS;
    private final int COLUMNS;

    MazeSolver(boolean[][] maze) {
        this.maze = maze;
        ROWS = maze.length;
        COLUMNS = maze[0].length;
        solution = new boolean[ROWS][COLUMNS];
        for (var i : solution) {
            Arrays.fill(i, false);
        }
    }

    void solveMaze() {
        int entrance = -1;
        for (int i = 0; i < ROWS; i++) {
            if (maze[i][0]) {
                entrance = i;
                i = ROWS;
            }
        }
        // my entrance
        solveMaze(entrance, 0);
        printSolution();
    }
    // this method uses recursive backtracking
    private boolean solveMaze(int r, int c) {
        // it is the exit
        if (c == COLUMNS - 1 && r >= 0 && r < ROWS && maze[r][c]) {
            return solution[r][c] = true;
        }
        // if r and c are valid then a "rat" moves inside the maze
        if (r >= 0 && c >= 0 && r < ROWS && c < COLUMNS && !solution[r][c] && maze[r][c]) {
            solution[r][c] = true;
            // going down
            if (solveMaze(r + 1, c)) {
                return true;
            }
            // going right
            if (solveMaze(r, c + 1)) {
                return true;
            }
            // going up
            if (solveMaze(r - 1, c)) {
                return true;
            }
            // going left
            if (solveMaze(r, c - 1)) {
                return true;
            }
            // if we are here, then the sell is dead end(backtracking)
            solution[r][c] = false;
        }
        return false;
    }

    private void printSolution() {
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[0].length; j++){
                if(maze[i][j] && solution[i][j]){
                    System.out.print("//");
                } else if(maze[i][j]){
                    System.out.print("  ");
                } else {
                    System.out.print("\u2588\u2588");
                }
            }
            System.out.println();
        }
    }
}
