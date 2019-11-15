import java.util.HashMap;
import java.util.Map;

public class ActorMap {

    //Start of singleton declaration
    //This is used to make ActorMap a singleton.
    //This means that only one instance can ever be created.
    private static ActorMap instance;

    /**
     * Return an instance of ActorMap
     * @return an ActorMap
     */
    static ActorMap getInstance(){
        if(instance == null){
            instance = new ActorMap();
        }
        return instance;
    }
    //End singleton declaration

    private final Map<String, ActorNode> actorMap;

    /**
     * Creates an ActorMap
     */
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
            //Removed the actor if it has no neighbors
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
     * Creates a String representation of the actor map
     * @return a String with the actor map
     */
    @Override
    public String toString() {
        return "ActorMap{" +
                "actorMap=" + actorMap +
                '}';
    }
}
