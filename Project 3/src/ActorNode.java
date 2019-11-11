import java.util.Collection;
import java.util.List;

public class ActorNode implements Node {

    private final String name;
    private final List<MovieNode> neighbors;

    ActorNode(final String name, final List<MovieNode> neighbors){
        this.name = name;
        this.neighbors = neighbors;
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
}
