class Edge<T> {
    Node<T> first;
    Node<T> second;
    int weight;

    Edge(Node<T> first, Node<T> second, int weight) {
        /*
         * Check if the graph contains loop or not
         * i.e if you already have the edge AE, you cannot 
         * make the edge EA and vice versa
         */
        if (first.isConnected(second)) {
            throw new UnsupportedOperationException("This graph cannot contains loop! " 
        + first.pos.toString() + second.pos.toString());
        }
        
        /* Otherwise, just construct normally */
        this.first = first;
        this.second = second;
        this.weight = weight;
        
        this.first.edges.add(this); // this will be in the first node's edges
        this.second.edges.add(this); // this will be in the second node's edges

    }
    
    /* This will be more convenient when we're dealing with maze */
    Edge(Node<T> first, Node<T> second) {
        this(first, second, 1);
    }
    
    /* To check if the edge contain a given node */
    boolean containNode(Node<T> node) {
        return this.first == node
            || this.second == node;
    }
    
    Node<T> getOtherNode(Node<CartPt> n) {
    	Node<T> n1 = this.first;
    	Node<T> n2 = this.second;
    	
    	if (n == n1) {
    		return n2;
    	} else if (n == n2) {
    		return n1;
    	} else {
    		throw new IllegalArgumentException("No other node");
    	}
    }
}