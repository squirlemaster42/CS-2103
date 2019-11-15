import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void testFindsEndOfHeader(){
        try {
            IMDBGraph graph = new IMDBGraphImpl("actors_first_10000_lines.list", "actresses_first_10000_lines.list");
            //ActorNode actor1 = new ActorNode("Actor1");
            //MovieNode movie1 = new MovieNode("Movie1");
            //actor1.addMovie(movie1);
            //movie1.addActor(actor1);
            //assertEquals(actor1.getName(), ActorMap.getInstance().getActor("Actor1").getName());
            //System.out.println(ActorMap.getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO make this not an aids name
    @Test
    public void testingStuff(){
        try{
            IMDBGraph graph = new IMDBGraphImpl("actors_test.list", "actresses.list");

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
