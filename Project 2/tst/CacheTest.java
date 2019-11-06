import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
//TODO Make a more complex test
public class CacheTest {
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

	@Test
	public void testGetNotExistingStringProvider(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,5);
		provider.addData(4,"there");
		provider.addData(0,"their");
		provider.addData(1,"they're");
		assertNull(cache.get(3));
	}

	@Test
	public void testWorkWithCapacityOfOne(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,1);
		provider.addData(10,"light");
		assertEquals("light", cache.get(10));
	}

	@Test
	public void testWorkWithCapacityZero(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,0);
		provider.addData(13,"apple");
		assertEquals("apple",cache.get(13));
	}



	//TODO better name for this
	@Test
	public void testCapDoesntChange(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,2);
		provider.addData(0,"a");
		provider.addData(2,"b");
		provider.addData(6,"c");

		//TODO Make sure the right stuff is getting removed
		cache.get(0);
		cache.get(2);
		assertEquals(2, cache.getNumMisses());
		cache.get(6);
		assertEquals(3, cache.getNumMisses());
		cache.get(0);
		assertEquals(4, cache.getNumMisses());
	}

	@Test
	public void testGetWithCharacterProvider(){
		CharacterProvider provider = new CharacterProvider();
		Cache<Integer, Character> cache = new LRUCache<>(provider,3);
		provider.addData(0,'a');
		provider.addData(2, 's');
		provider.addData(1,'g');
		provider.addData(3,'r');
		assertEquals(Character.valueOf('a'), cache.get(0));
	}

	@Test
	public void testGetNotExistingCharacterProvider(){
		CharacterProvider provider = new CharacterProvider();
		Cache<Integer, Character> cache = new LRUCache<>(provider,5);
		provider.addData(4,'t');
		provider.addData(0,'s');
		provider.addData(1,'l');
		assertNull(cache.get(3));
	}

	//TODO Make sure we dont miss when we shouldnt
	@Test
	public void testNegOneCap(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider,-1);
		provider.addData(3,"test");

	}

	@Test
	public void testProviderHasNoData(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider, 2);
		assertNull(cache.get(0));
	}

	@Test
	public void testGetRetrieved(){
		StringProvider provider = new StringProvider();
		Cache<Integer, String> cache = new LRUCache<>(provider, 2);
		provider.addData(1,"apple");
		provider.addData(3,"duck");
		provider.addData(19, "garlicBread");
		cache.get(3);
		cache.get(19);
		assertEquals(2,provider.getRetrieves());
		cache.get(19);
		assertEquals(2,provider.getRetrieves());
		cache.get(1);
		assertEquals(3,provider.getRetrieves());
	}

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
}
