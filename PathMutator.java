package assign3;

import java.util.*;

/**
 * Class for mutating a path.
 *
 * @author Aiden Osipenko 
 */
public class PathMutator {

    /**
     * Mutates a path by swapping the indeces of 2-3 vertices.
     *
     * @param path
     * @return Mutated path.
     */
    public static Vertex[] mutate(Vertex[] path) {
        Vertex[] mutatedPath = path.clone();
        //Create random generator
        Random rand = new Random();
        //Get first index 
        int ind1 = rand.nextInt(TSP.numVertices);
        //Get second index
        int ind2 = rand.nextInt(TSP.numVertices);
        //Ensure indexes are distinct
        while (ind1 == ind2) {
            ind2 = rand.nextInt(TSP.numVertices);
        }
        //Get third index
        int ind3 = rand.nextInt(TSP.numVertices);
        //Ensure indexes are distinct
        while (ind1 == ind3 || ind2 == ind3) {
            ind3 = rand.nextInt(TSP.numVertices);
        }
        //Five different ways to swap the three vertices
        int swap = rand.nextInt(5);
        switch (swap) {
            case 0: //2 1 3 
                swap(mutatedPath, ind2, ind1);
                break;
            case 1: //1 3 2
                swap(mutatedPath, ind2, ind3);
                break;
            case 2: //3 2 1
                swap(mutatedPath, ind1, ind3);
                break;
            case 3: //2 3 1 
                swap(mutatedPath, ind1, ind2);
                swap(mutatedPath, ind2, ind3);
                break;
            case 4: //3 1 2
                swap(mutatedPath, ind2, ind3);
                swap(mutatedPath, ind1, ind3);
                break;
        }
        return mutatedPath;
    }

    /**
     * Swap method used to swap the index of two vertices.
     *
     * @param path Array of vertices.
     * @param i Index 1
     * @param c Index 2
     */
    private static void swap(Vertex[] path, int i, int c) {
        Vertex temp = path[i]; //Put one of the vertices in temp
        path[i] = path[c]; //Set other vertex to temp's index
        path[c] = temp;
    }

}
