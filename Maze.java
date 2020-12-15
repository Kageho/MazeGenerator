package maze;

import java.util.*;

public class Maze {
    private final int ROWS;
    private final int COLUMNS;
    private final boolean[][] gird;
    private Set<Edge> minSpanningTree;
    private Set<Node> nodes;
    private ArrayList<Edge> edges;
    Random random;

    public Maze(int rows, int columns) {
        this.COLUMNS = columns - 2;
        this.ROWS = rows - 2;
        gird = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(gird[i], false);
        }
    }

    public void createMaze() {
        if (!(ROWS <= 2 || COLUMNS <= 2)) {
            random = new Random();
            createNodes();
            createEdges();
            useAlgorithmOfPrimAndGetMST();
            fillGird();
            printGird();
        } else if (ROWS >= 1 && COLUMNS >= 1) {
            makeSmallMaze();
            printGird();
        } else {
            System.out.println("Your data is wrong. Can't make a maze");
        }
    }

    // this method will be used if input data is small
    private void makeSmallMaze() {
        for (int i = 0; i < COLUMNS + 2; i++) {
            gird[1][i] = true;
        }
        for (int i = 2; i < ROWS + 1; i += 2) {
            for (int j = 1; j < COLUMNS + 1; j += 2) {
                gird[i][j] = true;
            }
        }
    }

    // this monster fill my gird, by default gird is filled by false
    // cells which belong to MST will be represent as true
    private void fillGird() {
        int gate = -1;
        // it makes two gates
        for (int i = 0; i < 2; i++) {
            do {
                gate = random.nextInt(ROWS);
            } while (gate % 2 == 0 || gate >= ROWS);
            if (i == 0) {
                gird[gate][0] = true;
            } else {
                gird[gate][COLUMNS + 1] = true;
            }
        }


        // filling in the field with offsets
        for (var i : minSpanningTree) {
            gird[i.getFirstNode().getROW() + 1][i.getFirstNode().getCOLUMN() + 1] = true;
            gird[i.getSecondNode().getROW() + 1][i.getSecondNode().getCOLUMN() + 1] = true;
            boolean doRowsEqual = i.getFirstNode().getROW() == i.getSecondNode().getROW();
            if (doRowsEqual) {
                gird[1 + i.getFirstNode().getROW()][1 + (i.getFirstNode().getCOLUMN() + i.getSecondNode().getCOLUMN()) / 2] = true;
            } else {
                gird[1 + (i.getFirstNode().getROW() + i.getSecondNode().getROW()) / 2][1 + i.firstNode.getCOLUMN()] = true;
            }
        }
        // it helps to deal with even number of columns, The same situation is here, look below
        if (COLUMNS % 2 == 0) {
            gird[gate][COLUMNS] = true;
            for (int i = 0; i < ROWS ; i++) {
                do {
                    gate = random.nextInt(ROWS);
                } while (gate % 2 != 0 || gate >= ROWS || gate == 0);
                if (gird[gate][COLUMNS - 1]) {
                    gird[gate][COLUMNS] = true;
                }

            }
        }
        // it helps to deal with even number of rows: by filling a penultimate line with true
        // I made it because the penultimate line can't be included to the MST
        if (ROWS % 2 == 0) {
            for (int i = 0; i < COLUMNS; i++) {
                do {
                    gate = random.nextInt(COLUMNS);
                } while (gate % 2 != 0 || gate >= COLUMNS || gate == 0);
                if (gird[ROWS - 1][gate]) {
                    gird[ROWS][gate] = true;
                }
            }
        }
    }

    private void printGird() {
        for (var i : gird) {
            for (var j : i) {
                System.out.print(j ? "  " : "\u2588\u2588");
            }
            System.out.println();
        }
    }

    private void useAlgorithmOfPrimAndGetMST() {
        minSpanningTree = new HashSet<>();
        Edge currentEdge = null;
        // sorting edges by weight
        edges.sort(new WeightComparator());
        // it takes the first edge and then it will be added to minSpanningTree
        for (var i : edges) {
            if (i.getFirstNode().getCOLUMN() == 0 || i.getSecondNode().getCOLUMN() == 0) {
                currentEdge = i;
                break;
            }
        }
        if (currentEdge == null) {
            System.out.println("Error, there is no edges");
            return;
        }

        currentEdge.getFirstNode().setVisited(true);
        currentEdge.getSecondNode().setVisited(true);
        edges.remove(currentEdge);
        minSpanningTree.add(currentEdge);
        // this loop makes the MST, it will contain (nodes - 1) edges
        while (minSpanningTree.size() != nodes.size() - 1) {
            currentEdge = getEdgeWithMinWeight();
            edges.remove(currentEdge);
            currentEdge.getFirstNode().setVisited(true);
            currentEdge.getSecondNode().setVisited(true);
            minSpanningTree.add(currentEdge);
        }
    }

    // returns an edge with min weight which cannot create a loop
    private Edge getEdgeWithMinWeight() {
        int minWeight = Integer.MAX_VALUE;
        Edge edgeWithMin = null;
        for (var i : edges) {
            if (i.getWeight() < minWeight && (i.getFirstNode().isVisited() && !i.getSecondNode().isVisited() ||
                    !i.getFirstNode().isVisited() && i.getSecondNode().isVisited())) {
                minWeight = i.getWeight();
                edgeWithMin = i;
            }
        }
        return edgeWithMin;
    }

    // this method makes nodes, the vertices are staggered
    private void createNodes() {
        nodes = new HashSet<>();
        for (int i = 0; i < ROWS; i += 2) {
            for (int j = 0; j < COLUMNS; j += 2) {
                nodes.add(new Node(i, j));
            }
        }
    }

    // this method makes an edge from two nodes
    // it connect  first horizontal neighbors and then vertical
    private void createEdges() {
        edges = new ArrayList<>();
        int currentColumns = COLUMNS % 2 == 0 ? COLUMNS - 1 : COLUMNS;
        for (int i = 0; i < ROWS; i += 2) {
            for (int j = 1; j < currentColumns; j += 2) {
                edges.add(getAnEdgeFromTwoNodes(i, j));
            }
        }
        int currentRows = ROWS % 2 == 0 ? ROWS - 1 : ROWS;
        for (int j = 0; j < COLUMNS; j += 2) {
            for (int i = 1; i < currentRows; i += 2) {
                edges.add(getAnEdgeFromTwoNodes(i, j));
            }
        }
    }

    // it returns two edges, this method take a cell(wall) and then it finds vertices
// that are separated by this wall
    private Edge getAnEdgeFromTwoNodes(int row, int column) {
        boolean isRowEven = row % 2 == 0;
        Node[] twoNodes = new Node[2];
        int index = 0;
        for (var i : nodes) {
            if (isRowEven) {
                if ((i.getCOLUMN() == column - 1 || i.getCOLUMN() == column + 1) && i.getROW() == row) {
                    twoNodes[index++] = i;
                }
            } else {
                if ((i.getROW() == row - 1 || i.getROW() == row + 1) && i.getCOLUMN() == column) {
                    twoNodes[index++] = i;
                }
            }
        }
        return new Edge(twoNodes[0], twoNodes[1], random.nextInt(100) + 1);
    }
}
