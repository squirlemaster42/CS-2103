import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActorNode implements Node {

    private final String name;
    private final Set<MovieNode> neighbors;

    ActorNode(final String name){
        this.name = name;
        this.neighbors = new HashSet<>();
    }

    void addMovie(final MovieNode movie){
        neighbors.add(movie);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        return "ActorNode{" +
                "name='" + name + '\'' +
                ", neighbors=" + neighbors +
                '}';
    }
}
