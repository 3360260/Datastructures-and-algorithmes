import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FrontEndGUI extends JFrame implements ActionListener {

    private final JTextArea outputArea;
    private final JButton readCSVFileButton;
    private final JButton runButton;
    private final JTable dataTable;

    public FrontEndGUI() {
        setTitle("Algorithms Duration GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // "Read Books CSV File" button
        readCSVFileButton = new JButton("Read Books CSV File");
        readCSVFileButton.addActionListener(this);
        readCSVFileButton.setBackground(Color.decode("#C1C7C8"));
        readCSVFileButton.setForeground(Color.BLACK);
        Font buttonFont = new Font("Microsoft Sans Serif", Font.BOLD, 18);
        readCSVFileButton.setFont(buttonFont);
        add(readCSVFileButton, BorderLayout.NORTH);

        // Output area panel for displaying messages
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(outputPanel, BorderLayout.CENTER);

        // Panel for radio button selections (data structure and algorithm)
        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
        JPanel dataStructurePanel = createSelectionPanel("Select Data Structure",
                "LinkedList", "ArrayList", "TreeMap");
        JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
                "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
        selectionPanel.add(dataStructurePanel);
        selectionPanel.add(algorithmPanel);
        add(selectionPanel, BorderLayout.SOUTH);

        // "Run Algorithm" button
        runButton = new JButton("Run Algorithm");
        runButton.addActionListener(this);
        runButton.setBackground(Color.decode("#C1C7C8"));
        add(runButton, BorderLayout.EAST);

        // Table to display CSV data
        dataTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        outputPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Set preferred sizes to maintain layout
        outputPanel.setPreferredSize(new Dimension(800, 400));
        selectionPanel.setPreferredSize(new Dimension(800, 200));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSelectionPanel(String title, String... options) {
        JPanel panel = new JPanel(new GridLayout(options.length + 1, 1));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setActionCommand(option);
            buttonGroup.add(radioButton);
            panel.add(radioButton);
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == readCSVFileButton) {
            // Prompt user to select CSV file
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Read and display CSV data
                String csvFilePath = fileChooser.getSelectedFile().getPath();
                displayCSVData(csvFilePath);
            }
        } else if (e.getSource() == runButton) {
            // Simulate executing the algorithm
            outputArea.append("Algorithm executed!\n");
        }
    }

    private void displayCSVData(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            DefaultTableModel tableModel = new DefaultTableModel();

            // Read the first line as table headers
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");
                for (String header : headers) {
                    tableModel.addColumn(header.trim());
                }
            }

            // Read remaining lines as table rows
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }

            // Set table model to display in JTable
            dataTable.setModel(tableModel);

            // Display success message
            outputArea.setText("CSV file loaded successfully!\n");
        } catch (IOException ex) {
            outputArea.setText("Error reading CSV file: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrontEndGUI::new);
    }
}
