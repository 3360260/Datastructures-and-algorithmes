import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;

public class FrontEndGUI extends JFrame implements ActionListener {

    private final JTextArea outputArea;
    private final DatasetProcessor<String, String> datasetProcessor;

    public FrontEndGUI() {
        setTitle("Algorithms Duration GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create DatasetProcessor instance with appropriate data structures
        LinkedListDS<String, String> linkedListDS = new LinkedListDS<>();
        ArrayListDS<String, String> arrayListDS = new ArrayListDS<>();
        TreeMapDS<String, String> treeMapDS = new TreeMapDS<>();
        datasetProcessor = new DatasetProcessor<>(linkedListDS, arrayListDS, treeMapDS);

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set 10px insets (gap)

        // "Read Books CSV File" button
        JButton readCSVFileButton = new JButton("Read Books CSV File");
        readCSVFileButton.addActionListener(this);
        mainPanel.add(readCSVFileButton, createGridBagConstraints(gbc, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL));

        // "Run Algorithm" button
        JButton runAlgorithmButton = new JButton("Run Algorithm");
        runAlgorithmButton.addActionListener(this);
        mainPanel.add(runAlgorithmButton, createGridBagConstraints(gbc, 2, 0, 1, 1, GridBagConstraints.HORIZONTAL));

        // "Select Data Structure" panel
        JPanel dataStructurePanel = createSelectionPanel("Select Data Structure",
                "LinkedList", "ArrayList", "TreeMap");
        mainPanel.add(dataStructurePanel, createGridBagConstraints(gbc, 0, 1, 1, 1, GridBagConstraints.BOTH));

        // "Select Algorithm" panel
        JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
                "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
        mainPanel.add(algorithmPanel, createGridBagConstraints(gbc, 2, 1, 1, 1, GridBagConstraints.BOTH));

        // Output area panel
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, createGridBagConstraints(gbc, 0, 2, 3, 1, GridBagConstraints.BOTH));

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSelectionPanel(String title, String... options) {
        JPanel panel = new JPanel(new GridLayout(options.length, 1));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        ButtonGroup buttonGroup = new ButtonGroup();
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setActionCommand(option);
            radioButton.addActionListener(this); // Register ActionListener
            buttonGroup.add(radioButton);
            panel.add(radioButton);
        }

        // Select the first option by default
        if (options.length > 0) {
            buttonGroup.getElements().nextElement().setSelected(true);
        }

        return panel;
    }

    private GridBagConstraints createGridBagConstraints(GridBagConstraints gbc, int x, int y, int width, int height, int fill) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = fill;
        gbc.weightx = 1.0; // Take up available horizontal space
        gbc.weighty = 1.0; // Take up available vertical space
        return gbc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Read Books CSV File")) {
            // Simulate reading books CSV file
            outputArea.setText("Reading Books CSV File...\n");
        } else if (command.equals("Run Algorithm")) {
            // Simulate executing algorithm based on selected options
            outputArea.setText("Running Algorithm...\n");
        } else {
            // Handle data structure and algorithm selections
            int dataStructureIndex = 0; // Example: 0 for LinkedList, 1 for ArrayList, 2 for TreeMap
            int algorithmIndex = 0; // Example: 0 for Sort by title, 1 for Search book by title, etc.

            if (command.equals("LinkedList")) {
                dataStructureIndex = 0;
            } else if (command.equals("ArrayList")) {
                dataStructureIndex = 1;
            } else if (command.equals("TreeMap")) {
                dataStructureIndex = 2;
            } else {
                // Handle algorithm selections
                // Example: implement logic based on selected algorithm
                switch (algorithmIndex) {
                    case 0: // Sort by title
                        List<String> sortedItems = datasetProcessor.sortItems(Comparator.naturalOrder(), dataStructureIndex);
                        outputArea.setText("Sorted items: " + sortedItems + "\n");
                        break;
                    case 1: // Search book by title
                        Optional<String> foundItem = datasetProcessor.searchItemByCriteria(item -> item.contains("title"), dataStructureIndex);
                        outputArea.setText("Found item: " + foundItem.orElse("Not found") + "\n");
                        break;
                    // Add cases for other algorithms...
                    default:
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrontEndGUI();
        });
    }
}
