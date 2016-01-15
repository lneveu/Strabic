package master;

import graphGeneration.generation.GenGraphs;

import java.io.IOException;

/**
 * MASTER CLASS - Entry point of the program
 */
public class Master {

    public static void main(String[] args) throws IOException {
        // generate graphs
        GenGraphs.execute();
    }
}
