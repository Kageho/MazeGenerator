package maze;

import java.util.Objects;
/*
* This class represents a node*/
public class Node {
    private final int ROW;
    private final int COLUMN;
    private boolean isVisited;

    public Node(int row, int column){
        this.ROW = row;
        this.COLUMN = column;

    }

    public int getROW() {
        return ROW;
    }

    public int getCOLUMN() {
        return COLUMN;
    }
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isVisited() {
        return isVisited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return ROW == node.ROW && COLUMN == node.COLUMN;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ROW, COLUMN);
    }
}
