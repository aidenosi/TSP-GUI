package assign3;

import static java.lang.Math.sqrt;

/**
 * Class to contain adjacency matrix array and methods that require access to
 * the adjacency matrix.
 *
 * @author Aiden Osipenko
 */
public class AdjacencyMatrix {

    private static float[][] adjMatrix; //Array to represent adjacency matrix

    /**
     * Method to initialize the adjacency matrix.
     *
     * @param vertices Array containing all vertices.
     */
    public static void initMatrix(Vertex[] vertices) {
        //Init adjMatrix
        adjMatrix = new float[vertices.length][vertices.length];
        //Nested for loop to populate the adjacency matrix with the distances
        //between vertices
        for (int col = 0; col < vertices.length; col++) {
            for (int row = 0; row < vertices.length; row++) {
                adjMatrix[col][row] = distanceBetween(vertices[col],
                        vertices[row]);
            }
        }
    }

    /**
     * Method to find the total distance of a path (represented by a Vertex[]).
     *
     * @param path Vertex[] that represents the path.
     * @return float The total distance of the path.
     */
    public static float totalDistanceOf(Vertex[] path) {
        float totalDistance = 0; //Total distance
        int current, next; //Indexes of current and next vertex
        for (int i = 0; i < path.length; i++) {
            current = path[i].getIndex(); //Get current vertex
            if (i + 1 == path.length) { //Current is last vertex
                next = path[0].getIndex(); //Next vertex is first
            } else {
                next = path[i + 1].getIndex(); //Next is following vertex
            }
            //Add distance between current and next to total
            totalDistance += adjMatrix[current][next]; 
        }
        return totalDistance;
    }

    /**
     * Method to find the Euclidean distance between two vertices. Used for 
     * initialization of the adjacency matrix.
     *
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @return float The Euclidean distance between the two vertices.
     */
    public static float distanceBetween(Vertex v1, Vertex v2) {
        float x1 = v1.getX(); //Put coordinates of vertices into variables (for 
        float y1 = v1.getY(); //readability's sake
        float x2 = v2.getX();
        float y2 = v2.getY();
        return (float) sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

}
