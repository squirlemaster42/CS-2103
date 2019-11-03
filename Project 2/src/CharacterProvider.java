import java.util.HashMap;
import java.util.Map;

public class CharacterProvider implements DataProvider<Integer, Character> {

    private final Map<Integer, Character> _map;

    public CharacterProvider() {
        _map = new HashMap<>();
    }

    public void addData(Integer key, Character data){
        _map.put(key,data);
    }
    @Override
    public Character get(Integer key) {
        return _map.get(key);
    }
}
