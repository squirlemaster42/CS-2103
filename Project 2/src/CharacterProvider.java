import java.util.HashMap;
import java.util.Map;

public class CharacterProvider implements DataProvider<Integer, Character> {

    private final Map<Integer, Character> _map;
    private int retrieves = 0;
    public CharacterProvider() {
        _map = new HashMap<>();
    }

    public void addData(Integer key, Character data){
        _map.put(key,data);
    }
    @Override
    public Character get(Integer key) {
        retrieves++;
        return _map.get(key);
    }

    public int getRetrieves(){
        return retrieves;
    }
}
