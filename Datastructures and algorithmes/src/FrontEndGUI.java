import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrontEndGUI extends JFrame implements ActionListener {

    private final JTextArea outputArea;
    private final JButton runButton;

    public FrontEndGUI() {
        setTitle("Algorithms Duration GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // "Read Books CSV File" button
        JButton readCSVFile = new JButton("Read Books CSV File");
        readCSVFile.addActionListener(e -> {
            readCSVFile.setEnabled(false);
            // Simulate loading data
            SwingUtilities.invokeLater(() -> {
                readCSVFile.setEnabled(true);
                readCSVFile.setBackground(Color.decode("#C1C7C8"));
            });
        });
        readCSVFile.setBackground(Color.decode("#C1C7C8"));
        readCSVFile.setForeground(Color.BLACK);
        Font windows95StyleFont = new Font("Microsoft Sans Serif", Font.BOLD, 18);
        readCSVFile.setFont(windows95StyleFont);
        add(readCSVFile, BorderLayout.NORTH);

        // Output area panel
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
        if (e.getSource() == runButton) {
            runButton.setEnabled(false);
            // Simulate executing the algorithm
            SwingUtilities.invokeLater(() -> {
                runButton.setEnabled(true);
                outputArea.append("Algorithm executed!\n");
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrontEndGUI();
        });
    }
}
