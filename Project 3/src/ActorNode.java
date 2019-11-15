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
     * 
     * @return
     */
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
