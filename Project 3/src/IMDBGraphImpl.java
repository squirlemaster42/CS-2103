import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

public class IMDBGraphImpl implements IMDBGraph {

    //TODO Throw exceptions
    //TODO Check that names are being loaded correctly

    /**
     *
     * @param actorPath
     * @param actressPath
     * @throws IOException
     */
    IMDBGraphImpl(final String actorPath, final String actressPath) throws IOException {
        final Scanner actorScanner = new Scanner(new File(actorPath), StandardCharsets.ISO_8859_1);
        final Scanner actressScanner = new Scanner(new File(actressPath), StandardCharsets.ISO_8859_1);
        load(actorScanner);
        load(actressScanner);
        System.out.println("Actors: " + ActorMap.getInstance().size());
        System.out.println("Movies: " + MovieMap.getInstance().size());
    }

    /**
     * Loads and parses the file into memory
     * @param file the scanner containing the file to parse
     */
    private void load(final Scanner file) {
        boolean pastHeader = false; //Stores if we are past the header
        ActorNode lastActor = null; //Stores the lastActor seen
        while (file.hasNextLine()) { //Loops through the entire file
            String line = file.nextLine(); //Stores the next line of the file
            if (line.equals("----\t\t\t------")) { //Check we we have gotten past the header
                pastHeader = true;
                continue;
            } else if (!pastHeader || line.equals("")) { //Skips blank lines
                continue;
            } else if (line.equals("-----------------------------------------------------------------------------")) {
                //Stops when we reach the footer
                break;
            }

            if (line.indexOf("\t") == 0) {
                // We just have a movie line;
                assert lastActor != null; //Make sure we found an actor for the movie
                parseMovie(line.trim(), lastActor); //Parse the movie line
            } else {
                //We have an actor line
                if (lastActor != null && lastActor.getNeighbors().size() == 0) {
                    //Remove the last actor found if no movies we assigned to them (they had all TV movies/shows)
                    ActorMap.getInstance().removeActorWithNoMovies(lastActor);
                }
                lastActor = parseActor(line.substring(0, line.indexOf("\t"))); //Parse the actor in the line
                parseMovie(line.substring(line.indexOf("\t")).trim(), lastActor); //Parse the movie in the line
            }
        }
    }

    /**
     * Parse a movie line
     * @param movieLine The line to parse
     * @param actor The actor associated with the movie
     */
    private void parseMovie(final String movieLine, final ActorNode actor) {
        if (movieLine.contains("(TV)") ||
                movieLine.substring(0, movieLine.indexOf("(")).trim().matches("\"(?:[^\"\\\\]|\\\\.)*\"")) {
            //Ignore line if it is a TV show or TV Movie
            return;
        }
        //Removes everything past the date from the line
        final String prunedLine = movieLine.substring(0, movieLine.indexOf(")") + 1);
        if (MovieMap.getInstance().contains(prunedLine)) {
            //Handle the case where we already found the movie
            actor.addMovie(MovieMap.getInstance().getMovie(prunedLine));
            MovieMap.getInstance().getMovie(prunedLine).addActor(actor);
        } else {
            //Handle the case where we have not found the movie yet
            final MovieNode movie = new MovieNode(prunedLine);
            actor.addMovie(movie);
            movie.addActor(actor);
            MovieMap.getInstance().addMovie(movie);
        }
    }

    /**
     * Parses an actor line
     * @param actorLine The line to parse
     * @return The actor that was parsed
     */
    private ActorNode parseActor(final String actorLine) {
        final ActorNode actor = new ActorNode(actorLine);
        ActorMap.getInstance().addActor(actor);
        return actor;
    }

    @Override
    public Collection<? extends Node> getActors() {
        return ActorMap.getInstance().getActorMap().values();
    }

    @Override
    public Collection<? extends Node> getMovies() {
        return MovieMap.getInstance().getMovieMap().values();
    }

    @Override
    public Node getMovie(String name) {
        return MovieMap.getInstance().getMovie(name);
    }

    @Override
    public Node getActor(String name) {
        return ActorMap.getInstance().getActor(name);
    }
}
