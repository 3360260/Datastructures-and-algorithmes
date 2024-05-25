//import DataStructures.DataStructure;
//import DataStructures.LinkedListDS;
//import DataStructures.ArrayListDS;
//import DataStructures.TreeMapDS;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//public class FrontEndGUIOld extends JFrame implements ActionListener {
//
//    private final JTextArea outputArea;
//    private final JButton readCSVFileButton;
//    private final JButton runButton;
//    private final JTable dataTable;
//    private DataStructure<String, Book> dataStructure;
//    private JRadioButton linkedListRadioButton;
//    private JRadioButton arrayListRadioButton;
//    private JRadioButton treeMapRadioButton;
//    private ButtonGroup dataStructureGroup;
//    private ButtonGroup algorithmGroup;
//
//    public FrontEndGUIOld() {
//        setTitle("Algorithms Duration GUI");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // "Read Books CSV File" button
//        readCSVFileButton = new JButton("Read Books CSV File");
//        readCSVFileButton.addActionListener(this);
//        readCSVFileButton.setBackground(Color.decode("#C1C7C8"));
//        readCSVFileButton.setForeground(Color.BLACK);
//        Font buttonFont = new Font("Microsoft Sans Serif", Font.BOLD, 18);
//        readCSVFileButton.setFont(buttonFont);
//        add(readCSVFileButton, BorderLayout.NORTH);
//
//        // Output area panel for displaying messages
//        JPanel outputPanel = new JPanel(new BorderLayout());
//        outputArea = new JTextArea();
//        outputArea.setEditable(false);
//        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
//        add(outputPanel, BorderLayout.CENTER);
//
//        // Panel for radio button selections (data structure and algorithm)
//        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
//        JPanel dataStructurePanel = createSelectionPanel("Select Data Structure",
//                "LinkedList", "ArrayList", "TreeMap");
//        JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
//                "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
//        selectionPanel.add(dataStructurePanel);
//        selectionPanel.add(algorithmPanel);
//        add(selectionPanel, BorderLayout.SOUTH);
//
//        // "Run Algorithm" button
//        runButton = new JButton("Run Algorithm");
//        runButton.addActionListener(this);
//        runButton.setBackground(Color.decode("#C1C7C8"));
//        add(runButton, BorderLayout.EAST);
//
//        // Table to display CSV data
//        dataTable = new JTable();
//        JScrollPane tableScrollPane = new JScrollPane(dataTable);
//        outputPanel.add(tableScrollPane, BorderLayout.CENTER);
//
//        // Set preferred sizes to maintain layout
//        outputPanel.setPreferredSize(new Dimension(800, 400));
//        selectionPanel.setPreferredSize(new Dimension(800, 200));
//
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        // Initialize the default data structure as LinkedListDS
//        dataStructure = new LinkedListDS<>();
//
//        // Check for CSV file on startup
//        checkAndLoadCSVFile();
//    }
//
//    private JPanel createSelectionPanel(String title, String... options) {
//        JPanel panel = new JPanel(new GridLayout(options.length + 1, 1));
//        panel.setBorder(BorderFactory.createTitledBorder(title));
//
//        JLabel titleLabel = new JLabel(title);
//        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        panel.add(titleLabel);
//
//        dataStructureGroup = new ButtonGroup();
//        for (String option : options) {
//            JRadioButton radioButton = new JRadioButton(option);
//            radioButton.setActionCommand(option);
//            dataStructureGroup.add(radioButton);
//            panel.add(radioButton);
//
//            // Set LinkedList radio button as selected by default
//            if (option.equals("LinkedList")) {
//                radioButton.setSelected(true);
//                linkedListRadioButton = radioButton;
//            } else if (option.equals("ArrayList")) {
//                arrayListRadioButton = radioButton;
//            } else if (option.equals("TreeMap")) {
//                treeMapRadioButton = radioButton;
//            }
//        }
//
//        return panel;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == readCSVFileButton) {
//            openFilePicker();
//        } else if (e.getSource() == runButton) {
//            // Simulate executing the algorithm
//            runSelectedAlgorithm();
//        }
//    }
//
//    private void checkAndLoadCSVFile() {
//        String csvFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Github" + File.separator + "Datastructures-and-algorithmes" + File.separator + "Datastructures and algorithmes" + File.separator + "src" + File.separator + "dataset" + File.separator + "books.csv";
//
//        // Log the file path for debugging
//        System.out.println("Checking CSV file at path: " + csvFilePath);
//
//        File csvFile = new File(csvFilePath);
//        if (csvFile.exists()) {
//            // If the file exists, read and display the CSV data
//            System.out.println("CSV file found at path: " + csvFilePath);
//            displayCSVData(csvFilePath);
//        } else {
//            // If the file does not exist, show a pop-up message
//            System.out.println("CSV file not found at path: " + csvFilePath);
//            int result = JOptionPane.showOptionDialog(
//                    this,
//                    "No CSV file found, please select one",
//                    "File Not Found",
//                    JOptionPane.DEFAULT_OPTION,
//                    JOptionPane.WARNING_MESSAGE,
//                    null,
//                    new Object[]{"OK"},
//                    "OK"
//            );
//
//            // If the user clicks "OK", open the file picker
//            if (result == JOptionPane.OK_OPTION) {
//                openFilePicker();
//            }
//        }
//    }
//
//    private void openFilePicker() {
//        // Prompt user to select CSV file
//        JFileChooser fileChooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
//        fileChooser.setFileFilter(filter);
//
//        int returnValue = fileChooser.showOpenDialog(this);
//
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//            // Read and display CSV data
//            String csvFilePath = fileChooser.getSelectedFile().getPath();
//            displayCSVData(csvFilePath);
//        }
//    }
//
//    private void displayCSVData(String csvFilePath) {
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
//            dataStructure = new LinkedListDS<>(); // Default to LinkedListDS
//            DefaultTableModel tableModel = new DefaultTableModel();
//
//            // Read the first line as table headers
//            String headerLine = br.readLine();
//            if (headerLine != null) {
//                String[] headers = headerLine.split(",");
//                for (String header : headers) {
//                    tableModel.addColumn(header.trim());
//                }
//                System.out.println("Headers loaded: " + String.join(", ", headers));
//            }
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                if (data.length == 4) {
//                    Book book = new Book(data[0].trim(), data[1].trim(), Double.parseDouble(data[2].trim()), data[3].trim());
//                    dataStructure.put(book.getTitle(), book);
//                    tableModel.addRow(data);
//                }
//            }
//
//            dataTable.setModel(tableModel);
//
//            outputArea.setText("CSV file loaded successfully!\n");
//
//            for (Book book : dataStructure.values()) {
//                System.out.println(book);
//            }
//        } catch (IOException ex) {
//            outputArea.setText("Error reading CSV file: " + ex.getMessage() + "\n");
//            ex.printStackTrace();
//        } catch (NumberFormatException ex) {
//            outputArea.setText("Error parsing number: " + ex.getMessage() + "\n");
//            ex.printStackTrace();
//        }
//    }
//
//    private void printDataStructureType() {
//        if (dataStructure instanceof LinkedListDS) {
//            outputArea.append("Data structure is now a LinkedList.\n");
//        } else if (dataStructure instanceof ArrayListDS) {
//            outputArea.append("Data structure is now an ArrayList.\n");
//        } else if (dataStructure instanceof TreeMapDS) {
//            outputArea.append("Data structure is now a TreeMap.\n");
//        } else {
//            outputArea.append("Data structure is of unknown type.\n");
//        }
//    }
//
//    private void runSelectedAlgorithm() {
//        // Check if a radio button is selected
//        if (dataStructureGroup.getSelection() != null) {
//            String selectedDataStructure = dataStructureGroup.getSelection().getActionCommand();
//
//            // Convert to the selected data structure if necessary
//            if (selectedDataStructure.equals("ArrayList") && !(dataStructure instanceof ArrayListDS)) {
//                dataStructure = convertToArrayListDS();
//            } else if (selectedDataStructure.equals("TreeMap") && !(dataStructure instanceof TreeMapDS)) {
//                dataStructure = convertToTreeMapDS();
//            } else if (selectedDataStructure.equals("LinkedList") && !(dataStructure instanceof LinkedListDS)) {
//                dataStructure = convertToLinkedListDS();
//            }
//
//            // Print the type of data structure
//            printDataStructureType();
//
//            // Execute the selected algorithm
//            executeSelectedAlgorithm();
//        } else {
//            // Display an error message if no radio button is selected
//            outputArea.append("Please select a data structure.\n");
//        }
//    }
//
//    private void executeSelectedAlgorithm() {
//        String selectedAlgorithm = dataStructureGroup.getSelection().getActionCommand();
//        List<Book> sortedBooks;
//
//        switch (selectedAlgorithm) {
//            case "Sort by title":
//                sortedBooks = sortBooksByTitle();
//                updateTableWithSortedData(sortedBooks);
//                break;
//            case "Sort by natural order":
//                sortedBooks = sortBooksByNaturalOrder();
//                updateTableWithSortedData(sortedBooks);
//                break;
//            case "Search book by title":
//                // Implement search by title logic
//                break;
//            case "Search books by author":
//                // Implement search by author logic
//                break;
//            default:
//                outputArea.append("Unknown algorithm selected.\n");
//        }
//    }
//
//    private List<Book> sortBooksByTitle() {
//        return StreamSupport.stream(dataStructure.values().spliterator(), false)
//                .sorted(Comparator.comparing(Book::getTitle))
//                .collect(Collectors.toList());
//    }
//
//    private List<Book> sortBooksByNaturalOrder() {
//        return StreamSupport.stream(dataStructure.values().spliterator(), false)
//                .sorted()
//                .collect(Collectors.toList());
//    }
//
//    private void updateTableWithSortedData(List<Book> sortedData) {
//        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
//        tableModel.setRowCount(0); // Clear existing rows
//
//        for (Book book : sortedData) {
//            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getRating(), book.getPubDate()});
//        }
//
//        dataTable.repaint();
//    }
//
//    private DataStructure<String, Book> convertToArrayListDS() {
//        DataStructure<String, Book> newDataStructure = new ArrayListDS<>();
//        for (Book book : dataStructure.values()) {
//            newDataStructure.put(book.getTitle(), book);
//        }
//        return newDataStructure;
//    }
//
//    private DataStructure<String, Book> convertToTreeMapDS() {
//        DataStructure<String, Book> newDataStructure = new TreeMapDS<>();
//        for (Book book : dataStructure.values()) {
//            newDataStructure.put(book.getTitle(), book);
//        }
//        return newDataStructure;
//    }
//
//    private DataStructure<String, Book> convertToLinkedListDS() {
//        DataStructure<String, Book> newDataStructure = new LinkedListDS<>();
//        for (Book book : dataStructure.values()) {
//            newDataStructure.put(book.getTitle(), book);
//        }
//        return newDataStructure;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(FrontEndGUIOld::new);
//    }
//}

// 24 Mei, werkt, bug: Kan niet switchen naar andere data structure nadat arraylist is geselecteerd.
//import DataStructures.DataStructure;
//import DataStructures.LinkedListDS;
//import DataStructures.ArrayListDS;
//import DataStructures.TreeMapDS;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//public class FrontEndGUIOld extends JFrame implements ActionListener {
//
//    private final JTextArea outputArea;
//    private final JButton readCSVFileButton;
//    private final JButton runButton;
//    private final JTable dataTable;
//    private DataStructure<String, Book> dataStructure;
//    private JRadioButton linkedListRadioButton;
//    private JRadioButton arrayListRadioButton;
//    private JRadioButton treeMapRadioButton;
//    private ButtonGroup dataStructureGroup;
//    private ButtonGroup algorithmGroup;
//    private String currentDataStructure;
//
//    public FrontEndGUIOld() {
//        setTitle("Algorithms Duration GUI");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // "Read Books CSV File" button
//        readCSVFileButton = new JButton("Read Books CSV File");
//        readCSVFileButton.addActionListener(this);
//        readCSVFileButton.setBackground(Color.decode("#C1C7C8"));
//        readCSVFileButton.setForeground(Color.BLACK);
//        Font buttonFont = new Font("Microsoft Sans Serif", Font.BOLD, 18);
//        readCSVFileButton.setFont(buttonFont);
//        add(readCSVFileButton, BorderLayout.NORTH);
//
//        // Output area panel for displaying messages
//        JPanel outputPanel = new JPanel(new BorderLayout());
//        outputArea = new JTextArea();
//        outputArea.setEditable(false);
//        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
//        add(outputPanel, BorderLayout.CENTER);
//
//        // Panel for radio button selections (data structure and algorithm)
//        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
//        JPanel dataStructurePanel = createSelectionPanel("Select Data Structure",
//                "LinkedList", "ArrayList", "TreeMap");
//        JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
//                "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
//        selectionPanel.add(dataStructurePanel);
//        selectionPanel.add(algorithmPanel);
//        add(selectionPanel, BorderLayout.SOUTH);
//
//        // "Run Algorithm" button
//        runButton = new JButton("Run Algorithm");
//        runButton.addActionListener(this);
//        runButton.setBackground(Color.decode("#C1C7C8"));
//        add(runButton, BorderLayout.EAST);
//
//        // Table to display CSV data
//        dataTable = new JTable();
//        JScrollPane tableScrollPane = new JScrollPane(dataTable);
//        outputPanel.add(tableScrollPane, BorderLayout.CENTER);
//
//        // Set preferred sizes to maintain layout
//        outputPanel.setPreferredSize(new Dimension(800, 400));
//        selectionPanel.setPreferredSize(new Dimension(800, 200));
//
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        // Initialize the default data structure as LinkedListDS
//        dataStructure = new LinkedListDS<>();
//        currentDataStructure = "LinkedList";
//
//        // Check for CSV file on startup
//        checkAndLoadCSVFile();
//    }
//
//    private JPanel createSelectionPanel(String title, String... options) {
//        JPanel panel = new JPanel(new GridLayout(options.length + 1, 1));
//        panel.setBorder(BorderFactory.createTitledBorder(title));
//
//        JLabel titleLabel = new JLabel(title);
//        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        panel.add(titleLabel);
//
//        if (title.contains("Data Structure")) {
//            dataStructureGroup = new ButtonGroup();
//        } else {
//            algorithmGroup = new ButtonGroup();
//        }
//
//        for (String option : options) {
//            JRadioButton radioButton = new JRadioButton(option);
//            radioButton.setActionCommand(option);
//            if (title.contains("Data Structure")) {
//                dataStructureGroup.add(radioButton);
//            } else {
//                algorithmGroup.add(radioButton);
//            }
//            panel.add(radioButton);
//
//            // Set LinkedList radio button as selected by default
//            if (option.equals("LinkedList")) {
//                radioButton.setSelected(true);
//                linkedListRadioButton = radioButton;
//            } else if (option.equals("ArrayList")) {
//                arrayListRadioButton = radioButton;
//            } else if (option.equals("TreeMap")) {
//                treeMapRadioButton = radioButton;
//            }
//        }
//
//        return panel;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == readCSVFileButton) {
//            openFilePicker();
//        } else if (e.getSource() == runButton) {
//            // Simulate executing the algorithm
//            runSelectedAlgorithm();
//        }
//    }
//
//    private void checkAndLoadCSVFile() {
//        String csvFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Github" + File.separator + "Datastructures-and-algorithmes" + File.separator + "Datastructures and algorithmes" + File.separator + "src" + File.separator + "dataset" + File.separator + "books.csv";
//
//        // Log the file path for debugging
//        System.out.println("Checking CSV file at path: " + csvFilePath);
//
//        File csvFile = new File(csvFilePath);
//        if (csvFile.exists()) {
//            // If the file exists, read and display the CSV data
//            System.out.println("CSV file found at path: " + csvFilePath);
//            displayCSVData(csvFilePath);
//        } else {
//            // If the file does not exist, show a pop-up message
//            System.out.println("CSV file not found at path: " + csvFilePath);
//            int result = JOptionPane.showOptionDialog(
//                    this,
//                    "No CSV file found, please select one",
//                    "File Not Found",
//                    JOptionPane.DEFAULT_OPTION,
//                    JOptionPane.WARNING_MESSAGE,
//                    null,
//                    new Object[]{"OK"},
//                    "OK"
//            );
//
//            // If the user clicks "OK", open the file picker
//            if (result == JOptionPane.OK_OPTION) {
//                openFilePicker();
//            }
//        }
//    }
//
//    private void openFilePicker() {
//        // Prompt user to select CSV file
//        JFileChooser fileChooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
//        fileChooser.setFileFilter(filter);
//
//        int returnValue = fileChooser.showOpenDialog(this);
//
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//            // Read and display CSV data
//            String csvFilePath = fileChooser.getSelectedFile().getPath();
//            displayCSVData(csvFilePath);
//        }
//    }
//
//    private void displayCSVData(String csvFilePath) {
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
//            dataStructure = new LinkedListDS<>(); // Default to LinkedListDS
//            DefaultTableModel tableModel = new DefaultTableModel();
//
//            // Read the first line as table headers
//            String headerLine = br.readLine();
//            if (headerLine != null) {
//                String[] headers = headerLine.split(",");
//                for (String header : headers) {
//                    tableModel.addColumn(header.trim());
//                }
//                System.out.println("Headers loaded: " + String.join(", ", headers));
//            }
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                if (data.length == 4) {
//                    Book book = new Book(data[0].trim(), data[1].trim(), Double.parseDouble(data[2].trim()), data[3].trim());
//                    dataStructure.put(book.getTitle(), book);
//                    tableModel.addRow(data);
//                }
//            }
//
//            dataTable.setModel(tableModel);
//
//            outputArea.setText("CSV file loaded successfully!\n");
//
//            for (Book book : dataStructure.values()) {
//                System.out.println(book);
//            }
//        } catch (IOException ex) {
//            outputArea.setText("Error reading CSV file: " + ex.getMessage() + "\n");
//            ex.printStackTrace();
//        } catch (NumberFormatException ex) {
//            outputArea.setText("Error parsing number: " + ex.getMessage() + "\n");
//            ex.printStackTrace();
//        }
//    }
//
//    private void printDataStructureType() {
//        if (dataStructure instanceof LinkedListDS) {
//            outputArea.append("Data structure is now a LinkedList.\n");
//        } else if (dataStructure instanceof ArrayListDS) {
//            outputArea.append("Data structure is now an ArrayList.\n");
//        } else if (dataStructure instanceof TreeMapDS) {
//            outputArea.append("Data structure is now a TreeMap.\n");
//        } else {
//            outputArea.append("Data structure is of unknown type.\n");
//        }
//    }
//
//    private void runSelectedAlgorithm() {
//        if (dataStructureGroup.getSelection() != null) {
//            String selectedDataStructure = dataStructureGroup.getSelection().getActionCommand();
//
//            long startTime = System.currentTimeMillis(); // Start time
//
//            // Convert to the selected data structure if necessary
//            if (!currentDataStructure.equals(selectedDataStructure)) {
//                switch (selectedDataStructure) {
//                    case "ArrayList":
//                        dataStructure = convertToArrayListDS();
//                        break;
//                    case "TreeMap":
//                        dataStructure = convertToTreeMapDS();
//                        break;
//                    case "LinkedList":
//                        dataStructure = convertToLinkedListDS();
//                        break;
//                }
//
//                long endTime = System.currentTimeMillis(); // End time
//                long elapsedTime = endTime - startTime;
//                displayTimeElapsedPopup(currentDataStructure, selectedDataStructure, elapsedTime);
//
//                currentDataStructure = selectedDataStructure; // Update current data structure
//            }
//
//            // Print the type of data structure
//            printDataStructureType();
//
//            // Execute the selected algorithm
//            executeSelectedAlgorithm();
//        } else {
//            // Display an error message if no radio button is selected
//            outputArea.append("Please select a data structure.\n");
//        }
//    }
//
//    private void executeSelectedAlgorithm() {
//        if (algorithmGroup.getSelection() != null) {
//            String selectedAlgorithm = algorithmGroup.getSelection().getActionCommand();
//            List<Book> sortedBooks;
//
//            switch (selectedAlgorithm) {
//                case "Sort by title":
//                    sortedBooks = sortBooksByTitle();
//                    updateTableWithSortedData(sortedBooks);
//                    break;
//                case "Sort by natural order":
//                    sortedBooks = sortBooksByNaturalOrder();
//                    updateTableWithSortedData(sortedBooks);
//                    break;
//                case "Search book by title":
//                    // Implement search by title logic
//                    break;
//                case "Search books by author":
//                    // Implement search by author logic
//                    break;
//                default:
//                    outputArea.append("Unknown algorithm selected.\n");
//            }
//        } else {
//            outputArea.append("Please select an algorithm.\n");
//        }
//    }
//
//    private void displayTimeElapsedPopup(String from, String to, long elapsedTime) {
//        long minutes = (elapsedTime / 1000) / 60;
//        long seconds = (elapsedTime / 1000) % 60;
//        long milliseconds = elapsedTime % 1000;
//
//        String message = String.format(
//                "Switched data from data structure %s to data structure %s.\nTime elapsed: %d minutes, %d seconds, %d milliseconds.",
//                from, to, minutes, seconds, milliseconds
//        );
//
//        JOptionPane.showMessageDialog(
//                this,
//                message,
//                "Data Structure Switch",
//                JOptionPane.INFORMATION_MESSAGE
//        );
//    }
//
//    private List<Book> sortBooksByTitle() {
//        return StreamSupport.stream(dataStructure.values().spliterator(), false)
//                .sorted(Comparator.comparing(Book::getTitle))
//                .collect(Collectors.toList());
//    }
//
//    private List<Book> sortBooksByNaturalOrder() {
//        return StreamSupport.stream(dataStructure.values().spliterator(), false)
//                .sorted()
//                .collect(Collectors.toList());
//    }
//
//    private void updateTableWithSortedData(List<Book> sortedData) {
//        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
//        tableModel.setRowCount(0); // Clear existing rows
//
//        for (Book book : sortedData) {
//            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getRating(), book.getPubDate()});
//        }
//
//        dataTable.repaint();
//    }
//
//    private DataStructure<String, Book> convertToArrayListDS() {
//        DataStructure<String, Book> newDataStructure = new ArrayListDS<>();
//        for (Book book : dataStructure.values()) {
//            newDataStructure.put(book.getTitle(), book);
//        }
//        return newDataStructure;
//    }
//
//    private DataStructure<String, Book> convertToTreeMapDS() {
//        DataStructure<String, Book> newDataStructure = new TreeMapDS<>();
//        for (Book book : dataStructure.values()) {
//            newDataStructure.put(book.getTitle(), book);
//        }
//        return newDataStructure;
//    }
//
//    private DataStructure<String, Book> convertToLinkedListDS() {
//        DataStructure<String, Book> newDataStructure = new LinkedListDS<>();
//        for (Book book : dataStructure.values()) {
//            newDataStructure.put(book.getTitle(), book);
//        }
//        return newDataStructure;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(FrontEndGUIOld::new);
//    }
//}
//


// 24 mei, werkt, bug: sorting algoritmes verwijderen data uit tabel
//import DataStructures.DataStructure;
//import DataStructures.LinkedListDS;
//import DataStructures.ArrayListDS;
//import DataStructures.TreeMapDS;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//public class FrontEndGUIOld extends JFrame implements ActionListener {
//
//    private final JTextArea outputArea;
//    private final JButton readCSVFileButton;
//    private final JButton runButton;
//    private final JTable dataTable;
//    private DataStructure<String, Book> dataStructure;
//    private JRadioButton linkedListRadioButton;
//    private JRadioButton arrayListRadioButton;
//    private JRadioButton treeMapRadioButton;
//    private ButtonGroup dataStructureGroup;
//    private ButtonGroup algorithmGroup;
//    private String currentDataStructure;
//
//    public FrontEndGUIOld() {
//        setTitle("Algorithms Duration GUI");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // "Read Books CSV File" button
//        readCSVFileButton = new JButton("Read Books CSV File");
//        readCSVFileButton.addActionListener(this);
//        readCSVFileButton.setBackground(Color.decode("#C1C7C8"));
//        readCSVFileButton.setForeground(Color.BLACK);
//        Font buttonFont = new Font("Microsoft Sans Serif", Font.BOLD, 18);
//        readCSVFileButton.setFont(buttonFont);
//        add(readCSVFileButton, BorderLayout.NORTH);
//
//        // Output area panel for displaying messages
//        JPanel outputPanel = new JPanel(new BorderLayout());
//        outputArea = new JTextArea();
//        outputArea.setEditable(false);
//        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
//        add(outputPanel, BorderLayout.CENTER);
//
//        // Panel for radio button selections (data structure and algorithm)
//        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
//        JPanel dataStructurePanel = createSelectionPanel("Select Data Structure",
//                "LinkedList", "ArrayList", "TreeMap");
//        JPanel algorithmPanel = createSelectionPanel("Select Algorithm",
//                "Sort by title", "Search book by title", "Sort by natural order", "Search books by author");
//        selectionPanel.add(dataStructurePanel);
//        selectionPanel.add(algorithmPanel);
//        add(selectionPanel, BorderLayout.SOUTH);
//
//        // "Run Algorithm" button
//        runButton = new JButton("Run Algorithm");
//        runButton.addActionListener(this);
//        runButton.setBackground(Color.decode("#C1C7C8"));
//        add(runButton, BorderLayout.EAST);
//
//        // Table to display CSV data
//        dataTable = new JTable();
//        JScrollPane tableScrollPane = new JScrollPane(dataTable);
//        outputPanel.add(tableScrollPane, BorderLayout.CENTER);
//
//        // Set preferred sizes to maintain layout
//        outputPanel.setPreferredSize(new Dimension(800, 400));
//        selectionPanel.setPreferredSize(new Dimension(800, 200));
//
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        // Initialize the default data structure as LinkedListDS
//        dataStructure = new LinkedListDS<>();
//        currentDataStructure = "LinkedList";
//
//        // Check for CSV file on startup
//        checkAndLoadCSVFile();
//    }
//
//    private JPanel createSelectionPanel(String title, String... options) {
//        JPanel panel = new JPanel(new GridLayout(options.length + 1, 1));
//        panel.setBorder(BorderFactory.createTitledBorder(title));
//
//        JLabel titleLabel = new JLabel(title);
//        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        panel.add(titleLabel);
//
//        if (title.equals("Select Data Structure")) {
//            dataStructureGroup = new ButtonGroup();
//            for (String option : options) {
//                JRadioButton radioButton = new JRadioButton(option);
//                radioButton.setActionCommand(option);
//                dataStructureGroup.add(radioButton);
//                panel.add(radioButton);
//
//                // Set LinkedList radio button as selected by default
//                if (option.equals("LinkedList")) {
//                    radioButton.setSelected(true);
//                    linkedListRadioButton = radioButton;
//                } else if (option.equals("ArrayList")) {
//                    arrayListRadioButton = radioButton;
//                } else if (option.equals("TreeMap")) {
//                    treeMapRadioButton = radioButton;
//                }
//            }
//        } else {
//            algorithmGroup = new ButtonGroup();
//            for (String option : options) {
//                JRadioButton radioButton = new JRadioButton(option);
//                radioButton.setActionCommand(option);
//                algorithmGroup.add(radioButton);
//                panel.add(radioButton);
//            }
//        }
//
//        return panel;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == readCSVFileButton) {
//            openFilePicker();
//        } else if (e.getSource() == runButton) {
//            // Simulate executing the algorithm
//            runSelectedAlgorithm();
//        }
//    }
//
//    private void checkAndLoadCSVFile() {
//        String csvFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Github" + File.separator + "Datastructures-and-algorithmes" + File.separator + "Datastructures and algorithmes" + File.separator + "src" + File.separator + "dataset" + File.separator + "books.csv";
//
//        // Log the file path for debugging
//        System.out.println("Checking CSV file at path: " + csvFilePath);
//
//        File csvFile = new File(csvFilePath);
//        if (csvFile.exists()) {
//            // If the file exists, read and display the CSV data
//            System.out.println("CSV file found at path: " + csvFilePath);
//            displayCSVData(csvFilePath);
//        } else {
//            // If the file does not exist, show a pop-up message
//            System.out.println("CSV file not found at path: " + csvFilePath);
//            int result = JOptionPane.showOptionDialog(
//                    this,
//                    "No CSV file found, please select one",
//                    "File Not Found",
//                    JOptionPane.DEFAULT_OPTION,
//                    JOptionPane.WARNING_MESSAGE,
//                    null,
//                    new Object[]{"OK"},
//                    "OK"
//            );
//
//            // If the user clicks "OK", open the file picker
//            if (result == JOptionPane.OK_OPTION) {
//                openFilePicker();
//            }
//        }
//    }
//
//    private void openFilePicker() {
//        // Prompt user to select CSV file
//        JFileChooser fileChooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
//        fileChooser.setFileFilter(filter);
//
//        int returnValue = fileChooser.showOpenDialog(this);
//
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//            // Read and display CSV data
//            String csvFilePath = fileChooser.getSelectedFile().getPath();
//            displayCSVData(csvFilePath);
//        }
//    }
//
//    private void displayCSVData(String csvFilePath) {
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
//            dataStructure = new LinkedListDS<>(); // Default to LinkedListDS
//            DefaultTableModel tableModel = new DefaultTableModel();
//
//            // Read the first line as table headers
//            String headerLine = br.readLine();
//            if (headerLine != null) {
//                String[] headers = headerLine.split(",");
//                for (String header : headers) {
//                    tableModel.addColumn(header.trim());
//                }
//                System.out.println("Headers loaded: " + String.join(", ", headers));
//            }
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                if (data.length == 4) {
//                    Book book = new Book(data[0].trim(), data[1].trim(), Double.parseDouble(data[2].trim()), data[3].trim());
//                    dataStructure.put(book.getTitle(), book);
//                    tableModel.addRow(data);
//                }
//            }
//
//            dataTable.setModel(tableModel);
//
//            outputArea.setText("CSV file loaded successfully!\n");
//
//            for (Book book : dataStructure.values()) {
//                System.out.println(book);
//            }
//        } catch (IOException ex) {
//            outputArea.setText("Error reading CSV file: " + ex.getMessage() + "\n");
//            ex.printStackTrace();
//        } catch (NumberFormatException ex) {
//            outputArea.setText("Error parsing number: " + ex.getMessage() + "\n");
//            ex.printStackTrace();
//        }
//    }
//
//    private void printDataStructureType() {
//        if (dataStructure instanceof LinkedListDS) {
//            outputArea.append("Data structure is now a LinkedList.\n");
//        } else if (dataStructure instanceof ArrayListDS) {
//            outputArea.append("Data structure is now an ArrayList.\n");
//        } else if (dataStructure instanceof TreeMapDS) {
//            outputArea.append("Data structure is now a TreeMap.\n");
//        } else {
//            outputArea.append("Data structure is of unknown type.\n");
//        }
//    }
//
//    private void runSelectedAlgorithm() {
//        if (dataStructureGroup.getSelection() != null) {
//            String selectedDataStructure = dataStructureGroup.getSelection().getActionCommand();
//            if (!selectedDataStructure.equals(currentDataStructure)) {
//                long startTime = System.currentTimeMillis(); // Start time
//
//                // Switch data structure
//                switch (selectedDataStructure) {
//                    case "ArrayList":
//                        convertToArrayListDS();
//                        break;
//                    case "TreeMap":
//                        convertToTreeMapDS();
//                        break;
//                    case "LinkedList":
//                        convertToLinkedListDS();
//                        break;
//                }
//
//                long endTime = System.currentTimeMillis(); // End time
//                long elapsedTime = endTime - startTime; // Elapsed time in milliseconds
//
//                // Calculate minutes, seconds, and milliseconds
//                long minutes = (elapsedTime / 1000) / 60;
//                long seconds = (elapsedTime / 1000) % 60;
//                long milliseconds = elapsedTime % 1000;
//
//                // Display pop-up window with elapsed time
//                String message = String.format("Switched data from data structure %s to data structure %s. Time elapsed: %d:%02d:%03d",
//                        currentDataStructure, selectedDataStructure, minutes, seconds, milliseconds);
//                JOptionPane.showMessageDialog(this, message, "Data Structure Switch", JOptionPane.INFORMATION_MESSAGE);
//
//                // Update current data structure
//                currentDataStructure = selectedDataStructure;
//
//                // Print the type of data structure
//                printDataStructureType();
//            }
//
//            // Execute the selected algorithm
//            executeSelectedAlgorithm();
//        } else {
//            // Display an error message if no radio button is selected
//            outputArea.append("Please select a data structure.\n");
//        }
//    }
//
//    private void executeSelectedAlgorithm() {
//        if (algorithmGroup.getSelection() != null) {
//            String selectedAlgorithm = algorithmGroup.getSelection().getActionCommand();
//            List<Book> sortedBooks;
//
//            switch (selectedAlgorithm) {
//                case "Sort by title":
//                    sortedBooks = sortBooksByTitle();
//                    updateTableWithSortedData(sortedBooks);
//                    break;
//                case "Sort by natural order":
//                    sortedBooks = sortBooksByNaturalOrder();
//                    updateTableWithSortedData(sortedBooks);
//                    break;
//                case "Search book by title":
//                    // Implement search by title logic
//                    break;
//                case "Search books by author":
//                    // Implement search by author logic
//                    break;
//                default:
//                    outputArea.append("Unknown algorithm selected.\n");
//            }
//        } else {
//            // Display an error message if no radio button is selected
//            outputArea.append("Please select an algorithm.\n");
//        }
//    }
//
//    private List<Book> sortBooksByTitle() {
//        return StreamSupport.stream(dataStructure.values().spliterator(), false)
//                .sorted(Comparator.comparing(Book::getTitle))
//                .collect(Collectors.toList());
//    }
//
//    private List<Book> sortBooksByNaturalOrder() {
//        return StreamSupport.stream(dataStructure.values().spliterator(), false)
//                .sorted()
//                .collect(Collectors.toList());
//    }
//
//    private void updateTableWithSortedData(List<Book> sortedData) {
//        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
//        tableModel.setRowCount(0); // Clear existing rows
//
//        for (Book book : sortedData) {
//            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getRating(), book.getPubDate()});
//        }
//
//        dataTable.repaint();
//    }
//
//    private void convertToArrayListDS() {
//        DataStructure<String, Book> newDataStructure = new ArrayListDS<>();
//        for (Book book : dataStructure.values()) {
//            if (book != null) {
//                newDataStructure.put(book.getTitle(), book);
//            }
//        }
//        dataStructure = newDataStructure;
//    }
//
//    private void convertToTreeMapDS() {
//        DataStructure<String, Book> newDataStructure = new TreeMapDS<>();
//        for (Book book : dataStructure.values()) {
//            if (book != null) {
//                newDataStructure.put(book.getTitle(), book);
//            }
//        }
//        dataStructure = newDataStructure;
//    }
//
//    private void convertToLinkedListDS() {
//        DataStructure<String, Book> newDataStructure = new LinkedListDS<>();
//        for (Book book : dataStructure.values()) {
//            if (book != null) {
//                newDataStructure.put(book.getTitle(), book);
//            }
//        }
//        dataStructure = newDataStructure;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(FrontEndGUIOld::new);
//    }
//}

// 24 mei, werkt, bug: maar één sorting werkt.
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

public class FrontEndGUIOld extends JFrame implements ActionListener {

    private final JTextArea outputArea;
    private final JButton readCSVFileButton;
    private final JButton runButton;
    private final JTable dataTable;
    private DataStructure<String, Book> dataStructure;
    private JRadioButton linkedListRadioButton;
    private JRadioButton arrayListRadioButton;
    private JRadioButton treeMapRadioButton;
    private ButtonGroup dataStructureGroup;
    private ButtonGroup algorithmGroup;
    private String currentDataStructure;

    public FrontEndGUIOld() {
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

        // Initialize the default data structure as LinkedListDS
        dataStructure = new LinkedListDS<>();
        currentDataStructure = "LinkedList";

        // Check for CSV file on startup
        checkAndLoadCSVFile();
    }

    private JPanel createSelectionPanel(String title, String... options) {
        JPanel panel = new JPanel(new GridLayout(options.length + 1, 1));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        if (title.equals("Select Data Structure")) {
            dataStructureGroup = new ButtonGroup();
            for (String option : options) {
                JRadioButton radioButton = new JRadioButton(option);
                radioButton.setActionCommand(option);
                dataStructureGroup.add(radioButton);
                panel.add(radioButton);

                // Set LinkedList radio button as selected by default
                if (option.equals("LinkedList")) {
                    radioButton.setSelected(true);
                    linkedListRadioButton = radioButton;
                } else if (option.equals("ArrayList")) {
                    arrayListRadioButton = radioButton;
                } else if (option.equals("TreeMap")) {
                    treeMapRadioButton = radioButton;
                }
            }
        } else {
            algorithmGroup = new ButtonGroup();
            for (String option : options) {
                JRadioButton radioButton = new JRadioButton(option);
                radioButton.setActionCommand(option);
                algorithmGroup.add(radioButton);
                panel.add(radioButton);
            }
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == readCSVFileButton) {
            openFilePicker();
        } else if (e.getSource() == runButton) {
            // Simulate executing the algorithm
            runSelectedAlgorithm();
        }
    }

    private void checkAndLoadCSVFile() {
        String csvFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Github" + File.separator + "Datastructures-and-algorithmes" + File.separator + "Datastructures and algorithmes" + File.separator + "src" + File.separator + "dataset" + File.separator + "books.csv";

        // Log the file path for debugging
        System.out.println("Checking CSV file at path: " + csvFilePath);

        File csvFile = new File(csvFilePath);
        if (csvFile.exists()) {
            // If the file exists, read and display the CSV data
            System.out.println("CSV file found at path: " + csvFilePath);
            displayCSVData(csvFilePath);
        } else {
            // If the file does not exist, show a pop-up message
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

            // If the user clicks "OK", open the file picker
            if (result == JOptionPane.OK_OPTION) {
                openFilePicker();
            }
        }
    }

    private void openFilePicker() {
        // Prompt user to select CSV file
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Read and display CSV data
            String csvFilePath = fileChooser.getSelectedFile().getPath();
            displayCSVData(csvFilePath);
        }
    }

    private void displayCSVData(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            dataStructure = new LinkedListDS<>(); // Default to LinkedListDS
            DefaultTableModel tableModel = new DefaultTableModel();

            // Read the first line as table headers
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
        if (dataStructureGroup.getSelection() != null) {
            String selectedDataStructure = dataStructureGroup.getSelection().getActionCommand();
            if (!selectedDataStructure.equals(currentDataStructure)) {
                long startTime = System.currentTimeMillis(); // Start time

                // Switch data structure
                switch (selectedDataStructure) {
                    case "ArrayList":
                        convertToArrayListDS();
                        break;
                    case "TreeMap":
                        convertToTreeMapDS();
                        break;
                    case "LinkedList":
                        convertToLinkedListDS();
                        break;
                }

                long endTime = System.currentTimeMillis(); // End time
                long elapsedTime = endTime - startTime; // Elapsed time in milliseconds

                // Calculate minutes, seconds, and milliseconds
                long minutes = (elapsedTime / 1000) / 60;
                long seconds = (elapsedTime / 1000) % 60;
                long milliseconds = elapsedTime % 1000;

                // Display pop-up window with elapsed time
                String message = String.format("Switched data from data structure %s to data structure %s. Time elapsed: %d:%02d:%03d",
                        currentDataStructure, selectedDataStructure, minutes, seconds, milliseconds);
                JOptionPane.showMessageDialog(this, message, "Data Structure Switch", JOptionPane.INFORMATION_MESSAGE);

                // Update current data structure
                currentDataStructure = selectedDataStructure;

                // Print the type of data structure
                printDataStructureType();
            }

            // Execute the selected algorithm
            executeSelectedAlgorithm();
        } else {
            // Display an error message if no radio button is selected
            outputArea.append("Please select a data structure.\n");
        }
    }

    private void executeSelectedAlgorithm() {
        if (algorithmGroup.getSelection() != null) {
            String selectedAlgorithm = algorithmGroup.getSelection().getActionCommand();
            List<Book> sortedBooks;

            switch (selectedAlgorithm) {
                case "Sort by title":
                    sortedBooks = sortBooksByTitle();
                    updateTableWithSortedData(sortedBooks);
                    break;
                case "Sort by natural order":
                    sortedBooks = sortBooksByNaturalOrder();
                    updateTableWithSortedData(sortedBooks);
                    break;
                case "Search book by title":
                    // Implement search by title logic
                    break;
                case "Search books by author":
                    // Implement search by author logic
                    break;
                default:
                    outputArea.append("Unknown algorithm selected.\n");
            }
        } else {
            // Display an error message if no radio button is selected
            outputArea.append("Please select an algorithm.\n");
        }
    }

    private List<Book> sortBooksByTitle() {
        return StreamSupport.stream(dataStructure.values().spliterator(), false)
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    private List<Book> sortBooksByNaturalOrder() {
        return StreamSupport.stream(dataStructure.values().spliterator(), false)
                .sorted()
                .collect(Collectors.toList());
    }

    private void updateTableWithSortedData(List<Book> sortedData) {
        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (Book book : sortedData) {
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getRating(), book.getPubDate()});
        }

        dataTable.repaint();
    }

    private void convertToArrayListDS() {
        DataStructure<String, Book> newDataStructure = new ArrayListDS<>();
        for (Book book : dataStructure.values()) {
            if (book != null) {
                newDataStructure.put(book.getTitle(), book);
            }
        }
        dataStructure = newDataStructure;
    }

    private void convertToTreeMapDS() {
        DataStructure<String, Book> newDataStructure = new TreeMapDS<>();
        for (Book book : dataStructure.values()) {
            if (book != null) {
                newDataStructure.put(book.getTitle(), book);
            }
        }
        dataStructure = newDataStructure;
    }

    private void convertToLinkedListDS() {
        DataStructure<String, Book> newDataStructure = new LinkedListDS<>();
        for (Book book : dataStructure.values()) {
            if (book != null) {
                newDataStructure.put(book.getTitle(), book);
            }
        }
        dataStructure = newDataStructure;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrontEndGUIOld::new);
    }
}