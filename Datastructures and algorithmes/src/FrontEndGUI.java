import DataStructures.DataStructure;
import DataStructures.LinkedListDS;
import DataStructures.ArrayListDS;
import DataStructures.TreeMapDS;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FrontEndGUI extends JFrame implements ActionListener {

    private final JTextArea outputArea;
    private final JButton readCSVFileButton;
    private final JButton runDataStructureButton;
    private final JButton runAlgorithmButton;
    private final JTable dataTable;
    private DataStructure<String, Book> dataStructure;
    private JRadioButton linkedListRadioButton;
    private JRadioButton arrayListRadioButton;
    private JRadioButton treeMapRadioButton;
    private ButtonGroup dataStructureGroup;
    private ButtonGroup algorithmGroup;

    public FrontEndGUI() {
        setTitle("Algorithms Duration GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        readCSVFileButton = new JButton("Read Books CSV File");
        readCSVFileButton.addActionListener(this);
        readCSVFileButton.setBackground(Color.decode("#C1C7C8"));
        readCSVFileButton.setForeground(Color.BLACK);
        Font buttonFont = new Font("Microsoft Sans Serif", Font.BOLD, 18);
        readCSVFileButton.setFont(buttonFont);
        add(readCSVFileButton, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(outputPanel, BorderLayout.CENTER);

        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));

        // Left Section
        JPanel leftSelectionPanel = new JPanel(new BorderLayout());
        JPanel dataStructurePanel = createSelectionPanel("Select Data Structure",
                "LinkedList", "ArrayList", "TreeMap");
        leftSelectionPanel.add(dataStructurePanel, BorderLayout.CENTER);
        runDataStructureButton = new JButton("Run Data Structure");
        runDataStructureButton.addActionListener(this);
        leftSelectionPanel.add(runDataStructureButton, BorderLayout.SOUTH);
        selectionPanel.add(leftSelectionPanel);

        // Right Section
        JPanel rightSelectionPanel = new JPanel(new BorderLayout());
        JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
                "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
        rightSelectionPanel.add(algorithmPanel, BorderLayout.CENTER);
        runAlgorithmButton = new JButton("Run Algorithm");
        runAlgorithmButton.addActionListener(this);
        rightSelectionPanel.add(runAlgorithmButton, BorderLayout.SOUTH);
        selectionPanel.add(rightSelectionPanel);

        add(selectionPanel, BorderLayout.SOUTH);

        dataTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        outputPanel.add(tableScrollPane, BorderLayout.CENTER);

        outputPanel.setPreferredSize(new Dimension(800, 400));
        selectionPanel.setPreferredSize(new Dimension(800, 200));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        dataStructure = new LinkedListDS<>();

        checkAndLoadCSVFile();
    }

    private JPanel createSelectionPanel(String title, String... options) {
        JPanel panel = new JPanel(new GridLayout(options.length + 1, 1));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        dataStructureGroup = new ButtonGroup();
        algorithmGroup = new ButtonGroup();
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setActionCommand(option);
            if (title.equals("Select Data Structure")) {
                dataStructureGroup.add(radioButton);
            } else if (title.equals("Select Algorithm")) {
                algorithmGroup.add(radioButton);
            }
            panel.add(radioButton);

            if (option.equals("LinkedList")) {
                radioButton.setSelected(true);
                linkedListRadioButton = radioButton;
            } else if (option.equals("ArrayList")) {
                arrayListRadioButton = radioButton;
            } else if (option.equals("TreeMap")) {
                treeMapRadioButton = radioButton;
            }
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == readCSVFileButton) {
            openFilePicker();
        } else if (e.getSource() == runDataStructureButton) {
            runDataStructure();
        } else if (e.getSource() == runAlgorithmButton) {
            runSelectedAlgorithm();
        }
    }

    private void runDataStructure() {
        if (dataStructureGroup.getSelection() != null) {
            String selectedDataStructure = dataStructureGroup.getSelection().getActionCommand();
            long startTime = System.currentTimeMillis();

            switch (selectedDataStructure) {
                case "LinkedList":
                    dataStructure = new LinkedListDS<>();
                    outputArea.append("Data structure changed to LinkedList.\n");
                    break;
                case "ArrayList":
                    dataStructure = new ArrayListDS<>();
                    outputArea.append("Data structure changed to ArrayList.\n");
                    break;
                case "TreeMap":
                    dataStructure = new TreeMapDS<>();
                    outputArea.append("Data structure changed to TreeMap.\n");
                    break;
                default:
                    outputArea.append("Unknown data structure selected.\n");
            }

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            long minutes = (elapsedTime / 1000) / 60;
            long seconds = (elapsedTime / 1000) % 60;
            long milliseconds = elapsedTime % 1000;

            JOptionPane.showMessageDialog(this,
                    "Switched from data structure X to " + selectedDataStructure + ".\n" +
                            "Time elapsed: " + minutes + " minutes, " + seconds + " seconds, " + milliseconds + " milliseconds");
        } else {
            outputArea.append("Please select a data structure.\n");
        }
    }



    private void checkAndLoadCSVFile() {
        String csvFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Github" + File.separator + "Datastructures-and-algorithmes" + File.separator + "Datastructures and algorithmes" + File.separator + "src" + File.separator + "dataset" + File.separator + "books.csv";

        System.out.println("Checking CSV file at path: " + csvFilePath);

        File csvFile = new File(csvFilePath);
        if (csvFile.exists()) {
            System.out.println("CSV file found at path: " + csvFilePath);
            displayCSVData(csvFilePath);
        } else {
            System.out.println("CSV file not found at path: " + csvFilePath);
            int result = JOptionPane.showOptionDialog(
                    this,
                    "No CSV file found, please select one",
                    "File Not Found",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{"OK"},
                    "OK"
            );

            if (result == JOptionPane.OK_OPTION) {
                openFilePicker();
            }
        }
    }

    private void openFilePicker() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String csvFilePath = fileChooser.getSelectedFile().getPath();
            displayCSVData(csvFilePath);
        }
    }

    private void displayCSVData(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            dataStructure = new LinkedListDS<>();
            DefaultTableModel tableModel = new DefaultTableModel();

            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");
                for (String header : headers) {
                    tableModel.addColumn(header.trim());
                }
                System.out.println("Headers loaded: " + String.join(", ", headers));
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Book book = new Book(data[0].trim(), data[1].trim(), Double.parseDouble(data[2].trim()), data[3].trim());
                    dataStructure.put(book.getTitle(), book);
                    tableModel.addRow(data);
                }
            }

            dataTable.setModel(tableModel);

            outputArea.setText("CSV file loaded successfully!\n");

            for (Book book : dataStructure.values()) {
                System.out.println(book);
            }
        } catch (IOException ex) {
            outputArea.setText("Error reading CSV file: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            outputArea.setText("Error parsing number: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    private void printDataStructureType() {
        if (dataStructure instanceof LinkedListDS) {
            outputArea.append("Data structure is now a LinkedList.\n");
        } else if (dataStructure instanceof ArrayListDS) {
            outputArea.append("Data structure is now an ArrayList.\n");
        } else if (dataStructure instanceof TreeMapDS) {
            outputArea.append("Data structure is now a TreeMap.\n");
        } else {
            outputArea.append("Data structure is of unknown type.\n");
        }
    }

    private void runSelectedAlgorithm() {
        if (algorithmGroup.getSelection() != null) {
            String selectedAlgorithm = algorithmGroup.getSelection().getActionCommand();

            executeSelectedAlgorithm(selectedAlgorithm);
        } else {
            outputArea.append("Please select an algorithm.\n");
        }
    }

    private void executeSelectedAlgorithm(String selectedAlgorithm) {
        switch (selectedAlgorithm) {
            case "Sort by title":
                sortBooksByTitle();
                break;
            case "Sort by natural order":
                sortBooksByNaturalOrder();
                break;
            case "Search book by title":
                searchBookByTitle();
                break;
            case "Search books by author":
                searchBooksByAuthor();
                break;
            default:
                outputArea.append("Unknown algorithm selected.\n");
        }
    }

    private void sortBooksByTitle() {
        List<Book> sortedBooks = StreamSupport.stream(dataStructure.values().spliterator(), false)
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
        updateTableWithSortedData(sortedBooks);
    }

    private void sortBooksByNaturalOrder() {
        List<Book> sortedBooks = StreamSupport.stream(dataStructure.values().spliterator(), false)
                .sorted()
                .collect(Collectors.toList());
        updateTableWithSortedData(sortedBooks);
    }

    private void searchBookByTitle() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter book title to search:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Book foundBook = dataStructure.get(searchTerm);
            if (foundBook != null) {
                outputArea.append("Book found: " + foundBook + "\n");
            } else {
                outputArea.append("Book with title '" + searchTerm + "' not found.\n");
            }
        }
    }

    private void searchBooksByAuthor() {
        String authorName = JOptionPane.showInputDialog(this, "Enter author name to search books:");
        if (authorName != null && !authorName.isEmpty()) {
            List<Book> foundBooks = StreamSupport.stream(dataStructure.values().spliterator(), false)
                    .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                    .collect(Collectors.toList());
            if (!foundBooks.isEmpty()) {
                outputArea.append("Books by author '" + authorName + "':\n");
                for (Book book : foundBooks) {
                    outputArea.append(book.toString() + "\n");
                }
            } else {
                outputArea.append("No books found by author '" + authorName + "'.\n");
            }
        }
    }

    private void updateTableWithSortedData(List<Book> sortedData) {
        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
        tableModel.setRowCount(0);

        for (Book book : sortedData) {
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getRating(), book.getPubDate()});
        }

        dataTable.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrontEndGUI::new);
    }
}
