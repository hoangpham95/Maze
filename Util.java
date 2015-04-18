import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javalib.worldimages.*;

class Utils {

    /*
     * These two functions below are only for the Maze they checked if the edge
     * are vertical/horizontal
     */

    boolean horizontal(Edge<CartPt> edge) {
        return edge.first.pos.y == edge.second.pos.y;
    }

    boolean vertical(Edge<CartPt> edge) {
        return !this.horizontal(edge);
    }

    /*
     * Draw the graph on the given background
     */
    WorldImage drawGraph(WorldImage background, Graph<CartPt> board) {
        ArrayList<Node<CartPt>> nodes = board.nodes;
        ArrayList<Edge<CartPt>> edges = board.edges;
        for (Node<CartPt> node : nodes) {
            background = background.overlayImages(this.drawNode(node));
        }
        for (Edge<CartPt> edge : edges) {
            background = background.overlayImages(this.drawEdge(edge));
        }
        return background;
    }
    
    WorldImage drawGraphPath(WorldImage background, ArrayList<Edge<CartPt>> edges) {
        for (Edge<CartPt> e : edges) {
            background = background.overlayImages(this.drawPath(e));
        }
        return background;
    }

    /*
     * Draw the node framed outside
     */
    WorldImage drawNode(Node<CartPt> node) {
        /*
         * This node is original node. Need to translate to board's position
         */
        return new FrameImage(node.pos.toPixel(Maze.CELL_SIZE),
                Maze.CELL_SIZE, Maze.CELL_SIZE, new Color(0, 0, 0));
        /*return new DiskImage(node.pos.toPixel(Maze.CELL_SIZE),
               5, new Color(0, 0, 0));*/
    }

    /*
     * Draw the edge between two node: if the (real) edge is vertical, draw the
     * (invisible) horizontal edge, and vice versa
     */
    WorldImage drawEdge(Edge<CartPt> edge) {
        Node<CartPt> fst = edge.first;
        Node<CartPt> snd = edge.second;
        CartPt middle = fst.pos.toPixel(Maze.CELL_SIZE).midPoint(snd.pos.toPixel(Maze.CELL_SIZE));

        if (this.horizontal(edge)) {
            CartPt startPoint1 = new CartPt(middle.x, middle.y - Maze.CELL_SIZE
                    / 2);
            CartPt startPoint2 = new CartPt(middle.x, middle.y + Maze.CELL_SIZE
                    / 2);

            return new LineImage(startPoint1, startPoint2, new Color(255, 255,
                    255));
        } else {
            CartPt startPoint1 = new CartPt(middle.x - Maze.CELL_SIZE / 2,
                    middle.y);
            CartPt startPoint2 = new CartPt(middle.x + Maze.CELL_SIZE / 2,
                    middle.y);
            return new LineImage(startPoint1, startPoint2, new Color(255, 255,
                    255));
        }
    }
    
    WorldImage drawPath(Edge<CartPt> edge) {
        Node<CartPt> fst = edge.first;
        Node<CartPt> snd = edge.second;
        return new LineImage(fst.pos.toPixel(Maze.CELL_SIZE), snd.pos.toPixel(Maze.CELL_SIZE), new Color(255, 0, 0));
    }
    /*
     * The instructor allowed us to use
     * Collections in Java (check piazza)
     */
    <T> void shuffle(ArrayList<T> arr) {
        Collections.shuffle(arr);
    }

    ArrayList<ArrayList<Node<CartPt>>> generateGraphPosition(
            int horizontalDimension, int verticalDimension) {
        ArrayList<ArrayList<Node<CartPt>>> result = new ArrayList<ArrayList<Node<CartPt>>>();
        for (int i = 0; i < verticalDimension; i += 1) {
            ArrayList<Node<CartPt>> arr = new ArrayList<Node<CartPt>>();
            for (int j = 0; j < horizontalDimension; j += 1) {
                arr.add(new Node<CartPt>(new CartPt(i + 1, j + 1)));
            }
            result.add(arr);
        }
        return result;
    }

    ArrayList<Edge<CartPt>> connectMaze(ArrayList<ArrayList<Node<CartPt>>> nodes) {
        ArrayList<Edge<CartPt>> result = new ArrayList<Edge<CartPt>>();
        for (int i = 0; i < nodes.size() - 1; i += 1) {
            ArrayList<Node<CartPt>> x = nodes.get(i);
            ArrayList<Node<CartPt>> y = nodes.get(i + 1);
            for (int j = 0; j < x.size() - 1; j += 1) {
                Node<CartPt> n1 = x.get(j);
                Node<CartPt> n2 = x.get(j + 1);
                Node<CartPt> n3 = y.get(j);
                result.add(new Edge<CartPt>(n1, n2));
                result.add(new Edge<CartPt>(n1, n3));
            }
            /* There is some problem on the connect Maze
             * need to revise
             */
            Node<CartPt> lastmember = x.get(x.size() - 1);
            Node<CartPt> lastmember2 = y.get(y.size() - 1);
            result.add(new Edge<CartPt>(lastmember, lastmember2));
        }
        ArrayList<Node<CartPt>> lastrow = nodes.get(nodes.size() - 1);
        for (int i = 0; i < lastrow.size() - 1; i += 1) {
            Node<CartPt> n1 = lastrow.get(i);
            Node<CartPt> n2 = lastrow.get(i + 1);
            result.add(new Edge<CartPt>(n1, n2));
        }

        return result;
    }

    <T> ArrayList<T> toArrayList(ArrayList<ArrayList<T>> arr) {
        ArrayList<T> result = new ArrayList<T>();
        for (int i = 0; i < arr.size(); i += 1) {
            ArrayList<T> a = arr.get(i);
            for (int j = 0; j < arr.size(); j += 1) {
                result.add(a.get(j));
            }
        }
        return result;
    }
}

/* A comparator to sort edges according to its weight */
class EdgeComparator<T> implements Comparator<Edge<T>> {
    public int compare(Edge<T> o1, Edge<T> o2) {
        return o1.weight - o2.weight;
    }
}