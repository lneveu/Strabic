package master;

import articleGeneration.GenArticles;
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
        // generate graphs (saisons, auteurs, keyword)
        GenGraphs.execute(false);

        // apply algorithm layout and generate HTML file for each graph
        GenMaps.execute();

        // generate HTML file for earch article
        //GenArticles.execute();

        // frame test
        new UITest(GenGraphs.getSaisonGraph());
    }
}
