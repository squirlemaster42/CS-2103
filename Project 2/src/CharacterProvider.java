import java.util.HashMap;
import java.util.Map;

public class CharacterProvider implements DataProvider<Integer, Character> {

    private final Map<Integer, Character> _map;
    private int retrieves = 0;

    /**
     * An implementation of DataProvider that stores Characters
     */
    public CharacterProvider() {
        _map = new HashMap<>();
    }

    /**
     * Adds data to the provider
     * @param key the key of the data
     * @param data the Character to be stored
     */
    public void addData(Integer key, Character data){
        _map.put(key,data);
    }

    /**
     * Gets the Character at the specified key
     * @param key the key
     * @return
     */
    @Override
    public Character get(Integer key) {
        retrieves++;
        return _map.get(key);
    }

    /**
     * Gets the number of retrieves
     * @return the number of retrieves
     */

    public int getRetrieves(){
        return retrieves;
    }
}
