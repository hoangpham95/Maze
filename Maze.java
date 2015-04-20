import java.awt.Color;
import java.util.ArrayList;

import javalib.impworld.World;
import javalib.worldimages.*;

class Maze extends World{
    Graph<CartPt> board;
    ArrayList<Edge<CartPt>> path;
    
    static final int CELL_SIZE = 15;
    Node<CartPt> player;
    Node<CartPt> destination;
    Utils ut = new Utils();
    
    Maze(Graph<CartPt> board) {
        this.board = board;
        this.player = this.board.nodes.get(0);
        int last = this.board.nodes.size() - 1;
        this.destination = this.board.nodes.get(last);
        this.path = new ArrayList<Edge<CartPt>>();
    }

    public WorldImage makeImage() {
        WorldImage background = new RectangleImage(new Posn(750, 450), 1501, 901, new Color(125, 125, 125));
        Utils ut = new Utils();
        WorldImage bg = ut.drawGraph(background, this.board);
        bg = bg.overlayImages(this.player.pos.draw(new Color(0, 0, 255)));
        bg = bg.overlayImages(this.destination.pos.draw(new Color(255, 150, 150)));
        return bg;
    }
    
    public void onKeyEvent(String ke) {
        if (ke.equals("m")) {
            this.board = this.board.minimumSpanningTree();
        }
        if (ke.equals("e")) {
            WorldImage bg = this.makeImage();
            bg = ut.drawGraphPath(bg, this.path);
        }
    }
}