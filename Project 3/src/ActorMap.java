import java.util.HashMap;
import java.util.Map;

public class ActorMap {

    private static ActorMap instance;

    static ActorMap getInstance(){
        if(instance == null){
            instance = new ActorMap();
        }
        return instance;
    }

    private final Map<String, ActorNode> actorMap;

    private ActorMap(){
        actorMap = new HashMap<>();
    }

    /**
     * Adds actor to the actor
     * @param actor actor to add
     */
    void addActor(final ActorNode actor){
        actorMap.put(actor.getName(), actor);
    }

    /**
     * Gets the actor node at the specified key
     * @param key key of the actor node to get
     * @return the actor node at the specified key
     */
    ActorNode getActor(final String key){
        return actorMap.get(key);
    }

    /**
     *
     * @param lastActor
     */
    void removeActorWithNoMovies(ActorNode lastActor) {
        if(lastActor.getNeighbors().size() == 0){
            actorMap.remove(lastActor.getName(), lastActor);
        }
    }

    /**
     * Gets the size of the actor map
     * @return the size of the actor map
     */
    int size() {
        return actorMap.size();
    }

    /**
     * Gets the actor map
     * @return the actor map
     */
    Map<String, ActorNode> getActorMap(){
        return actorMap;
    }

    /**
     * Stores the actor map into a string
     * @return a string with the actor map
     */
    @Override
    public String toString() {
        return "ActorMap{" +
                "actorMap=" + actorMap +
                '}';
    }
}
