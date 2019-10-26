import java.util.Random;
import java.util.*;
import java.io.*;

/**
 * Implements a 3-d word search puzzle program.
 */
public class WordSearch3D {

	public static final int MAX_ITERATIONS = 1000; //TODO Double check that public is okay

	public WordSearch3D () {
	}

	/**
	 * Searches for all the words in the specified list in the specified grid.
	 * You should not need to modify this method.
	 * @param grid the grid of characters comprising the word search puzzle
	 * @param words the words to search for
	 * @param /a list of lists of locations of the letters in the words
	 */
	public int[][][] searchForAll (char[][][] grid, String[] words) {
		final int[][][] locations = new int[words.length][][];
		for (int i = 0; i < words.length; i++) {
			locations[i] = search(grid, words[i]);
		}
		return locations;
	}

	/**
	 * Searches for the specified word in the specified grid.
	 * @param grid the grid of characters comprising the word search puzzle
	 * @param word the word to search for
	 * @return If the grid contains the
	 * word, then the method returns a list of the (3-d) locations of its letters; if not, 
	 */
	public int[][] search (char[][][] grid, String word) {
		if(word == null){
			return null;
		} else if (word.equals("")) {
			return new int[0][0];
		} else if (!canFitInGrid(grid, word)){ //TODO Check if we need equals
			return null;
		}

		final char firstChar = word.charAt(0);
		final char lastChar = word.charAt(word.length() - 1);

		final ArrayList<int[]> startPos = new ArrayList<>();
		final ArrayList<int[]> endPos = new ArrayList<>();

		//TODO Check with words length 1
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++) { //TODO Check if we need to handle arrays that are not rectangles
				for(int k = 0; k < grid[0][0].length; k++){
					if(grid[i][j][k] == firstChar){
						startPos.add(new int[]{i, j, k});
					} else if (grid[i][j][k] == lastChar){
						endPos.add(new int[]{i, j, k});
					}
				}
			}
		}

		for (int[] startE : startPos) {
			for (int[] endE : endPos) {
				final int diffI = Math.abs(startE[0] - endE[0]);
				final int diffJ = Math.abs(startE[1] - endE[1]);
				final int diffK = Math.abs(startE[2] - endE[2]);
				//TODO check logic behind this
				//TODO Test that - 1 is okay to do here for other grids
				if((diffI == 0 || diffI == word.length() - 1) &&
						(diffJ == 0 || diffJ == word.length() - 1) &&
							(diffK == 0 || diffK == word.length() - 1)){
					if(checkWord(grid, word, startE, endE)){
						return new int[][]{startE, endE};
					}
				}
			}
		}

		return null;
	}

	private boolean checkWord(char[][][] grid, String word, int[] startPos, int[] endPos){
		int[] currentPos = {startPos[0],startPos[1],startPos[2]};
		int deltaI = endPos[0] - startPos[0];
		int deltaJ = endPos[1] - startPos[1];
		int deltaK = endPos[2] - startPos[2];

		final StringBuilder str = new StringBuilder();
		str.append(grid[startPos[0]][startPos[1]][startPos[2]]);
		while(deltaI != 0 || deltaJ != 0 || deltaK != 0){
			currentPos[0] = followPath(deltaI, currentPos[0]);
			currentPos[1] = followPath(deltaJ, currentPos[1]);
			currentPos[2] = followPath(deltaK, currentPos[2]);

			deltaI = endPos[0] - currentPos[0];
			deltaJ = endPos[1] - currentPos[1];
			deltaK = endPos[2] - currentPos[2];

			str.append(grid[currentPos[0]][currentPos[1]][currentPos[2]]);
		}
		return word.equals(str.toString());
	}
	//TODO change name potentially
	private int followPath(int delta, int curPos){
		if(delta > 0){
			return curPos + 1;
		}else if(delta < 0){
			return curPos - 1;
		}else{
			return curPos;
		}
	}

	private boolean canFitInGrid(final char[][][] grid, final String word){
		return grid.length < word.length() || grid[0].length < word.length() || grid[0][0].length < word.length();
	}

	/**
	 * Tries to create a word search puzzle of the specified size with the specified
	 * list of words.
	 * @param words the list of words to embed in the grid
	 * @param sizeX size of the grid along first dimension
	 * @param sizeY size of the grid along second dimension
	 * @param sizeZ size of the grid along third dimension
	 * @return a 3-d char array if successful that contains all the words, or <tt>null</tt> if
	 * no satisfying grid could be found.
	 */
	public char[][][] make (String[] words, int sizeX, int sizeY, int sizeZ) {
		final Random rng = new Random();
		final char[][][] grid = new char[sizeX][sizeY][sizeZ]; //TODO Check that sizes are in the correct spot
		int currentIterations = MAX_ITERATIONS;

		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++) {
				for(int k = 0; k < grid[0][0].length; k++){
					grid[i][j][k] = (char) (rng.nextInt(26) + 'a');
				}
			}
		}

		return grid;
	}

	/**
	 * Exports to a file the list of lists of 3-d coordinates.
	 * You should not need to modify this method.
	 * @param locations a list (for all the words) of lists (for the letters of each word) of 3-d coordinates.
	 * @param filename what to name the exported file.
	 */
	public static void exportLocations (int[][][] locations, String filename) {
		// First determine how many non-null locations we have
		int numLocations = 0;
		for (int i = 0; i < locations.length; i++) {
			if (locations[i] != null) {
				numLocations++;
			}
		}

		try (final PrintWriter pw = new PrintWriter(filename)) {
			pw.print(numLocations);  // number of words
			pw.print('\n');
			for (int i = 0; i < locations.length; i++) {
				if (locations[i] != null) {
					pw.print(locations[i].length);  // number of characters in the word
					pw.print('\n');
					for (int j = 0; j < locations[i].length; j++) {
						for (int k = 0; k < 3; k++) {  // 3-d coordinates
							pw.print(locations[i][j][k]);
							pw.print(' ');
						}
					}
					pw.print('\n');
				}
			}
			pw.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	/**
	 * Exports to a file the contents of a 3-d grid.
	 * You should not need to modify this method.
	 * @param grid a 3-d grid of characters
	 * @param filename what to name the exported file.
	 */
	public static void exportGrid (char[][][] grid, String filename) {
		try (final PrintWriter pw = new PrintWriter(filename)) {
			pw.print(grid.length);  // height
			pw.print(' ');
			pw.print(grid[0].length);  // width
			pw.print(' ');
			pw.print(grid[0][0].length);  // depth
			pw.print('\n');
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[0].length; y++) {
					for (int z = 0; z < grid[0][0].length; z++) {
						pw.print(grid[x][y][z]);
						pw.print(' ');
					}
				}
				pw.print('\n');
			}
			pw.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	/**
	 * Creates a 3-d word search puzzle with some nicely chosen fruits and vegetables,
	 * and then exports the resulting puzzle and its solution to grid.txt and locations.txt
	 * files.
	 */
	public static void main (String[] args) {
		final WordSearch3D wordSearch = new WordSearch3D();
		final String[] words = new String[] { "apple", "orange", "pear", "peach", "durian", "lemon", "lime", "jackfruit", "plum", "grape", "apricot", "blueberry", "tangerine", "coconut", "mango", "lychee", "guava", "strawberry", "kiwi", "kumquat", "persimmon", "papaya", "longan", "eggplant", "cucumber", "tomato", "zucchini", "olive", "pea", "pumpkin", "cherry", "date", "nectarine", "breadfruit", "sapodilla", "rowan", "quince", "toyon", "sorb", "medlar" };
		final int xSize = 10, ySize = 10, zSize = 10;
		final char[][][] grid = wordSearch.make(words, xSize, ySize, zSize);
		exportGrid(grid, "grid.txt");

		final int[][][] locations = wordSearch.searchForAll(grid, words);
		exportLocations(locations, "locations.txt");
	}
}
