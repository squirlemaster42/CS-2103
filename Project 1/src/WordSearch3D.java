import java.util.Random;
import java.util.*;
import java.io.*;

/**
 * Implements a 3-d word search puzzle program.
 */
public class WordSearch3D {

	//TODO Comments
	private static final int MAX_ITERATIONS = 1000;

	public WordSearch3D () {
	}

	/**
	 * Searches for all the words in the specified list in the specified grid.
	 * You should not need to modify this method.
	 * @param grid the grid of characters comprising the word search puzzle
	 * @param words the words to search for
	 * @return /a list of lists of locations of the letters in the words
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
	 * returns null
	 */
	public int[][] search (final char[][][] grid, final String word) {
		if(word == null){ //Checks is the word is null
			return null;
		} else if (word.equals("")) { //Checks if the word is an empty String
			return new int[0][0];
		} else if (!canFitInGrid(grid, word)){ //Checks if the word can fit in the grid
			return null;
		}

		final char firstChar = word.charAt(0); //The first char of the word
		final char lastChar = word.charAt(word.length() - 1); //The last char of the word

		final ArrayList<int[]> startPos = getInstanceOfChar(grid, firstChar); //Stores all of the positions of the first char
		final ArrayList<int[]> endPos = getInstanceOfChar(grid, lastChar); //Stores all of the positions of that last char

		//Checks that the difference in position on each direction is either 0 or the length of the String - 1
		for (int[] startE : startPos) {
			for (int[] endE : endPos) {
				final int diffI = Math.abs(startE[0] - endE[0]);
				final int diffJ = Math.abs(startE[1] - endE[1]);
				final int diffK = Math.abs(startE[2] - endE[2]);

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

	/**
	 * Returns an ArrayList of all positions where a character appears
	 * @param grid The grid of characters to search through
	 * @param c The character to search for
	 * @return An ArrayList of int[]s representing where the char c appears
	 */
	private ArrayList<int[]> getInstanceOfChar(final char[][][] grid, final char c){
		final ArrayList<int[]> pos = new ArrayList<>();
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++) {
				for(int k = 0; k < grid[0][0].length; k++){
					if(grid[i][j][k] == c){
						pos.add(new int[] {i,j,k});
					}
				}
			}
		}
		return pos;
	}

	/**
	 * Check if the word exists between the start and end positions
	 * @param grid The grid to search through
	 * @param word The word to check
	 * @param startPos The start position of the word
	 * @param endPos The end positions of the word
	 * @return Return true if the word exists, false otherwise
	 */
	private boolean checkWord(final char[][][] grid, final String word, final int[] startPos, final int[] endPos){
		final int[] currentPos = {startPos[0],startPos[1],startPos[2]};
		int deltaI = endPos[0] - startPos[0];
		int deltaJ = endPos[1] - startPos[1];
		int deltaK = endPos[2] - startPos[2];

		final StringBuilder str = new StringBuilder();
		str.append(grid[startPos[0]][startPos[1]][startPos[2]]);
		//While we have not reached the last char in the String
		//All deltas will either be 0 or the length of the String - 1
		//This mean that when one of the deltas that did not start at 0 hits 0
		//they all hit 0.
		while(deltaI != 0 || deltaJ != 0 || deltaK != 0){
			//Updates the current position to follow the path to the last char in the String
			currentPos[0] = followPath(deltaI, currentPos[0]);
			currentPos[1] = followPath(deltaJ, currentPos[1]);
			currentPos[2] = followPath(deltaK, currentPos[2]);

			//Updates the distance to the last char in the String
			deltaI = endPos[0] - currentPos[0];
			deltaJ = endPos[1] - currentPos[1];
			deltaK = endPos[2] - currentPos[2];

			str.append(grid[currentPos[0]][currentPos[1]][currentPos[2]]);
		}
		return word.equals(str.toString());
	}

	/**
	 * Updates the current position of the path to the last character in the string
	 * @param delta change in direction we want to go in
	 * @param curPos where we are currently located
	 * @return next int  position to follow
	 */
	private int followPath(final int delta, final int curPos){
		if(delta > 0){
			return curPos + 1;
		}else if(delta < 0){
			return curPos - 1;
		}else{
			return curPos;
		}
	}

	/**
	 * Returns true if the word can fit in the grid
	 * @param grid The grid to check the word against
	 * @param word The word to check
	 * @return Returns true of the word will fit in the grid, false otherwise
	 */
	private boolean canFitInGrid(final char[][][] grid, final String word){
		return grid.length >= word.length() || grid[0].length >= word.length() || grid[0][0].length >= word.length();
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
	public char[][][] make (final String[] words, final int sizeX, final int sizeY, final int sizeZ) {
		boolean genNewGrid = true;
		LockableCharacter[][][] grid = randomlyGenGrid(sizeX, sizeY, sizeZ);
		System.out.println(Arrays.deepToString(grid));
		for(int iteration = 0; iteration < MAX_ITERATIONS; iteration++){
			for(final String currentWord : words) {
				boolean wordPlaced = false;
				int counter = 0;
				while(!wordPlaced){
					final char[][][] charGrid = lockableCharToCharGrid(grid);
					System.out.println("         Placing: " + currentWord + " in " + Arrays.deepToString(charGrid));
					//Something is wrong with placing in 3d
					final Map<Integer, ArrayList<int[]>> charMap = new HashMap<>();
					for (int i = 0; i < currentWord.length(); i++) {
						charMap.put(i, getInstanceOfChar(charGrid, currentWord.charAt(i)));
					}
					//TODO Something is prob not being reset properly
					//Grid ends up just being all the same letters
					//Need to figure out where the grid needs to be reset
					for (final Map.Entry<Integer, ArrayList<int[]>> entry : charMap.entrySet()) {
						final ArrayList<int[]> v = entry.getValue();
						for (int[] pos : v) {
							//This might be getting called more than it needs to
							final LockableCharacter[][][] newGrid = placeWordInGrid(currentWord, pos, entry.getKey(), grid);
							if(newGrid != null){
								grid = newGrid; //This might be getting run when it should not
								wordPlaced = true;
								genNewGrid = false;
							}
						}
					}
					if(genNewGrid){
						grid = randomlyGenGrid(sizeX, sizeY, sizeZ);
					}
					counter++;
					if(counter > 30){
						break;
					}
					System.out.println("Finished Placing: " + currentWord + " in " + Arrays.deepToString(charGrid));
				}
			}
			if(containsAllWords(lockableCharToCharGrid(grid), words)){
				return lockableCharToCharGrid(grid);
			}else{
				grid = randomlyGenGrid(sizeX, sizeY, sizeZ);
			}
		}
		return null;
	}

	/**
	 * Checks if the grid contains all words in the word array
	 * @param grid 3D grid of characters
	 * @param words list of words to check
	 * @return true if the grid contains all the words false otherwise
	 */
	private boolean containsAllWords(final char[][][] grid, final String[] words){
		for(final String word : words){
			if(search(grid, word) == null){
				return false;
			}
		}
		return true;
	}

	/**
	 * Attempts to find a valid place to place the word in the grid
	 * @param currentWord word to place
	 * @param pos location of the letter that can be used
	 * @param charPos the index of the character thats mapped to pos
	 * @param grid grid to place the word in
	 * @return a lockablecharacter grid with the word in it
	 */
	private LockableCharacter[][][] placeWordInGrid(final String currentWord, final int[] pos, final int charPos, final LockableCharacter[][][] grid){
		final Random rng = new Random();
		boolean cannotPlace = false;
		for (int iter = 0; iter < 100; iter++) {
			final int deltaI = rng.nextInt(3) - 1;
			final int deltaJ = rng.nextInt(3) - 1;
			final int deltaK = rng.nextInt(3) - 1;
			try {
				//trying to place on the left half
				//TODO Something is prob not being reset properly
				for(int i = currentWord.length() - charPos - 1; i >= 0; i--){
					if(canPlaceChar(grid[pos[0]][pos[1]][pos[2]],currentWord.charAt(i))){
						final int iPos = pos[0] + (deltaI * i * getDirection(i, charPos));
						final int jPos = pos[1] + (deltaJ * i * getDirection(i, charPos));
						final int kPos = pos[2] + (deltaK * i * getDirection(i, charPos));
						System.out.println("Trying to place: " + currentWord.charAt(i));
						grid[iPos][jPos][kPos].setChar(currentWord.charAt(i));
					}else{
						cannotPlace = true;
						break;
					}
				}
				//Try to place right half
				if(!cannotPlace){
					for(int i = charPos + 1; i < currentWord.length(); i++){
						if(canPlaceChar(grid[pos[0]][pos[1]][pos[2]],currentWord.charAt(i))){
							final int iPos = pos[0] + (deltaI * i * getDirection(i, charPos));
							final int jPos = pos[1] + (deltaJ * i * getDirection(i, charPos));
							final int kPos = pos[2] + (deltaK * i * getDirection(i, charPos));
							System.out.println("Trying to place: " + currentWord.charAt(i));
							grid[iPos][jPos][kPos].setChar(currentWord.charAt(i));
						}else{
							cannotPlace = true;
							break;
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
			if(search(lockableCharToCharGrid(grid), currentWord) != null){
				return grid;
			}
		}
		return null;
	}

	/**
	 * Determines if a character can be places
	 * @param curr The character to check against
	 * @param toPlace The character to place
	 * @return True if the characters are equal or the current character is not locked
	 */
	private boolean canPlaceChar(final LockableCharacter curr, final char toPlace){
		//return true if curr is same as toPlace or false and therefore we can change cur
		return curr.getChar() == toPlace || !curr.isLocked();
	}

	/**
	 * Gets the direction to travel in in order to get the desired position in the string
	 * @param compare position of the current char
	 * @param master position of the char to compare to
	 * @return -1 if you need to move toward the beginning of the string or 1 if you need to
	 * move toward the end of the string
	 */
	private int getDirection(final int compare, final int master){
		return compare < master ? -1 : 1;
	}

	/**
	 * Converts an array of LockableChars to an array of chars
	 * @param grid The array of LockableChars
	 * @return char array generated from the LockableChar array
	 */
	private char[][][] lockableCharToCharGrid(final LockableCharacter[][][] grid){
		final char[][][] tempGrid = new char[grid.length][grid[0].length][grid[0][0].length];
		for(int i = 0; i < tempGrid.length; i++){
			for(int j = 0; j < tempGrid[0].length;j++){
				for(int k = 0; k < tempGrid[0][0].length;k++){
					tempGrid[i][j][k] = grid[i][j][k].getChar();
				}
			}
		}
		return tempGrid;
	}

	/**
	 * randomly creates a new 3D grid of lockableCharacters
	 * @param sizeX width of the grid
	 * @param sizeY height of the grid
	 * @param sizeZ depth of the grid
	 * @return a new LockableCharacter grid with random characters
	 */
	private LockableCharacter[][][] randomlyGenGrid(final int sizeX, final int sizeY, final int sizeZ){
		final LockableCharacter[][][] grid = new LockableCharacter[sizeX][sizeY][sizeZ];
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++) {
				for(int k = 0; k < grid[0][0].length; k++){
					grid[i][j][k] = new LockableCharacter();
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
