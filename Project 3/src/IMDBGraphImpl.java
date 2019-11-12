import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

public class IMDBGraphImpl implements IMDBGraph {

    //TODO Look up HashSet for node neighbors and maybe to replace HashMap in MovieMap and ActorMap
    //TODO Throw exceptions
    //TODO Make stuff thread-safe
    //TODO Check that names are being loaded correctly
    IMDBGraphImpl(final String actorPath, final String actressPath) throws IOException {
        final Scanner actorScanner = new Scanner(new File(actorPath), StandardCharsets.ISO_8859_1);
        final Scanner actressScanner = new Scanner(new File(actressPath), StandardCharsets.ISO_8859_1);
        load(actorScanner);
        load(actressScanner);
        System.out.println("Actors: " + ActorMap.getInstance().size());
        System.out.println("Movies: " + MovieMap.getInstance().size());
    }

    private void load(final Scanner file) {
        boolean pastHeader = false;
        ActorNode lastActor = null;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            if (line.equals("----\t\t\t------")) {
                pastHeader = true;
                continue;
            } else if (!pastHeader || line.equals("")) {
                continue;
            } else if (line.equals("-----------------------------------------------------------------------------")) {
                break;
            }

            if (line.indexOf("\t") == 0) {
                // We just have a movie line;
                //We need to figure out who was the last actor so we can add to that node
                assert lastActor != null;
                parseMovie(line.trim(), lastActor);
            } else {
                //We have an actor line
                if (lastActor != null && lastActor.getNeighbors().size() == 0) {
                    ActorMap.getInstance().removeActorWithNoMovies(lastActor);
                }
                lastActor = parseActor(line.substring(0, line.indexOf("\t")));
                parseMovie(line.substring(line.indexOf("\t")).trim(), lastActor);
            }
        }
    }

    //TODO Make this nicer
    private void parseMovie(final String movieLine, final ActorNode actor) {
        if (movieLine.contains("(TV)") ||
                movieLine.substring(0, movieLine.indexOf("(")).trim().matches("\"(?:[^\"\\\\]|\\\\.)*\"")) {
            return;
        }
        final String prunedLine = movieLine.substring(0, movieLine.indexOf(")") + 1);
        if (MovieMap.getInstance().contains(prunedLine)) {
            actor.addMovie(MovieMap.getInstance().getMovie(prunedLine));
            MovieMap.getInstance().getMovie(prunedLine).addActor(actor);
        } else {
            final MovieNode movie = new MovieNode(prunedLine);
            actor.addMovie(movie);
            movie.addActor(actor);
            MovieMap.getInstance().addMovie(movie);
        }
    }

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
