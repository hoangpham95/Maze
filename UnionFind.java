import java.util.*;

class UnionFind<T> {
    HashMap<T, Link<T>> elements = new HashMap<T, Link<T>>();
    /*
     * Insert a new element into the UnionFind structure that initially
     * is in its own partition. If the element already exists, this
     * function returns false. Otherwise, it returns true
     */
    public boolean add(T elem) {
        /*Check for null */
        if (elem == null) {
            throw new NullPointerException("UnionFind does not support null!");
        }
        /* Check whether this entry exists; fail if it does */
        if (elements.containsKey(elem)) {
            return false;
        }
        /* Otherwise add the element as its own parent */
        elements.put(elem, new Link<T>(elem));
        return true;
    }
    
    public T find(T elem) {
        /* Check whether the element exists; fail if it doesn't */
        if (!elements.containsKey(elem)) {
            throw new NoSuchElementException(elem + " is not an element.");
        }
        /* Otherwise, recursively search the structure and return the result */
        return this.recursiveFind(elem);
    }
    
    /* 
     * Given an element which is known to be in the structure, searches
     * for its representative element and returns it, applying path
     * compression at each step
     */
    private T recursiveFind(T elem) {
        /* Get the info on this object. */
        Link<T> info = this.elements.get(elem);
        /*
         * If the element is its own parent, it's the representative of its
         * class and we should say so
         */
        if (info.parent == elem) {
            return elem;
        }
        
        /* Otherwise, look up the parent of this element, then compress the 
         * path.
         */
        info.parent = this.recursiveFind(info.parent);
        return info.parent;
    }
    
    /*
     * Given two elements, unions together the sets containing those
     * elements. If either element is not contained in the set,
     * throws a NoSuchElementException
     */
    
    public void union(T one, T two) {
        /*
         * Get the link info for the parents. This also handles the
         * exception guarantee
         */
        Link<T> firstLink = this.elements.get(one);
        Link<T> secondLink = this.elements.get(two);
        
        /* IF these are the same object, we're done */
        if (firstLink == secondLink) {
            return;
        }
        
        /* Otherwise, link the two. We'll do a union based on the rank of two nodes,
         * where the parent with the lower rank will merge with the parent with higher
         * rank
         */
        if (firstLink.rank > secondLink.rank) {
            /*
             * Since each parent must link to itself, the value of firstLink.parent
             * is the representative of one
             */
            secondLink.parent = firstLink.parent;
        } else if (firstLink.rank < secondLink.rank) {
            firstLink.parent = secondLink.parent;
        } else {
            /* Arbitrarily wire one to be the parent of two */
            secondLink.parent = firstLink.parent;
            firstLink.rank += 1;
        }
    }
}

/* An utility class holding an object's parent and rank */
class Link<T> {
    public T parent;
    public int rank = 0;
    
    Link(T parent) {
        this.parent = parent;
    }

}