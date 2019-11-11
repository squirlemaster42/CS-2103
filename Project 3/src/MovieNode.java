import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieNode implements Node {

    private final String title;
    private final List<ActorNode> neighbors;

    MovieNode(final String title, final List<ActorNode> neighbors){
        this.title = title;
        this.neighbors = neighbors;
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
