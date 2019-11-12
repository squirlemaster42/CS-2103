import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MovieNode implements Node {

    private final String title;
    private final Set<ActorNode> neighbors;

    MovieNode(final String title){
        this.title = title;
        this.neighbors = new HashSet<>();
    }

    public void addActor(final ActorNode actor){
        neighbors.add(actor);
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public Collection<? extends Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        return "MovieNode{" +
                "title='" + title + '\'' +
                '}';
    }
}
