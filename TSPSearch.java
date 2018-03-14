package assign3;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Threaded class for running the search algorithm on a starting path.
 *
 * @author Aiden Osipenko
 */
public class TSPSearch implements Runnable {

    Vertex[] initialPath; //Initial path (as read in from input file)
    int numSearches; //Number of searches that each thread is to perform
    int numIterations; //Number of iterations in each search

    /**
     * Constructor.
     *
     * @param path Array of vertices in it's starting configuration (ie, in the
     * order they are read in from the input file).
     * @param numSearches Number of searches to perform.
     * @param numIterations Number of iterations to run.
     */
    public TSPSearch(Vertex[] path, int numSearches, int numIterations) {
        this.initialPath = new Vertex[path.length];
        this.initialPath = path.clone(); //Make initialPath a deep copy of path
        this.numSearches = numSearches;
        this.numIterations = numIterations;
    }

    /**
     * Run method. Calls search().
     */
    @Override
    public void run() {
        search();
    }

    /**
     * Main search method. Runs numIterations number of times, and returns the
     * path with the shortest overall length.
     */
    public void search() {
        Vertex[] currentBestPath;
        for (int s = 0; s < numSearches; s++) {
            currentBestPath = shuffle(); //Shuffle the inital path
            //Find distance of initial (shuffled) path
            float localBest = AdjacencyMatrix.totalDistanceOf(currentBestPath);
            Vertex[] mutatedPath;
            float mutatedPathDistance;
            for (int n = 0; n < numIterations; n++) {
                //Get the mutated path for this iteration
                mutatedPath = PathMutator.mutate(currentBestPath);
                //Get distance of mutated path
                mutatedPathDistance = AdjacencyMatrix
                        .totalDistanceOf(mutatedPath);
                //Check if mutated path is shorter than the current path
                if (mutatedPathDistance < localBest) {
                    currentBestPath = mutatedPath;
                    localBest = mutatedPathDistance;
                    //Check if new local best is better than global best
                    if (localBest < TSP.globalBest) {
                        TSP.globalBest = localBest;
                        TSP.gui.setBest(localBest, currentBestPath);
                        //Slows down threads drawing to JPanel which results in 
                        //a smooth animation
                        if (TSP.smoothAnimation) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(TSPSearch.class.getName())
                                        .log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
        //Signal completion to main class
        TSP.signalCompletion(Thread.currentThread());
    }

    /**
     * Fisher-Yates shuffle algorithm to shuffle the array.
     *
     * @return Shuffled path.
     */
    public Vertex[] shuffle() {
        int size = initialPath.length;
        Random rand = new Random();
        Vertex[] shuffledPath;
        shuffledPath = initialPath.clone();
        for (int i = 0; i < size; i++) { //Use i as one index
            //Get second index between i and size - 1
            int c = i + rand.nextInt(size - i);
            swap(shuffledPath, i, c); //Swap the vertices at the indexes
        }
        return shuffledPath;
    }

    /**
     * Swap method used to swap the index of two vertices.
     *
     * @param path
     * @param i Index 1
     * @param c Index 2
     */
    public void swap(Vertex[] path, int i, int c) {
        Vertex temp = path[i]; //Put one of the vertices in temp
        path[i] = path[c]; //Set other vertex to temp's index
        path[c] = temp;
    }

}
