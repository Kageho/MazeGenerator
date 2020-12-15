package maze;

import java.util.Comparator;

public class WeightComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        Edge edge1 = (Edge) o1;
        Edge edge2 = (Edge) o2;
        return Integer.compare(edge1.getWeight(), edge2.getWeight());
    }
}
