import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * Code to test <tt>WordSearch3D</tt>.
 */
public class WordSearchTester {
	private WordSearch3D _wordSearch;

	@Test
	/**
	 * Verifies that make can generate a very simple puzzle that is effectively 1d.
	 */
	public void testMake1D () {
		final String[] words = new String[] { "java" };
		// Solution is either java or avaj
		final char[][][] grid = _wordSearch.make(words, 1, 1, 4);
		final char[] row = grid[0][0];
		assertTrue("Grid was: " + Arrays.toString(row), (row[0] == 'j' && row[1] == 'a' && row[2] == 'v' && row[3] == 'a') ||
						   (row[3] == 'j' && row[2] == 'a' && row[1] == 'v' && row[0] == 'a'));
	}

	@Test
	public void testMake2D () {
		final String[] words = new String[] {"java", "cpp"};
		// Solution is either java or avaj
		final char[][][] grid = _wordSearch.make(words, 1, 2, 4);
		assertNotNull("Grid was: " + Arrays.deepToString(grid), _wordSearch.search(grid, "java"));
		assertNotNull("Grid was: " + Arrays.deepToString(grid), _wordSearch.search(grid, "cpp"));
	}

	@Test
	/**
	 * Verifies that make returns null when it's impossible to construct a puzzle.
	 */
	public void testMakeImpossible () {
		final String[] words = new String[] {"abc", "def", "cd", "ad"};
		final char[][][] grid = _wordSearch.make(words,2,3,1);
		assertNull(grid);
	}

	@Test
	public void test3DOverlap(){
		final String[] words = new String[] {"ta","ak","at"};
		final char[][][] grid = _wordSearch.make(words,2,2,2);
		assertNotNull(grid);
	}
	@Test
	public void test2DOverlap(){
		final String[] words = new String[] {"tak","ak","ta"};
		final char[][][] grid = _wordSearch.make(words,3,2,1);
		assertNotNull(grid);
	}
	/**
	 * Verifies that make can generate a 3D grid
	 */
	@Test
	public void testMake3D(){
		final String[] words = new String[] {"top", "cpp","at","cak"};
		final char[][][] grid = _wordSearch.make(words,3,3,3);
		System.out.println(Arrays.deepToString(grid)); //Does not think it can create this grid
		assertNotNull(_wordSearch.search(grid,"top"));
		assertNotNull(_wordSearch.search(grid,"cpp"));
		assertNotNull(_wordSearch.search(grid,"at"));
		assertNotNull(_wordSearch.search(grid,"cak"));
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

	/**
	 * Test searching for a String that is null
	 */
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

	/**
	 * Test searching for a String that is empty
	 */
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

	/**
	 * Test searching for a String that is not in the grid
	 */
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

	/**
	 * Test finding a word that is larger than the grid
	 */
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

	/**
	 * Test finding a word that is at an edge of the grid
	 */
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
		assertArrayEquals(coord, _wordSearch.search(testGrid,"avw"));
	}

	/**
	 * Test finding a word that is not at the edge of the grid that is also backwards
	 * TODO Fix this
	 */
	@Test
	public void test3DNotAtEdgeAndBackwards(){
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
		int[][] coord = {{3, 2, 1},
						 {1, 2, 1}};
		assertArrayEquals(coord,_wordSearch.search(testGrid,"gvs"));
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

	//TODO Write a test to find a string that is backwards

	@Before
	public void setUp () {
		_wordSearch = new WordSearch3D();
	}
}
