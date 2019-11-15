import java.util.HashMap;
import java.util.Map;

public class MovieMap {

    private static MovieMap instance;

    static MovieMap getInstance(){
        if(instance == null){
            instance = new MovieMap();
        }
        return instance;
    }

    private final Map<String, MovieNode> movieMap;

    private MovieMap(){
        movieMap = new HashMap<>();
    }

    /**
     * Checks if the movie map contains a specific movie
     * @param key movie to check for
     * @return true if the movie exists false otherwise
     */
    boolean contains(final String key){
        return movieMap.containsKey(key);
    }

    /**
     * Gets the movie node at the specified key
     * @param key specifies which movie node to get
     * @return movie node that was specified by key
     */
    MovieNode getMovie(final String key){
        return movieMap.get(key);
    }

    /**
     * Adds the specified movie node
     * @param movie movie node to add
     */
    void addMovie(final MovieNode movie){
        movieMap.put(movie.getName(), movie);
    }

    /**
     * Gets the size of the movie map
     * @return the size of the movie map
     */
    int size() {
        return movieMap.size();
    }

    /**
     * Gets the movie map
     * @return the movie map
     */
    Map<String, MovieNode> getMovieMap(){
        return movieMap;
    }
}
