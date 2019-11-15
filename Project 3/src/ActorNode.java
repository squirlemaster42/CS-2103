import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActorNode implements Node {

    private final String name;
    private final Set<MovieNode> neighbors;

    /**
     * Constructs an ActorNode
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
     * Creates a String representation of the ActorNode
     * @return A String representation of the current instance of ActorNode
     */
    @Override
    public String toString() {
        return "ActorNode{" +
                "name='" + name + '\'' +
                ", neighbors=" + neighbors +
                '}';
    }
}
