import java.awt.Color;

import javalib.impworld.World;
import javalib.worldimages.*;

class Maze extends World{
    Graph<CartPt> board;
    static final int CELL_SIZE = 50;
    CartPt player;
    CartPt destination;
    
    Maze(Graph<CartPt> board) {
        this.board = board;
    }

    public WorldImage makeImage() {
        WorldImage background = new RectangleImage(new Posn(50, 50), 100, 100, new Color(255, 255, 255));
        Utils ut = new Utils();
        return ut.drawGraph(background, this.board.minimumSpanningTree());
    }
}