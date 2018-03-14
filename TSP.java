package assign3;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class. Contains the startSearch() method which takes the search
 * parameters from the GUI and creates (and subsequently starts) the threads.
 * Also contains reference to the GUI as well as a volatile variable to store
 * the global best length.
 *
 * @author Aiden Osipenko
 */
public class TSP {

    static int numVertices; //Number of vertices 
    public static GUI gui;
    static ArrayList<Thread> activeThreads;
    //Volatile variable so that each thread can access it
    public volatile static float globalBest;
    //If true, puts threads to sleep for 200ms after drawing to GUI. False 
    //results in no delay.
    public static boolean smoothAnimation;

    /**
     * Constructor.
     */
    public TSP() {
        gui = new GUI(); //Init GUI
    }

    /**
     * Method to begin a search. Called when the search button on the GUI is
     * clicked and all fields have values entered.
     *
     * @param filepath Filepath of the input file.
     * @param numThreads Number of threads to be used in the search.
     * @param numSearches Number of searches that each thread will perform.
     * @param numIterations Number of iterations that each search will have.
     * @param smoothAnim Boolean representing whether smooth animation is
     * enabled.
     * @throws IOException
     * @throws InterruptedException
     */
    public static void startSearch(String filepath, int numThreads,
            int numSearches, int numIterations, boolean smoothAnim)
            throws IOException, InterruptedException {
        //Load file given filepath
        Vertex[] initialPath = FileLoader.loadFile(filepath);
        //Initialize adjacency matrix
        AdjacencyMatrix.initMatrix(initialPath);
        numVertices = initialPath.length;
        globalBest = Float.MAX_VALUE;
        smoothAnimation = smoothAnim;
        activeThreads = new ArrayList<>();
        //For loop to create threads and start execution
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(new TSPSearch(initialPath, numSearches,
                    numIterations));
            activeThreads.add(t); //Add thread to list of active threads
            t.start(); //Start thread
        }
    }

    /**
     * Called by threads as they complete their search. Display a message to the
     * user when all threads have completed their search.
     *
     * @param completedThread A thread that has completed it's search.
     */
    public static void signalCompletion(Thread completedThread) {
        //Remove thread from list of active threads
        activeThreads.remove(completedThread);
        if (activeThreads.isEmpty()) {
            //Display message to user when all threads have completed search
            gui.userPrompt("All searches have completed. Global best length: "
                    + globalBest, "All searches complete", false);
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        TSP tsp = new TSP();
    }

}
