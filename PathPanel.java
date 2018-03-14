package assign3;

import java.awt.*;
import javax.swing.JPanel;

/**
 * JPanel class that draws the current best path.
 *
 * @author Aiden Osipenko
 */
public class PathPanel extends JPanel {

    private Vertex[] path; //Path to be drawn

    /**
     * Constructor. Sets preferred size and background colour of panel.
     */
    public PathPanel() {
        setPreferredSize(new Dimension(750, 750));
        setBackground(Color.WHITE);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (path != null) { //Don't try to draw a null path. Bad things happen.
            for (int i = 0; i < path.length; i++) {
                //Draw a line from current vertex to next one
                if (i + 1 == path.length) {
                    g.fillOval((int) path[i].getX() / 2, (int) path[i].getY() / 2, 4, 4);
                    g.drawLine(((int) path[i].getX() / 2) + 2, ((int) path[i].getY() / 2) + 2,
                            ((int) path[0].getX() / 2) + 2, ((int) path[0].getY() / 2) + 2);
                } else {
                    g.fillOval((int) path[i].getX() / 2, (int) path[i].getY() / 2, 4, 4);
                    g.drawLine(((int) path[i].getX() / 2) + 2, ((int) path[i].getY() / 2) + 2,
                            ((int) path[i + 1].getX() / 2) + 2, ((int) path[i + 1].getY() / 2) + 2);
                }
            }
        }
    }

    /**
     * Method for updating the path that is to be drawn.
     *
     * @param path The new path to be used when repaint() is called.
     */
    public void setPath(Vertex[] path) {
        this.path = path;
    }

}
