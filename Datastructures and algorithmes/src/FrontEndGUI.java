import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class FrontEndGUI extends JFrame implements ActionListener {

        private DatasetProcessor<String, Book> bookProcessor;
        private final JTextArea outputArea;
        private final JButton runButton;

        public FrontEndGUI() {
            setTitle("Algorithms Duration GUI");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JButton ReadCSVFile = new JButton("Read Books CSV File");
            ReadCSVFile.addActionListener(e -> {
                ReadCSVFile.setEnabled(false);
                List<Book> booksData = AlgorithmWorker.loadBooksData("Dataset/books.csv");
                ReadCSVFile.setEnabled(true);
                ReadCSVFile.setBackground(Color.decode("#C1C7C8"));
            });
            ReadCSVFile.setBackground(Color.decode("#C1C7C8"));
            ReadCSVFile.setForeground(Color.black);


            Font Windows95StyleFont = new Font("Microsoft sans-serif", Font.BOLD, 18);

            ReadCSVFile.setFont(Windows95StyleFont);
            add(ReadCSVFile, BorderLayout.NORTH);

            outputArea = new JTextArea();
            outputArea.setEditable(false);
            add(new JScrollPane(outputArea), BorderLayout.CENTER);

            JPanel selectionPanel = new JPanel(new GridLayout(1, 2));

            JPanel dataStructurePanel = createSelectionPanel("Select Data Structure",
                    "LinkedList", "ArrayList", "TreeMap");
            selectionPanel.add(dataStructurePanel);

            JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
                    "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
            selectionPanel.add(algorithmPanel);

            add(selectionPanel, BorderLayout.SOUTH);

            runButton = new JButton("Run Algorithm");
            runButton.addActionListener(this);
            runButton.setBackground(Color.decode("#273584"));
            add(runButton, BorderLayout.EAST);
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

                int dataStructureIndex = getSelectedIndexFromPanel((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0));

                int algorithmIndex = getSelectedIndexFromPanel((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(1));

                AlgorithmWorker worker = new AlgorithmWorker(dataStructureIndex, algorithmIndex);
                worker.execute();
            }
        }

        private int getSelectedIndexFromPanel(JPanel panel) {
            Component[] components = panel.getComponents();
            for (int i = 1; i < components.length; i++) {
                JRadioButton radioButton = (JRadioButton) components[i];
                if (radioButton.isSelected()) {
                    return i - 1;
                }
            }
            return -1;
        }

        private static class AlgorithmWorker extends SwingWorker<Void, String> {
            private final int dataStructureIndex;
            private final int algorithmIndex;

            public AlgorithmWorker(int dataStructureIndex, int algorithmIndex) {
                this.dataStructureIndex = dataStructureIndex;
                this.algorithmIndex = algorithmIndex;
            }

            @Override
            protected Void doInBackground() {
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
            }

            @Override
            protected void done() {
            }

            public static List<Book> loadBooksData(String booksDataset) {
                return null;
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                FrontEndGUI gui = new FrontEndGUI();
                gui.setVisible(true);
            });
        }
    }

