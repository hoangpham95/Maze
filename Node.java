import java.util.ArrayList;

class Node<T> {
    T pos;
    ArrayList<Edge<T>> edges;
    
    Node (T pos, ArrayList<Edge<T>> edges) {
        this.pos = pos;
        this.edges = edges;
    }
    
    Node (T pos) {
        this(pos, new ArrayList<Edge<T>>());
    }
    
    boolean isConnected(Node<T> other) {
        ArrayList<Edge<T>> edges = this.edges;
        for (Edge<T> e : edges) {
            if (e.containNode(other))
                return true;
        }
        return false;
    }
}