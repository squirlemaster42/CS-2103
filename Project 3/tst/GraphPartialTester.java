import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

/**
 * Code to test Project 3; you should definitely add more tests!
 */
public class GraphPartialTester {
	IMDBGraph imdbGraph;
	GraphSearchEngine searchEngine;

	/**
	 * Verifies that there is no shortest path between a specific and actor and actress.
	 */
	@Test(timeout=5000)
	public void findShortestPath () throws IOException {
		imdbGraph = new IMDBGraphImpl("actors_test.list", "actresses_test.list");
		final Node actor1 = imdbGraph.getActor("Actor1");
		final Node actress2 = imdbGraph.getActor("Actress2");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actress2);
		assertNull(shortestPath);  // there is no path between these people
	}

	@Test(timeout = 5000)
	public void findingShortPath() throws IOException{
		imdbGraph = new IMDBGraphImpl("actors_test.list", "actresses_test.list");
		final Node actor1 = imdbGraph.getActor("Actor1");
		final Node actress1 = imdbGraph.getActor("Actress1");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1,actress1);
		final ArrayList<Node> list = new ArrayList<>();
		list.add(ActorMap.getInstance().getActor("Actor1"));
		list.add(MovieMap.getInstance().getMovie("Movie1 (2001)"));
		list.add(ActorMap.getInstance().getActor("Actress1"));
		assertEquals(list, shortestPath);
	}
	@Before
	/**
	 * Instantiates the graph
	 */
	public void setUp () throws IOException {
		imdbGraph = new IMDBGraphImpl("actors_test.list", "actresses_test.list");
		searchEngine = new GraphSearchEngineImpl();
	}

	@Test
	/**
	 * Just verifies that the graphs could be instantiated without crashing.
	 */
	public void finishedLoading () {
		assertTrue(true);
		// Yay! We didn't crash
	}

	@Test
	/**
	 * Verifies that a specific movie has been parsed.
	 */
	public void testSpecificMovie () {
		testFindNode(imdbGraph.getMovies(), "Movie1 (2001)");
	}

	@Test
	/**
	 * Verifies that a specific actress has been parsed.
	 */
	public void testSpecificActress () {
		testFindNode(imdbGraph.getActors(), "Actress2");
	}

	/**
	 * Verifies that the specific graph contains a node with the specified name
	 * @param graph the IMDBGraph to search for the node
	 * @param name the name of the Node
	 */
	private static void testFindNode (Collection<? extends Node> nodes, String name) {
		boolean found = false;
		for (Node node : nodes) {
			if (node.getName().trim().equals(name)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	/**
	 * Tests that we do not find a path when there are no connections
	 */
	@Test
	public void testSearch(){
		try{
			IMDBGraph graph = new IMDBGraphImpl("actors_test.list", "actresses_test.list");
			ActorNode act1 = ActorMap.getInstance().getActor("A1");
			ActorNode act2 = ActorMap.getInstance().getActor("A15");
			GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
			assertNull(searchEngine.findShortestPath(act1, act2));
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
