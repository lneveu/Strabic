package mapGeneration;

import graphGeneration.generation.GenGraphs;
import mapGeneration.layout.FruchtermanReingold;
import mapGeneration.layout.LayoutAlgorithm;


/**
 * Apply layout algorithm on graphs and generate HTML view of maps
 * @author Loann Neveu
 */
public class GenMaps {

    public static void execute() {
        LayoutAlgorithm algorithm = new FruchtermanReingold();
        algorithm.apply(GenGraphs.getSaisonGraph(), 200);
    }
}
