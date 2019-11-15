import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MovieNode implements Node {

    private final String title;
    private final Set<ActorNode> neighbors;

    /**
     * An implementation of Node to store Movies
     * @param title Title of the movie
     */
    MovieNode(final String title){
        this.title = title;
        this.neighbors = new HashSet<>();
    }

    /**
     * Adds an actor to the movie node
     * @param actor actor to add
     */
    public void addActor(final ActorNode actor){
        neighbors.add(actor);
    }

    /**
     * Gets the name of the movie node
     * @return the name of the movie node
     */
    @Override
    public String getName() {
        return title;
    }

    /**
     * Gets the neighbors of the movie node
     * @return a collection of the movie's neighbors
     */
    @Override
    public Collection<? extends Node> getNeighbors() {
        return neighbors;
    }

    /**
     * Places the movie name and neighbors into a printable string
     * @return String with the movie name and neighbors formatted nicely
     */
    @Override
    public String toString() {
        return "MovieNode{" +
                "title='" + title + '\'' +
                '}';
    }
}
