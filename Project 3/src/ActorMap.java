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

    void addActor(final ActorNode actor){
        actorMap.put(actor.getName(), actor);
    }

    ActorNode getActor(final String key){
        return actorMap.get(key);
    }

    @Override
    public String toString() {
        return "ActorMap{" +
                "actorMap=" + actorMap +
                '}';
    }
}
