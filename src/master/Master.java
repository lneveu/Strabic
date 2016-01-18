package master;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import elements.LayoutData;
import graphGeneration.generation.GenGraphs;

import mapGeneration.GenMaps;
import java.io.IOException;

/**
 * MASTER CLASS - Entry point of the program
 */
public class Master {

    public static void main(String[] args) throws IOException {
        // generate graphs
        GenGraphs.execute();

        // algo layout
        GenMaps.execute();

        Graph g = GenGraphs.getSaisonGraph();
        // frame test
        new UITest(g);
        /*for(Vertex i : g.getVertices()) {
            LayoutData d = (LayoutData) i.getProperty("layout_data");
            System.out.println("x: " + d.getX() + " - y: " + d.getY());
        }*/


        /*
        for(Vertex i : g.getVertices()) {
            for(String k : i.getPropertyKeys()) {
                System.out.println(i.getProperty(k));
            }
            System.out.println("\n");
        }
        */

    }
}
