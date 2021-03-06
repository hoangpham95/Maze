import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

class Graph<T> {
    ArrayList<Node<T>> nodes;
    ArrayList<Edge<T>> edges;
    UnionFind<Node<T>> unionFind;
    Graph (ArrayList<Node<T>> nodes, ArrayList<Edge<T>> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.unionFind = new UnionFind<Node<T>>();
    }
    
    /* Constructor for empty graph, easier to operate at first */
    Graph() {
        this(new ArrayList<Node<T>>(), new ArrayList<Edge<T>>());
    }
    
    /*
     * Add a node to a graph
     * throw error if this node exists
     */
    void addNode(Node<T> node) {
        if (this.nodes.contains(node)) {
            throw new IllegalArgumentException("This node is already exists!");
        }
        else {
            this.nodes.add(node);
            for (Edge<T> e : node.edges) {
                this.edges.add(e);
            }
        }
    }
    
    /* 
     * Add an edge to the graph, guarantee that the two 
     * nodes are in the graph
     */
    void addEdge(Node<T> first, Node<T> second, int weight) {
        /* Only add the edge if the nodes exist in the graph */
        if (!this.nodes.contains(first) || !this.nodes.contains(second)) {
            throw new NoSuchElementException("Graph does not contain those nodes!");
        }
        else {
            Edge<T> e = new Edge<T>(first, second, weight);
            this.edges.add(e);
        }
    }
    
    /*
     * Find the node in the graph, given the data (T t)
     */
    Node<T> find(T t) {
        /* Since each node has a unique value t, only one value will be returned */
        for (Node<T> n : this.nodes) {
            if (n.pos == t) {
                return n;
            }
        }
        throw new NoSuchElementException("This graph doesn't contain this node!");
    }
    
    Graph<T> minimumSpanningTree() {
        /* The final result */
        Graph<T> result = new Graph<T>();
        
        /* Add all the nodes with no edges to the result 
         * (all the nodes are empty edge)
         */
        for (Node<T> n : this.nodes) {
            Node<T> newNode = new Node<T>(n.pos);
            result.nodes.add(newNode);
        }
        
        /* Building up a collection of all edges of the graph */
        ArrayList<Edge<T>> edges = this.edges;
        //Utils ut = new Utils();
        
        /* Now we need to sort the edges, in descending order */
        Collections.sort(edges, new EdgeComparator<T>());
        
        /*Set up a partition of nodes*/
        for (Node<T> node : this.nodes) {
            this.unionFind.add(node);
        }
        /*
         * Count how many edges we have already added
         */
        int numEdges = 0;
        
        /* 
         * Now, iterate over the edges, adding each edge if its endpoints aren't 
         * in the same partition
         */
        for (Edge<T> edge : this.edges) {
            /* If the endpoints are connected, skip the edge */
            /* Otherwise, add the edge */
            if (this.unionFind.find(edge.first) != this.unionFind.find(edge.second)) {
                Node<T> newNode1 = result.find(edge.first.pos);
                Node<T> newNode2 = result.find(edge.second.pos);
                result.addEdge(newNode1, newNode2, edge.weight);
                
                numEdges += 1; // increase the number of added edges
                
                /* Link the endpoints together */
                this.unionFind.union(edge.first, edge.second);
            }
            /* If we've added enough already, quit */
            if (numEdges + 1 == this.nodes.size()) {
                break;
            }
        }
    
        return result;
    }
    
    boolean noEmptyEdge() {
        ArrayList<Node<T>> nodes = this.nodes;
        for (Node<T> n : nodes) {
            if (n.edges.size() == 0)
                //System.out.println(n.toString());
                return false;
        }
        return true;
    }
    // Just for testing, delete afterwards
    void printNode() {
        for (Node<T> n : this.nodes) {
            System.out.println(n.pos.toString() + " - " + n.edges.size());
        }
    }
    
    void printEdge() {
        for (Edge<T> n : this.edges) {
            System.out.println(n.first.pos.toString() + n.second.pos.toString() + " " + n.weight);
        }
    }
}