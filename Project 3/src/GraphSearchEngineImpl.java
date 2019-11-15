import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class GraphSearchEngineImpl implements GraphSearchEngine {

    /**
     * Finds the shortest path between two given nodes
     * @param s the start node.
     * @param t the target node.
     * @return A list containing the shortest path between the two nodes
     */
    @Override
    public List<Node> findShortestPath(Node s, Node t) {
        final Queue<TrackedNode> queue = new LinkedBlockingDeque<>(); //The Queue of nodes to check
        final Set<TrackedNode> discovered = new HashSet<>(); //The list of discovered nodes
        discovered.add(new TrackedNode(s, null)); //Sets the neighbors of start to discovered
        queue.add(new TrackedNode(s, null)); //Adds the neighbors of start to the queue
        while(!queue.isEmpty()){ //Loops through the queue while we are still checking for nodes
            TrackedNode curr = queue.poll(); //The current node being operated on
            if(curr.node == t){ //If we have reached the target node
                final ArrayList<Node> path = new ArrayList<>();
                path.add(s); //Adds the starting node to the path
                path.addAll(getPathTo(curr)); //Adds the rest of the path
                return path;
            }
            //Finds the neighbors of curr that have not been discovered yet
            final Collection<TrackedNode> notDiscovered = notDiscovered(discovered, curr);
            queue.addAll(notDiscovered); //Adds the node that have not been discovered to the queue
            discovered.addAll(notDiscovered); //Adds the nodes that have not been discovered to discovered
        }
        return null; //We did not find a path
    }

    /**
     * Backtracks from goal to find the path to it
     * @param goal End node to  find a path to
     * @return A list containing a path to the goal node
     */
    private List<Node> getPathTo(final TrackedNode goal){
        final List<Node> path = new ArrayList<>(); //The path that we found
        TrackedNode curr = goal; //The current node
        while (curr.parent != null){ //Loops until we hit the starting node
            path.add(curr.node); //Adds the node to the path
            curr = curr.parent; //Updates the current node the the parent
        }
        Collections.reverse(path); //Makes sure the path is in the correct direction
        return path;
    }

    /**
     * Takes a node and determines all of its adjacent nodes that have not been discovered yet
     * @param discovered A collection of the nodes that have been discovered
     * @param curr The node whose neighbors are to be added
     * @return A collection of nodes that only contains nodes that have not been discovered yet
     */
    private Collection<TrackedNode> notDiscovered(final Collection<TrackedNode> discovered, final TrackedNode curr){
        Set<TrackedNode> uniqueNodes = new HashSet<>(); //The collection of unique nodes
        curr.node.getNeighbors().forEach(e ->{ //Loops through each node
            final TrackedNode toAdd =  new TrackedNode(e, curr); //The node to add
            if(!discovered.contains(toAdd)){ //Checks if the node has already been discovered
                uniqueNodes.add(toAdd); //Adds the node if it is has not been found yet
            }
        });
        return uniqueNodes;
    }

    /**
     * A class used to store a node and the path used to get to that node
     */
    private static class TrackedNode{
        private final Node node;
        private final TrackedNode parent;

        /**
         * Creates a TrackedNode
         * @param node The node
         * @param parent The Node that points to node
         */
        private TrackedNode(final Node node, final TrackedNode parent){
            this.node = node;
            this.parent = parent;
        }

        /**
         * Returns true if two nodes are equal
         * Two Nodes will be equal if the field node in both Nodes are equal
         * @param o The object to compare
         * @return True if the objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TrackedNode that = (TrackedNode) o;
            return Objects.equals(node, that.node);
        }

        /**
         * Hashes the object
         * The hash is based only on the field node
         * @return A hash of the object
         */
        @Override
        public int hashCode() {
            return Objects.hash(node);
        }
    }
}
