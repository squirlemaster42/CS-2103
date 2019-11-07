import java.util.HashMap;
import java.util.Map;

public class StringProvider implements DataProvider<Integer, String> {

    private final Map<Integer, String> _map;
    private  int retrieves = 0;

    /**
     *  An implementation of DataProvider that stores Strings
     */
    public StringProvider(){
        _map = new HashMap<>();
    }

    /**
     * Adds data to the provider
     * @param key the key of the data
     * @param data the string to be stored
     */
    public void addData(Integer key, String data){
        _map.put(key, data);
    }

    /**
     * Gets the String at the specified key
     * @param key the key of the data
     * @return
     */
    @Override
    public String get(Integer key) {
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
