import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * Code to test <tt>WordSearch3D</tt>.
 */
public class WordSearchTester {
	private WordSearch3D _wordSearch;

	//TODO Use search to test all words are in the grid
	//TODO Verify that we can deal with something impossible

	//TODO Test this 2 x 3 Grid
	// X X X
	// X X X
	
	//abc, def, cd, ad

	@Test
	/**
	 * Verifies that make can generate a very simple puzzle that is effectively 1d.
	 */
	public void testMake1D () {
		final String[] words = new String[] { "java" };
		// Solution is either java or avaj
		final char[][][] grid = _wordSearch.make(words, 1, 1, 4);
		final char[] row = grid[0][0];
		assertTrue((row[0] == 'j' && row[1] == 'a' && row[2] == 'v' && row[3] == 'a') ||
		           (row[3] == 'j' && row[2] == 'a' && row[1] == 'v' && row[0] == 'a'));
	}

	@Test
	/**
	 * Verifies that make returns null when it's impossible to construct a puzzle.
	 */
	public void testMakeImpossible () {
		// TODO: implement me
	}

	@Test
	/**
	 * Verifies that make can generate a grid when it's *necessary* for words to share
	 * some common letter locations.
	 */
	public void testMakeWithIntersection () {
		final String[] words = new String[] { "amc", "dmf", "gmi", "jml", "nmo", "pmr", "smu", "vmx", "yma", "zmq" };
		final char[][][] grid = _wordSearch.make(words, 3, 3, 3);
		assertNotNull(grid);
	}

	@Test
	/**
	 * Verifies that make returns a grid of the appropriate size.
	 */
	public void testMakeGridSize () {
		final String[] words = new String[] { "at", "it", "ix", "ax" };
		final char[][][] grid = _wordSearch.make(words, 17, 11, 13);
		assertEquals(grid.length, 17);
		for (int x = 0; x < 2; x++) {
			assertEquals(grid[x].length, 11);
			for (int y = 0; y < 2; y++) {
				assertEquals(grid[x][y].length, 13);
			}
		}
	}

	/* TODO: write more methods for both make and search. */

	//TODO Comment
	@Test
	public void testSearchNullString(){
		char[][][] testGrid = {
				{{'a', 'b'},
						{'c', 'd'}},
				{{'t', 'r'},
						{'w', 'z'}}
		};
		assertNull(_wordSearch.search(testGrid, null));
	}

	@Test
	public void testSearchEmptyString(){
		char[][][] testGrid = {
				{{'a', 'b'},
						{'c', 'd'}},
				{{'t', 'r'},
						{'w', 'z'}}
		};
		assertArrayEquals(new int[0][0], _wordSearch.search(testGrid, ""));
	}

	@Test
	public void testSearchStringNotInGrid(){
		char[][][] testGrid = {
				{{'a', 'b'},
						{'c', 'd'}},
				{{'t', 'r'},
						{'w', 'z'}}
		};
		assertNull(_wordSearch.search(testGrid, "we"));
	}

	@Test
	public void testWordBiggerThanGrid(){
		char[][][] testGrid = {
				{{'a', 'b'},
						{'c', 'e'}},
				{{'e', 'r'},
						{'w', 'e'}}
		};
		assertNull(_wordSearch.search(testGrid, "wee"));
	}

	@Test
	public void test3DAtEdge(){
		char[][][] testGrid = {
				{{'a', 'b' ,'c'},
						{'x', 'z', 'y'},
								{'m', 'n', 'o'}},
				{{'e', 'r', 'd'},
						{'g', 'v', 'b'},
								{'p', 's', 'q'}},
				{{'f', 'i', 't'},
						{'j', 'k', 'l'},
								{'z', 'v', 'w'}}
		};

		int[][] coord = {{0,0,0},
						 {2,2,2}};
		assertArrayEquals(coord ,_wordSearch.search(testGrid,"avw"));
	}

	@Test
	public void test3DNotAtEdge(){
		char[][][] testGrid = {
				{{'a', 'b' ,'c'},
						{'x', 'z', 'y'},
							{'m', 'n', 'o'},
								{'x', 'z', 'y'},
									{'m', 'n', 'o'}},
				{{'e', 'r', 'd'},
						{'g', 'v', 'b'},
							{'p', 's', 'q'},
								{'g', 'v', 'b'},
									{'p', 's', 'q'}},
				{{'f', 'i', 't'},
						{'j', 'k', 'l'},
							{'z', 'v', 'w'},
								{'g', 'v', 'b'},
									{'p', 's', 'q'}},
				{{'a', 's', 's'},
						{'d', 'f', 'l'},
							{'s', 'g', 'w'},
								{'g', 'j', 'b'},
									{'i', 's', 'i'}},
				{{'f', 'i', 't'},
						{'j', 'k', 's'},
							{'p', 'v', 'w'},
								{'g', 'o', 'b'},
									{'p', 's', 'j'}}
		};
		int[][] coord = {{1, 2, 1},
						 {4, 2, 1}};
		assertArrayEquals(coord,_wordSearch.search(testGrid,"svg"));
	}
	@Test
	/**
	 * Verifies that search works correctly in a tiny grid that is effectively 2D.
 	*/
	public void testSearchSimple () {
		// Note: this grid is 1x2x2 in size
		final char[][][] grid = new char[][][] { { { 'a', 'b', 'c' },
				{ 'd', 'f', 'e' } } };
		final int[][] location = _wordSearch.search(grid, "be");
		assertNotNull(location);
		assertEquals(location[0][0], 0);
		assertEquals(location[0][1], 0);
		assertEquals(location[0][2], 1);
		assertEquals(location[1][0], 0);
		assertEquals(location[1][1], 1);
		assertEquals(location[1][2], 2);
	}

	@Before
	public void setUp () {
		_wordSearch = new WordSearch3D();
	}
}
