import java.util.HashMap;
import java.util.Map;

public class StringProvider implements DataProvider<Integer, String> {

    private final Map<Integer, String> _map;
    private  int retrieves = 0;
    public StringProvider(){
        _map = new HashMap<>();
    }

    public void addData(Integer key, String data){
        _map.put(key, data);
    }

    @Override
    public String get(Integer key) {
        retrieves++;
        return _map.get(key);
    }

    public int getRetrieves(){return retrieves;}
}
