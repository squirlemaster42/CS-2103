import java.util.*;

public class GraphSearchEngineImpl implements GraphSearchEngine {

    @Override
    public List<Node> findShortestPath(Node s, Node t) {
        final Queue<TrackedNode> queue = new PriorityQueue<>();
        final Set<TrackedNode> discovered = new HashSet<>();
        discovered.add(new TrackedNode(s, null));
        queue.add(new TrackedNode(s, null));
        while(!queue.isEmpty()){
            TrackedNode curr = queue.poll();
            if(curr.node == t){
                return getPathTo(curr);
            }
            final Collection<TrackedNode> notDiscovered = notDiscovered(discovered, curr);
            queue.addAll(notDiscovered);
            discovered.addAll(notDiscovered);
        }

        return null;
    }

    private List<Node> getPathTo(final TrackedNode goal){
        final List<Node> path = new ArrayList<>();
        TrackedNode curr = goal;
        //TODO Check for off by one
        while (curr.parent != null){
            path.add(curr.node);
            curr = curr.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private Collection<TrackedNode> notDiscovered(final Collection<TrackedNode> discovered, final TrackedNode curr){
        Set<TrackedNode> toAdd = new HashSet<>();
        curr.node.getNeighbors().forEach(e ->{
            //TODO This is a problem
            //Need to make Queue and Discovered set act differently
            if(!discovered.contains(e)){
                toAdd.add(new TrackedNode(e, curr));
            }
        });
        return toAdd;
    }

    private static class TrackedNode{
        private final Node node;
        private final TrackedNode parent;

        private TrackedNode(final Node node, final TrackedNode parent){
            this.node = node;
            this.parent = parent;
        }
    }
}
