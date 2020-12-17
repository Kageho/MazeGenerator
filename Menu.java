package maze;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    private boolean isThereAnyMaze;
    private final Scanner scanner;
    private boolean[][] currentMaze;

    public Menu() {
        this.isThereAnyMaze = false;
        scanner = new Scanner(System.in);
        currentMaze = new boolean[0][0];
    }

    public void run() {
        Pattern pattern0_4 = Pattern.compile("[0-5]");
        Pattern pattern0_2 = Pattern.compile("[0-2]");
        Matcher matcher0_4;
        Matcher matcher0_2;
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            switch (command) {
                case "1":
                    //generate a new maze
                    generateANewMaze();
                    printMaze();
                    break;
                case "2":
                    // load a maze
                    loadMaze();
                    break;
                case "0":
                    System.out.println("Bye!");
                    return;
            }
            if (isThereAnyMaze) {
                switch (command) {
                    case "3":
                        // save the maze
                        saveAMaze();
                        break;
                    case "4":
                        //display the maze
                        printMaze();
                        break;
                    case "5":
                        MazeSolver mazeSolver = new MazeSolver(currentMaze);
                        mazeSolver.solveMaze();
                }
            }
            matcher0_2 = pattern0_2.matcher(command);
            matcher0_4 = pattern0_4.matcher(command);
            if (!(matcher0_2.matches() || isThereAnyMaze && matcher0_4.matches())) {
                System.out.println("Incorrect option. Please try again");
            }
            System.out.println();
        }

    }

    private void saveAMaze() {
        String pathToFile = scanner.nextLine().trim();
        try (PrintWriter printWriter = new PrintWriter(pathToFile)) {
            printWriter.println(currentMaze.length + "," + currentMaze[0].length);
            StringBuilder row = new StringBuilder();
            for (var i : currentMaze) {
                for (var j : i) {
                    row.append(j ? "1," : "0,");
                }
                row.deleteCharAt(row.length() - 1);
                printWriter.println(row.toString());
                row.delete(0, row.length());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    private void loadMaze() {
        String pathToFile = scanner.nextLine().trim();
        try (Scanner fileReader = new Scanner(new File(pathToFile))) {
            int rows = -1;
            int columns = -1;
            if (fileReader.hasNextLine()) {
                String[] rowsAndColumns = fileReader.nextLine().split(",");
                if (rowsAndColumns.length != 2) {
                    System.out.println("Invalid format. There is no rows or columns");
                }
                rows = Integer.parseInt(rowsAndColumns[0]);
                columns = Integer.parseInt(rowsAndColumns[1]);
            }
            currentMaze = new boolean[rows][columns];
            int currentRow = 0;
            while (fileReader.hasNextLine()) {
                String arguments = fileReader.nextLine();
                int currentColumn = 0;
                for (var i : arguments.split(",")) {
                    currentMaze[currentRow][currentColumn++] = Integer.parseInt(i) == 1;
                }
                currentRow++;
            }
            isThereAnyMaze = true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid format. More rows and columns than required");
        }
    }
    // method generates a new maze, it takes two numbers
    // if there are two numbers, then the first number will be rows
    // and the second one will be columns
    // if a user passes only one number, the number of rows will be equal
    // with number of columns
    private void generateANewMaze() {
        System.out.println("Enter the size of a new maze");
        String args = scanner.nextLine().trim();
        if (!args.matches("\\d+ +\\d+|\\d+")) {
            System.out.println("The arguments are invalid. Can't make a maze");
            return;
        }
        String[] rowsAndColumns = args.split("\\s+");
        int rows;
        int columns;
        if (args.matches("\\d+")) {
            rows = Integer.parseInt(args);
            columns = rows;
        } else if (args.matches("\\d+ +\\d+")) {
            rows = Integer.parseInt(rowsAndColumns[0]);
            columns = Integer.parseInt(rowsAndColumns[1]);
        } else {
            System.out.println("You data is invalid");
            return;
        }
        Maze maze = new Maze(rows, columns);
        currentMaze = maze.createAndGetMaze();
        if (currentMaze.length != 0) {
            isThereAnyMaze = true;
        }
    }

    private void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Generate a new maze\n2. Load a maze");
        if (isThereAnyMaze) {
            System.out.println("3. Save the maze\n4. Display the maze\n5. Find the escape");
        }
        System.out.println("0. Exit");
    }

    private void printMaze() {
        if (currentMaze.length == 0) {
            System.out.println("There is no current maze");
            return;
        }
        for (var i : currentMaze) {
            for (var j : i) {
                System.out.print(j ? "  " : "\u2588\u2588");
            }
            System.out.println();
        }
    }
}
