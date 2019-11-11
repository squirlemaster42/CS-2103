import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class IMDBGraphImpl implements IMDBGraph{

    IMDBGraphImpl(final String actorPath, final String actressPath) throws IOException {
        final Scanner actorScanner = new Scanner(new File(actorPath), StandardCharsets.ISO_8859_1);
        final Scanner actressScanner = new Scanner(new File(actressPath), StandardCharsets.ISO_8859_1);
        load(actorScanner);
        load(actressScanner);
    }

    //TODO need to remove actors/actresses who only are on tv
    private void load(final Scanner file){
        boolean pastHeader = false;
        ActorNode lastActor = null;
        while(file.hasNextLine()){
            String line = file.nextLine();
            if(line.equals("----\t\t\t------")){
                pastHeader = true;
                continue;
            }else if(!pastHeader || line.equals("")){
                continue;
            }

            if(line.indexOf("\t") == 0){
                // We just have a movie line;
                //We need to figure out who was the last actor so we can add to that node
                assert lastActor != null;
                parseMovie(line.trim(), lastActor);
            } else {
                //We have an actor line
                //TODO Check if last actor has enough movies
                lastActor = parseActor(line.substring(0, line.indexOf("\t")));
                parseMovie(line.substring(line.indexOf("\t")).trim(), lastActor);
            }
        }
    }

    //TODO Make this nicer
    private void parseMovie(final String movieLine, final ActorNode actor){
        if(movieLine.contains("(TV)") || movieLine.substring(0, movieLine.indexOf("(")).trim().matches("\"(?:[^\"\\\\]|\\\\.)*\"")){
            return; //TODO Test
        }
        final String prunedLine = movieLine.substring(0, movieLine.indexOf(")") + 1);
        if(MovieMap.getInstance().contains(prunedLine)){
            actor.addMovie(MovieMap.getInstance().getMovie(prunedLine));
            MovieMap.getInstance().getMovie(prunedLine).addActor(actor);
        }else{
            final MovieNode movie = new MovieNode(prunedLine, new ArrayList<>());
            actor.addMovie(movie);
            movie.addActor(actor);
            MovieMap.getInstance().addMovie(movie);
        }
    }

    private ActorNode parseActor(final String actorLine){
        final ActorNode actor = new ActorNode(actorLine, new ArrayList<>());
        ActorMap.getInstance().addActor(actor);
        return actor;
    }

    @Override
    public Collection<? extends Node> getActors() {
        return null;
    }

    @Override
    public Collection<? extends Node> getMovies() {
        return null;
    }

    @Override
    public Node getMovie(String name) {
        return null;
    }

    @Override
    public Node getActor(String name) {
        return null;
    }
}
