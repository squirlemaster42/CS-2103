import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * Code to test <tt>WordSearch3D</tt>.
 */
public class WordSearchTester {
	private WordSearch3D _wordSearch;


	/**
	 * Verifies that make can generate a very simple puzzle that is effectively 1d.
	 */
    @Test
	public void testMake1D () {
		final String[] words = new String[] { "java" };
		// Solution is either java or avaj
		final char[][][] grid = _wordSearch.make(words, 1, 1, 4);
		final char[] row = grid[0][0];
		assertTrue("Grid was: " + Arrays.toString(row), (row[0] == 'j' && row[1] == 'a' && row[2] == 'v' && row[3] == 'a') ||
						   (row[3] == 'j' && row[2] == 'a' && row[1] == 'v' && row[0] == 'a'));
	}

    /**
     * Verifies that make can generate a 3D grid
     */
    @Test
    public void testMake3D(){
        final String[] words = new String[] {"top", "cpp","at","cak"};
        final char[][][] grid = _wordSearch.make(words,3,3,3);
        assertNotNull(_wordSearch.search(grid,"top"));
        assertNotNull(_wordSearch.search(grid,"cpp"));
        assertNotNull(_wordSearch.search(grid,"at"));
        assertNotNull(_wordSearch.search(grid,"cak"));
    }


	/**
	 * Verifies that make returns null when it's impossible to construct a puzzle.
	 */
    @Test
	public void testMakeImpossible () {
		final String[] words = new String[] {"abc", "def", "cd", "ad"};
		final char[][][] grid = _wordSearch.make(words,2,3,1);
		assertNull(grid);
	}

    /**
     * Verifies that make returns null when the words given are null
     */
	@Test
	public void testMakeNullString(){
		final String[] words = new String[4];
		final char[][][] grid = _wordSearch.make(words,2,2,2);
		assertNull(grid);
	}

    /**
     * Verifies that make works when a given word is an empty string
     */
	@Test
    public void testMakeEmptyString(){
	    final String[] words = new String[]{"","abc","dee"};
	    final char[][][] grid = _wordSearch.make(words,3,3,3);
	    assertNotNull(grid);
    }

    /**
     * Verifies that make returns null when a given word is too long to fit into the grid
     */
    @Test
    public void testMakeWordTooLong(){
        final String[] words = new String[]{"lmao","ab","de"};
        final char[][][] grid = _wordSearch.make(words,2,2,2);
        assertNull(grid);
    }

    /**
     * Verifies that make generates a grid when a given word has a length of one
     */
    @Test
    public void testMakeWordLengthOne(){
        final String[] words = new String[]{"a","cee","fff"};
        final char[][][] grid = _wordSearch.make(words,3,3,3);
        assertNotNull(grid);
        assertNotNull("Grid was: " + Arrays.deepToString(grid), _wordSearch.search(grid, "a"));
        assertNotNull("Grid was: " + Arrays.deepToString(grid), _wordSearch.search(grid, "cee"));
        assertNotNull("Grid was: " + Arrays.deepToString(grid), _wordSearch.search(grid, "fff"));
    }

	/**
	 * Verifies that make can generate a grid when it's *necessary* for words to share
	 * some common letter locations.
	 */
    @Test
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

    /**
     * Finding a word with a length of one
     */
	@Test
    public void testSearchWordLengthOne(){
        final char[][][] grid = new char[][][]
                { { { 'a', 'b', 'c' },
                    { 'd', 'f', 'e' } } };
        int[][] coord = {{0,0,1},
                         {0,0,1}};
        assertArrayEquals(coord,_wordSearch.search(grid, "b"));
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
