package assign3;

/**
 * Class to represent a vertex.
 *
 * @author Aiden Osipenko
 */
public class Vertex {

    private final int index; //Index in adjacency matrix
    private final float x; //X coordinate
    private final float y; //Y coordinate

    public Vertex(int index, float x, float y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns this vertex's index in the adjacency matrix.
     *
     * @return int Index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Returns this vertex's X coordinate.
     *
     * @return float X
     */
    public float getX() {
        return this.x;
    }

    /**
     * Returns this vertex's Y coordinate.
     *
     * @return float Y
     */
    public float getY() {
        return this.y;
    }

    /**
     * To string method which prints the x and y coordinates of a vertex.
     *
     * @return String
     */
    @Override
    public String toString() {
        String s = this.x + ", " + this.y;
        return s;
    }

    /**
     * Deep cloning method.
     *
     * @return Deep clone of this instance of Vertex.
     */
    @Override
    public Vertex clone() {
        return new Vertex(this.index, this.x, this.y);
    }

}
