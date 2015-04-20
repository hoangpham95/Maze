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
        background = background.overlayImages(this.drawEdgeArrayHelp(edges, 0, edges.size()));
        return background;
    }

    WorldImage drawGraphPath(WorldImage background,
            ArrayList<Edge<CartPt>> edges) {
        for (Edge<CartPt> e : edges) {
            background = background.overlayImages(this.drawPath(e));
        }
        return background;
    }
    
    private WorldImage drawEdgeArrayHelp(ArrayList<Edge<CartPt>> arr, int minIndex,
            int maxIndex) {
        if (maxIndex - minIndex == 1) {
            return this.drawEdge(arr.get(minIndex));
        }
        else {
            WorldImage image1 = this.drawEdgeArrayHelp(arr, minIndex,
                    (minIndex + maxIndex) / 2);
            WorldImage image2 = this.drawEdgeArrayHelp(arr,
                    (minIndex + maxIndex) / 2, maxIndex);
            return image1.overlayImages(image2);
        }
    }
    
    /*
     * Draw the node framed outside
     */
    private WorldImage drawNode(Node<CartPt> node) {
        /*
         * This node is original node. Need to translate to board's position
         */
        return new FrameImage(node.pos.toPixel(Maze.CELL_SIZE), Maze.CELL_SIZE,
                Maze.CELL_SIZE, new Color(0, 0, 0));
        /*
         * return new DiskImage(node.pos.toPixel(Maze.CELL_SIZE), 5, new
         * Color(0, 0, 0));
         */
    }
    
    WorldImage drawNodesInGraph(WorldImage bg, ArrayList<Node<CartPt>> nodes) {
        for (Node<CartPt> n : nodes) {
            bg = bg.overlayImages(this.drawNode(n));
        }
        return bg;
    }

    /*
     * Draw the edge between two node: if the (real) edge is vertical, draw the
     * (invisible) horizontal edge, and vice versa
     */
    private WorldImage drawEdge(Edge<CartPt> edge) {
        Node<CartPt> fst = edge.first;
        Node<CartPt> snd = edge.second;

        return new LineImage(
                fst.pos.findBiggerPoint(snd.pos).scale(Maze.CELL_SIZE),
                fst.pos.findPerpPoint(snd.pos).scale(Maze.CELL_SIZE),
                new Color(125, 125, 125));
    }
    
    WorldImage drawEdgesInGraph(WorldImage bg, ArrayList<Edge<CartPt>> edges) {
        for (Edge<CartPt> e : edges) {
            bg = bg.overlayImages(this.drawEdge(e));
        }
        return bg;
    }

    WorldImage drawPath(Edge<CartPt> edge) {
        Node<CartPt> fst = edge.first;
        Node<CartPt> snd = edge.second;
        return new LineImage(fst.pos.toPixel(Maze.CELL_SIZE),
                snd.pos.toPixel(Maze.CELL_SIZE), new Color(255, 0, 0));
    }

    /*
     * The instructor allowed us to use Collections in Java (check piazza)
     */
    <T> void shuffle(ArrayList<T> arr) {
        Collections.shuffle(arr);
    }

    ArrayList<ArrayList<Node<CartPt>>> generateGraphPosition(
            int horizontalDimension, int verticalDimension) {
        ArrayList<ArrayList<Node<CartPt>>> result = new ArrayList<ArrayList<Node<CartPt>>>();
        for (int i = 0; i < horizontalDimension; i += 1) {
            ArrayList<Node<CartPt>> arr = new ArrayList<Node<CartPt>>();
            for (int j = 0; j < verticalDimension; j += 1) {
                arr.add(new Node<CartPt>(new CartPt(i, j)));
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
            /*
             * There is some problem on the connect Maze need to revise
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
            for (int j = 0; j < a.size(); j += 1) {
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