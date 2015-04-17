import tester.*;
import java.util.*;

class ExampleGraph {
    ArrayList<Node<String>> nodes = new ArrayList<Node<String>>();
    ArrayList<Edge<String>> edges = new ArrayList<Edge<String>>();
    
    Node<String> A = new Node<String>("A");
    Node<String> B = new Node<String>("B");
    Node<String> C = new Node<String>("C");
    Node<String> D = new Node<String>("D");
    Node<String> E = new Node<String>("E");
    
    Edge<String> AE = new Edge<String>(A, E, 1);
    Edge<String> AB = new Edge<String>(A, B, 3);
    Edge<String> BE = new Edge<String>(B, E, 4);
    Edge<String> BC = new Edge<String>(B, C, 5);
    Edge<String> CE = new Edge<String>(C, E, 6);
    Edge<String> CD = new Edge<String>(C, D, 2);
    Edge<String> ED = new Edge<String>(E, D, 7);
    
    
    Node<CartPt> origin;
    Node<CartPt> origin2;
    CartPt ori;
    
    //Edge<String> DE = new Edge<String>(D, E, 7);
    Utils ut = new Utils();
    
    Graph<String> gr = new Graph<String>(nodes, edges);
    Graph<String> newGr;
    Maze maze1;
    Graph<CartPt> board1;
    
    Maze maze2;
    Graph<CartPt> board2;
    
    void initialize() {
        nodes.add(A);
        nodes.add(B);
        nodes.add(C);
        nodes.add(D);
        nodes.add(E);
        
        edges.add(AE);
        edges.add(AB);
        edges.add(BE);
        edges.add(BC);
        edges.add(CE);
        edges.add(CD);
        edges.add(ED);
        newGr = gr.minimumSpanningTree();
        
        board1 = new Graph<CartPt>();
        ArrayList<ArrayList<Node<CartPt>>> graphNodes = ut.generateGraphPosition(4, 4);
        board1.nodes = ut.toArrayList(graphNodes);
        board1.edges = ut.connectMaze(graphNodes);
        
        maze1 = new Maze(board1);
        
        board2 = new Graph<CartPt>();
        origin = new Node<CartPt>(new CartPt(1, 1));
        origin2 = new Node<CartPt>(new CartPt(0, 1));
        board2.nodes.add(origin);
        board2.nodes.add(origin2);
        maze2 = new Maze(board2);
        
        ori = new CartPt(0, 0);
    }
    
    void testNode(Tester t) {
        this.initialize();
        t.checkExpect(AE.containNode(A), true);
        t.checkExpect(AE.containNode(E), true);
    }
    
    void testSort(Tester t) {
        Utils ut = new Utils();
        ut.sort(edges);
        t.checkExpect(edges.get(0), AE);
        t.checkExpect(edges.get(1), CD);
        t.checkExpect(edges.get(2), AB);
        t.checkExpect(edges.get(3), BE);
        t.checkExpect(edges.get(4), BC);
        t.checkExpect(edges.get(5), CE);
        t.checkExpect(edges.get(6), ED);
    }
    
    void testPrint(Tester t) {
        newGr.printEdge();
        t.checkExpect(newGr.edges.size(), 4);
    }
    
    void testDraw(Tester t) {
        this.initialize();
        t.checkExpect(board1.edges.size(), 12);
        t.checkExpect(board1.nodes.size(), 9);
        maze1.bigBang(500, 500, 0.5);
    }
}