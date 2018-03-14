package assign3;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Class for main JFrame of the GUI, which includes labels and text fields for
 * search parameters.
 *
 * @author Aiden Osipenko
 */
public class GUI extends JFrame {

    //Declaring fields and labels for property attributes
    private JTextField filepathField;
    private Label filepathLabel;
    private JTextField numThreadsField;
    private Label numThreadsLabel;
    private JTextField numSearchesField;
    private Label numSearchesLabel;
    private JTextField numIterationsField;
    private Label numIterationsLabel;
    private JTextField bestLengthField;
    private Label bestLengthLabel;
    private JRadioButton smoothEnabled;
    private JRadioButton smoothDisabled;
    private Label smoothAnimationLabel;
    private JButton searchButton;
    private PathPanel pathPanel;

    /**
     * Constructor. Calls initComponents().
     */
    public GUI() {
        initComponents();
    }

    /**
     * Builds GUI.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setTitle("Travelling Salesman"); //Set title of JFrame
        setLayout(new BorderLayout()); //Use border layout
        //Exit program on GUI close
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //Panel for components that the user interacts with
        JPanel UIPanel = new JPanel();
        //Init and adding labels and text fields to UIPanel
        filepathLabel = new Label("Filepath:", Label.RIGHT);
        UIPanel.add(filepathLabel);
        filepathField = new JTextField(20);
        UIPanel.add(filepathField);

        numThreadsLabel = new Label("# of threads:", Label.RIGHT);
        UIPanel.add(numThreadsLabel);
        numThreadsField = new JTextField(10);
        UIPanel.add(numThreadsField);

        numSearchesLabel = new Label("# of searches:", Label.RIGHT);
        UIPanel.add(numSearchesLabel);
        numSearchesField = new JTextField(10);
        UIPanel.add(numSearchesField);

        numIterationsLabel = new Label("# of iterations:", Label.RIGHT);
        UIPanel.add(numIterationsLabel);
        numIterationsField = new JTextField(10);
        UIPanel.add(numIterationsField);

        //Init and adding radio buttons (incl label) to UIPanel
        ButtonGroup smoothAnimationButtons = new ButtonGroup();
        smoothAnimationLabel = new Label("Smooth animation:", Label.RIGHT);
        UIPanel.add(smoothAnimationLabel);
        smoothEnabled = new JRadioButton("Enabled", true);
        smoothAnimationButtons.add(smoothEnabled);
        UIPanel.add(smoothEnabled);
        smoothDisabled = new JRadioButton("Disabled", false);
        smoothAnimationButtons.add(smoothDisabled);
        UIPanel.add(smoothDisabled);

        //Init and add search button
        searchButton = new JButton("Search");
        UIPanel.add(searchButton);
        //Adding action listener to search button
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    searchButtonActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        });

        JPanel bestPanel = new JPanel(); //Panel for best length 
        //Init and add best length label and field
        bestLengthLabel = new Label("Best length:");
        bestPanel.add(bestLengthLabel);
        bestLengthField = new JTextField(20);
        bestLengthField.setEditable(false);
        //Add to panel
        bestPanel.add(bestLengthField);

        //Init pathPanel
        pathPanel = new PathPanel();

        //Add panels to JFrame
        add(UIPanel, BorderLayout.NORTH);
        add(bestPanel, BorderLayout.CENTER);
        add(pathPanel, BorderLayout.SOUTH);

        pack(); //Pack components
        this.setVisible(true); //Set JFrame to be visible
    }

    /**
     * Displays a prompt containing a message to the user.
     *
     * @param message Message to write in prompt.
     * @param title Title of the prompt.
     * @param error Signifies whether the prompt should be an error prompt.
     */
    public void userPrompt(String message, String title, boolean error) {
        if (error) { //Show error message
            JOptionPane.showMessageDialog(null, message, title,
                    JOptionPane.ERROR_MESSAGE);
        } else { //Show information message
            JOptionPane.showMessageDialog(null, message, title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Method to display the current best path, as well as it's length, on the
     * GUI.
     *
     * @param bestLength Length of current best path.
     * @param path Vertex[] representing the current best path.
     */
    public synchronized void setBest(float bestLength, Vertex[] path) {
        bestLengthField.setText("" + bestLength); //Set text field
        pathPanel.setPath(path); //Pass path to PathPanel class
        pathPanel.repaint(); //Draw new best path
    }

    /**
     * Checks that all fields have values entered in them, then passes those
     * values to the startSearch method in the main class to begin a search with
     * the specified parameters.
     *
     * @param evt
     */
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt)
            throws IOException {
        int numThreads, numSearches, numIterations;
        boolean smoothAnimation;
        //Check that each field has some value entered in the textfield
        if (filepathField.getText().isEmpty()
                || numThreadsField.getText().isEmpty()
                || numSearchesField.getText().isEmpty()
                || numIterationsField.getText().isEmpty()) {
            userPrompt("Please ensure you have entered values in all fields.",
                    "Input Error", true);
        } else {
            String filepath = filepathField.getText(); //Get filepath
            try { //Ensure that an int has been entered in numThreadsField
                numThreads = Integer.parseInt(numThreadsField.getText());
            } catch (NumberFormatException e) {
                //Display error message to user
                userPrompt("Invalid input for # of threads.", "Input Error",
                        true);
                numThreads = 0; //Avoids possible issues with initialization
            }
            try { //Ensure that an int has been entered in numSearchesField
                numSearches = Integer.parseInt(numSearchesField.getText());
            } catch (NumberFormatException e) {
                //Display error message to user
                userPrompt("Invalid input for # of searches.", "Input Error",
                        true);
                numSearches = 0; //Avoids possible issues with initialization
            }
            try { //Ensure that an int has been entered in numIterationsField
                numIterations = Integer.parseInt(numIterationsField.getText());
            } catch (NumberFormatException e) {
                //Display error message to user
                userPrompt("Invalid input for # of iterations.", "Input Error",
                        true);
                numIterations = 0; //Avoids possible issues with initialization
            }
            //Check if user has enabled or disabled smooth animation
            smoothAnimation = smoothEnabled.isSelected();
            try { //Start search
                TSP.startSearch(filepath, numThreads, numSearches,
                        numIterations, smoothAnimation);
            } catch (InterruptedException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        }
    }
}
