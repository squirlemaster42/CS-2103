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

    boolean contains(final String key){
        return movieMap.containsKey(key);
    }

    MovieNode getMovie(final String key){
        return movieMap.get(key);
    }

    void addMovie(final MovieNode movie){
        movieMap.put(movie.getName(), movie);
    }

    int size() {
        return movieMap.size();
    }

    Map<String, MovieNode> getMovieMap(){
        return movieMap;
    }
}
