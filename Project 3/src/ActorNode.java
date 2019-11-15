import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActorNode implements Node {

    private final String name;
    private final Set<MovieNode> neighbors;

    //ask
    /**
     * An implementation of Node to store Actors
     * @param name Name of actor
     */
    ActorNode(final String name){
        this.name = name;
        this.neighbors = new HashSet<>();
    }

    /**
     * Adds movie to the actor node
     * @param movie movie to add
     */
    void addMovie(final MovieNode movie){
        neighbors.add(movie);
    }

    /**
     * Gets the name of the actor node
     * @return the name of the actor
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets neighbors of the actor node
     * @return a collection of the actor's neighbors
     */
    @Override
    public Collection<? extends Node> getNeighbors() {
        return neighbors;
    }

    /**
     * Places the actor name and neighbors into a printable string
     * @return String with the actors name and neighbors formatted nicely
     */
    @Override
    public String toString() {
        return "ActorNode{" +
                "name='" + name + '\'' +
                ", neighbors=" + neighbors +
                '}';
    }
}
