import java.awt.Color;
import java.util.ArrayList;

import javalib.impworld.World;
import javalib.worldimages.*;

class Maze extends World{
    Graph<CartPt> board;
    ArrayList<Edge<CartPt>> path;
    
    static final int CELL_SIZE = 16;
    CartPt player;
    CartPt destination;
    Utils ut = new Utils();
    
    Maze(Graph<CartPt> board) {
        this.board = board;
        this.path = new ArrayList<Edge<CartPt>>();
    }

    public WorldImage makeImage() {
        WorldImage background = new RectangleImage(new Posn(410, 410), 820, 820, new Color(255, 255, 255));
        Utils ut = new Utils();
        WorldImage bg = ut.drawGraph(background, this.board);
        return ut.drawGraphPath(bg, this.path);
    }
    
    public void onKeyEvent(String ke) {
        if (ke.equals("m")) {
            this.board = this.board.minimumSpanningTree();
        }
        if (ke.equals("p")) {
            /* Add the path in to the maze */
            this.path = this.board.edges;
        }
    }
    
    public void onTick() {
        
    }
}