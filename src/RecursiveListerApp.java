import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RecursiveListerApp extends JFrame {
    private JTextArea fileListArea;
    private JButton startButton;
    private JButton quitButton;
    private JLabel titleLabel;

    public RecursiveListerApp() {
        // Set up the frame
        setTitle("Recursive File Lister");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create and add title
        titleLabel = new JLabel("Recursive File Lister", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Create text area with scroll pane
        fileListArea = new JTextArea();
        fileListArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Monospaced for better alignment
        fileListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fileListArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Start button
        startButton = new JButton("Choose Directory");
        startButton.addActionListener(e -> browseAndListFiles());
        buttonPanel.add(startButton);

        // Quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void browseAndListFiles() {
        // Create file chooser set to directories only
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Show the dialog
        int result = fileChooser.showOpenDialog(this);

        // If a directory is selected
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();

            // Clear previous listings
            fileListArea.setText("");

            // Start recursive listing
            fileListArea.append("Directory Contents: " + selectedDirectory.getAbsolutePath() + "\n");
            fileListArea.append("--------------------------------------------\n");
            listFilesRecursively(selectedDirectory, 0);
        }
    }

    private void listFilesRecursively(File directory, int depth) {
        // Get list of files and directories in the current directory
        File[] filesAndDirs = directory.listFiles();

        // If directory is empty or null, return
        if (filesAndDirs == null) return;

        // Iterate through files and directories
        for (File file : filesAndDirs) {
            // Create indentation with vertical bars and spacing
            String indent = "│   ".repeat(Math.max(0, depth)) +
                    (depth > 0 ? "├── " : "");

            // If it's a directory, list it and recurse
            if (file.isDirectory()) {
                fileListArea.append(indent + "[DIR]  " + file.getName() + "/\n");
                listFilesRecursively(file, depth + 1);
            }
            // If it's a file, just list it
            else {
                fileListArea.append(indent + "[FILE] " + file.getName() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        // Ensure GUI is created on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            RecursiveListerApp app = new RecursiveListerApp();
            app.setLocationRelativeTo(null); // Center on screen
            app.setVisible(true);
        });
    }
}