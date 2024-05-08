import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrontEndGUI extends JFrame implements ActionListener {

    private final JTextArea outputArea;

    public FrontEndGUI() {
        setTitle("Algorithms Duration GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        selectDefaultRadioButton(dataStructurePanel, "LinkedList"); // Set default selection
        mainPanel.add(dataStructurePanel, createGridBagConstraints(gbc, 0, 1, 1, 1, GridBagConstraints.BOTH));

        // "Select Algorithm" panel
        JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
                "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
        selectDefaultRadioButton(algorithmPanel, "Sort by title"); // Set default selection
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
            buttonGroup.add(radioButton);
            panel.add(radioButton);
        }

        return panel;
    }

    private void selectDefaultRadioButton(JPanel panel, String defaultOption) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.getActionCommand().equals(defaultOption)) {
                    radioButton.setSelected(true);
                }
            }
        }
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
        if (e.getActionCommand().equals("Read Books CSV File")) {
            // Handle "Read Books CSV File" button click
            // Simulate loading data
            outputArea.append("Reading Books CSV File...\n");
        } else if (e.getActionCommand().equals("Run Algorithm")) {
            // Handle "Run Algorithm" button click
            // Simulate executing the algorithm
            outputArea.append("Running Algorithm...\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrontEndGUI();
        });
    }
}
