import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private Map<T, SinglyLinkedList.Node<T, U>> _map;
	private SinglyLinkedList<T, U> _linkedList;
	private final int _capacity;
	private final DataProvider<T, U> _provider;
	private int misses = 0;

	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (final DataProvider<T, U> provider, final int capacity) {
		this._map = new HashMap<>();
		this._linkedList = new SinglyLinkedList<>();
		this._capacity = capacity;
		this._provider = provider;
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		if(_map.containsKey(key)){
			return _map.get(key).getValue();
		}else{
			misses++;
			if(_map.size() == _capacity){
				_map.remove(_linkedList.removeTail());
			}
			final U data = _provider.get(key);
			final SinglyLinkedList.Node<T, U> node = new SinglyLinkedList.Node<>(key, data);
			_map.put(key, node);
			_linkedList.push(node);
			return data;
		}
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return misses;
	}
}
