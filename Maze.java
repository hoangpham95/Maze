import java.awt.Color;
import javalib.impworld.World;
import javalib.worldimages.*;
import java.util.*;

class Maze extends World{
	/* GLOBAL VARIABLE */
	public static final int CELL_SIZE = 12;
	public static final Color CELL_COLOR = new Color(192, 192, 192);
	public static final Color EDGE_COLOR = new Color(0, 0, 0);
	public static final int WIDTH = 100;
	public static final int HEIGHT = 60;
	public static Color PLAYER_COLOR = CELL_COLOR;
	public static Color DESTINATION_COLOR = CELL_COLOR;
	//end of global variable
	
    Graph<CartPt> board;
    Node<CartPt> player;
    Node<CartPt> destination;
    ArrayList<Node<CartPt>> visited;
    Utils ut = new Utils();
    
    /* For solution searching */
    HashMap<CartPt, Edge<CartPt>> cameFromEdge;
    
    
    Maze(Graph<CartPt> board) {
        this.board = board;
        this.player = this.board.nodes.get(0);
        int last = this.board.nodes.size() - 1;
        this.destination = this.board.nodes.get(last);
        this.visited = new ArrayList<Node<CartPt>>();
    }

    public WorldImage makeImage() {
        WorldImage background = new RectangleImage(new Posn(CELL_SIZE * WIDTH / 2, CELL_SIZE * HEIGHT / 2), 
        		CELL_SIZE * WIDTH, CELL_SIZE * HEIGHT, new Color(125, 125, 125));
        Utils ut = new Utils();
        WorldImage bg = ut.drawGraph(background, this.board, CELL_COLOR);
        bg = bg.overlayImages(this.player.pos.draw(PLAYER_COLOR));
        bg = bg.overlayImages(this.destination.pos.draw(DESTINATION_COLOR));
        return bg;
    }
    
    public void onKeyEvent(String ke) {
        if (ke.equals("m")) {
            this.board = this.board.minimumSpanningTree();
            this.player = this.board.nodes.get(0);
            int last = this.board.nodes.size() - 1;
            this.destination = this.board.nodes.get(last);
            this.PLAYER_COLOR = new Color(120, 120, 255);
            this.DESTINATION_COLOR = new Color(255, 160, 160);
        }
        if (ke.equals("up") || ke.equals("down") || ke.equals("left") || ke.equals("right")) {
        	if(ut.getNeighbor(this.player, ke) != this.player) {
        		this.player = ut.getNeighbor(this.player, ke);
        	}
        }
    }
}