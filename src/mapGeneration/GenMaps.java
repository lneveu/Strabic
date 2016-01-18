package mapGeneration;

import graphGeneration.generation.GenGraphs;
import mapGeneration.html.HTMLBuildMap;
import mapGeneration.html.HTMLBuildMapImpl;
import mapGeneration.layout.FruchtermanReingold;
import mapGeneration.layout.LayoutAlgorithm;


/**
 * Apply layout algorithm on graphs and generate HTML view of maps
 * @author Loann Neveu
 */
public class GenMaps {

    public static void execute() {
        // apply algo
        LayoutAlgorithm algorithm = new FruchtermanReingold();
        algorithm.apply(GenGraphs.getSaisonGraph(), 200);
        //algorithm.apply(GenGraphs.getAuteurGraph(), 150);

        // generate html view
        HTMLBuildMap htmlBuilder = new HTMLBuildMapImpl();
        htmlBuilder.create(GenGraphs.getSaisonGraph(), "saisons.html");
    }
}
