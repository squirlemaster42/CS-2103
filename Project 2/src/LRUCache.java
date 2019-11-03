/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private final int _capacity;
	private final DataProvider<T, U> _provider;
	private int misses = 0;
	private final LinkedHashMap<T, U> _backingStore; //TODO Look at name

	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (final DataProvider<T, U> provider, final int capacity) {
		this._capacity = capacity;
		this._provider = provider;
		_backingStore = new LinkedHashMap<>();
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		U returnVal = _backingStore.get(key);
		if(returnVal == null){
			misses++;
			if(_backingStore.getSize() >= _capacity){
				_backingStore.evictTail();
			}
			_backingStore.put(key, _provider.get(key));
		}
		return _backingStore.get(key);
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return misses;
	}
}
