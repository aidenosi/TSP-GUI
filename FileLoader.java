package assign3;

import java.io.*;

/**
 * This class handles file input for the program.
 *
 * @author Aiden Osipenko
 */
public class FileLoader {

    /**
     * Prompts user to select input file, then loads data from input file one
     * line at a time into a String. This string is then used to create the
     * Vertex objects, which are placed into an array and returned.
     *
     * @param filepath Filepath of input file.
     * @return Vertex[] to represent the path (as read in from the input file).
     * @throws IOException
     */
    public static Vertex[] loadFile(String filepath) throws IOException {
        Vertex[] vertices;
        FileReader fReader = null;
        BufferedReader bReader = null;
        try { //Load file
            fReader = new FileReader(filepath); //Init file and buffered readers
            bReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            TSP.gui.userPrompt("Error: File not found.", "File not found", true);
            throw new IOException("Error: File not found.");
        }
        int numVertices = Integer.parseInt(bReader.readLine());
        vertices = new Vertex[numVertices];
        //Iterate over input data until program has read numVertices coordinates
        //or EOF.
        for (int i = 0; i < vertices.length; i++) {
            String s = bReader.readLine(); //Read next line
            if (s == null) { //Break if EOF
                break;
            }
            //Convert string to string array
            String[] strArray = s.split("\\s");
            //Parse values to appropriate type
            int index = Integer.parseInt(strArray[0]) - 1;
            float x = Float.parseFloat(strArray[1]);
            float y = Float.parseFloat(strArray[2]);
            vertices[i] = new Vertex(index, x, y); //Add vertex to array
        }
        bReader.close(); //Closing file and buffered readers
        fReader.close();
        return vertices;
    }
}
