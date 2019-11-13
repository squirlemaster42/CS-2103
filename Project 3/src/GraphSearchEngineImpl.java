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
            //TODO Might not need to do this because sets will not allow for things to be added twice
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
        Set<TrackedNode> uniqueNodes = new HashSet<>();
        curr.node.getNeighbors().forEach(e ->{
            final TrackedNode toAdd =  new TrackedNode(e, curr);
            //TODO this version of contains will not work because the parents will not be equal if it has already been added
            //TODO need to figure out how to make it so contains will only check the node field and not the parent field of TrackedNode
            //TODO the overridden equals might fix it but might not
            if(!discovered.contains(toAdd)){
                uniqueNodes.add(toAdd);
            }
        });
        return uniqueNodes;
    }

    private static class TrackedNode{
        private final Node node;
        private final TrackedNode parent;

        private TrackedNode(final Node node, final TrackedNode parent){
            this.node = node;
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TrackedNode that = (TrackedNode) o;
            return Objects.equals(node, that.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node);
        }
    }
}
