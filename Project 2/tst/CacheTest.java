import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {

	/**
	 * Tests if the LRU cache is implemented corretly
	 */
	@Test
	public void leastRecentlyUsedIsCorrect () {
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,1);
		provider.addData(2,"circle");
		provider.addData(1,"square");
		provider.addData(9,"rectangle");
		provider.addData(4,"triangle");

		assertEquals(0, cache.getNumMisses());
		cache.get(2);
		assertEquals(1, cache.getNumMisses());
		cache.get(2);
		assertEquals(1, cache.getNumMisses());
		cache.get(4);
		assertEquals(2, cache.getNumMisses());
	}

	/**
	 * Tests cache when looking at a null value stored behind a non-null key
	 */
	@Test
	public void testNullValue(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider, 5);
		provider.addData(5, null);
		provider.addData(8, "8");
		provider.addData(1, "1");
		assertNull(cache.get(5));
		assertEquals(1, provider.getRetrieves());
		assertEquals("8", cache.get(8));
		assertEquals(2, provider.getRetrieves());
		assertNull(cache.get(5));
		assertEquals(2, provider.getRetrieves());
	}

	/**
	 * Tests cache using StringProvider
	 */
	@Test
	public void testGetWithStringProvider(){
		StringProvider provider = new StringProvider();
		Cache<Integer,String> cache = new LRUCache<>(provider,5);
		provider.addData(0,"yes");
		provider.addData(2, "java");
		provider.addData(1,"oop");
		provider.addData(3,"eeee");
		assertEquals("yes", cache.get(0));
	}

	/**
	 * Tests cache trying to get a key that doesn't exist using StringProvider
	 */
	@Test
	public void testGetNotExistingStringProvider(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,5);
		provider.addData(4,"there");
		provider.addData(0,"their");
		provider.addData(1,"they're");
		assertNull(cache.get(3));
	}

	/**
	 * Tests tht the cache works properly when it has a capacity of one
	 */
	@Test
	public void testWorkWithCapacityOfOne(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,1);
		provider.addData(10,"light");
		assertEquals("light", cache.get(10));
	}

	/**
	 * Tests that the cache works properly when it has a capacity of zero
	 */
	@Test
	public void testWorkWithCapacityZero(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,0);
		provider.addData(13,"apple");
		assertEquals("apple",cache.get(13));
	}

	/**
	 * Tests cache using CharacterProvider
	 */
	@Test
	public void testGetWithCharacterProvider(){
		CharacterProvider provider = new CharacterProvider();
		Cache<Integer, Character> cache = new LRUCache<>(provider,3);
		provider.addData(0,'a');
		provider.addData(2, 's');
		provider.addData(1,'g');
		provider.addData(3,'r');
		assertEquals(Character.valueOf('a'), cache.get(0));
		assertEquals(Character.valueOf('s'), cache.get(2));
		assertEquals(Character.valueOf('g'), cache.get(1));
		assertEquals(Character.valueOf('s'), cache.get(2));
	}

	/**
	 * Tests cache trying to get a key that doesn't exist using CharacterProvider
	 */
	@Test
	public void testGetNotExistingCharacterProvider(){
		CharacterProvider provider = new CharacterProvider();
		Cache<Integer, Character> cache = new LRUCache<>(provider,5);
		provider.addData(4,'t');
		provider.addData(0,'s');
		provider.addData(1,'l');
		assertNull(cache.get(3));
	}

	/**
	 * Tests that the cache works correctly if it has a capacity of -1
	 */
	@Test
	public void testNegOneCap(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,-1);
		provider.addData(3,"test");
		assertEquals("test",cache.get(3));
	}

	/**
	 * Tests the cache works when the provider has no data
	 */
	@Test
	public void testProviderHasNoData(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider, 2);
		assertNull(cache.get(0));
	}

	/**
	 * Tests that the getRetrieves method works as intended
	 */
	@Test
	public void testGetRetrieved(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider, 2);
		provider.addData(1,"apple");
		provider.addData(3,"duck");
		provider.addData(19, "garlicBread");
		cache.get(3);
		cache.get(19);
		assertEquals(2, provider.getRetrieves());
		cache.get(19);
		assertEquals(2, provider.getRetrieves());
		cache.get(1);
		assertEquals(3, provider.getRetrieves());
	}

	/**
	 * Tests that the cache works properly when an existing key gets set to new data
	 */
	@Test
	public void testSettingKeyToNewData(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider, 4);
		provider.addData(1,"apple");
		provider.addData(3,"duck");
		provider.addData(19, "garlicBread");
		provider.addData(3, "pineapple");
		assertEquals("pineapple", cache.get(3));
		provider.addData(5,"bee");
		provider.addData(3 , "bee");
		provider.addData(5, "apple");
		assertEquals("apple", cache.get(5));
	}

	/**
	 * Tests that the cache works properly when the given key is null
	 */
	@Test
	public void testNullKey(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider, 4);
		provider.addData(null,"apple");
		assertEquals("apple",cache.get(null));
	}
}
