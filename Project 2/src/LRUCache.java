import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private Map<T, SinglyLinkedList.Node<U>> _map;
	private SinglyLinkedList<U> _linkedList;
	private final int _capacity;
	private final DataProvider _provider;

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
		//TODO count misses
		return _map.get(key).getValue();
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return 0;
	}
}
