package GraphicsToText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class EncryptionImage {
    private JFrame frame;
    private JTextField imagePathTextField;
    private JTextField txtPathTextField;

    public EncryptionImage() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Image Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(750, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.setBackground(Color.BLACK);

        JButton inputFileButton = new JButton("Select Image File");
        inputFileButton.setForeground(Color.WHITE);
        inputFileButton.setBackground(Color.BLACK);
        inputFileButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        inputFileButton.setFocusPainted(false);
        inputFileButton.setHorizontalAlignment(SwingConstants.CENTER);
        inputFileButton.setVerticalAlignment(SwingConstants.CENTER);
        inputFileButton.setPreferredSize(new Dimension(120, 30));
        inputFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                inputFileButton.setBackground(Color.WHITE);
                inputFileButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                inputFileButton.setBackground(Color.BLACK);
                inputFileButton.setForeground(Color.WHITE);
            }
        });
        inputFileButton.addActionListener(new InputFileButtonListener());
        topPanel.add(inputFileButton);

        imagePathTextField = new JTextField(30);
        imagePathTextField.setEditable(false);
        imagePathTextField.setBackground(Color.BLACK);
        imagePathTextField.setForeground(Color.WHITE);
        topPanel.add(imagePathTextField);

        JButton outputButton = new JButton("Select Output TXT File");
        outputButton.setForeground(Color.WHITE);
        outputButton.setBackground(Color.BLACK);
        outputButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        outputButton.setFocusPainted(false);
        outputButton.setHorizontalAlignment(SwingConstants.CENTER);
        outputButton.setVerticalAlignment(SwingConstants.CENTER);
        outputButton.setPreferredSize(new Dimension(120, 30));
        outputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                outputButton.setBackground(Color.WHITE);
                outputButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                outputButton.setBackground(Color.BLACK);
                outputButton.setForeground(Color.WHITE);
            }
        });
        outputButton.addActionListener(new OutputButtonListener());
        topPanel.add(outputButton);

        txtPathTextField = new JTextField(30);
        txtPathTextField.setEditable(false);
        txtPathTextField.setBackground(Color.BLACK);
        txtPathTextField.setForeground(Color.WHITE);
        topPanel.add(txtPathTextField);

        frame.add(topPanel, BorderLayout.NORTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);

        JButton encryptButton = new JButton("Encrypt Image");
        encryptButton.setForeground(Color.WHITE);
        encryptButton.setBackground(Color.BLACK);
        encryptButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        encryptButton.setFocusPainted(false);
        encryptButton.setHorizontalAlignment(SwingConstants.CENTER);
        encryptButton.setVerticalAlignment(SwingConstants.CENTER);
        encryptButton.setPreferredSize(new Dimension(120, 30));
        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                encryptButton.setBackground(Color.WHITE);
                encryptButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                encryptButton.setBackground(Color.BLACK);
                encryptButton.setForeground(Color.WHITE);
            }
        });
        encryptButton.addActionListener(new EncryptButtonListener());
        bottomPanel.add(encryptButton);

        JButton endProgramButton = new JButton("End Program");
        endProgramButton.setForeground(Color.WHITE);
        endProgramButton.setBackground(Color.BLACK);
        endProgramButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        endProgramButton.setFocusPainted(false);
        endProgramButton.setHorizontalAlignment(SwingConstants.CENTER);
        endProgramButton.setVerticalAlignment(SwingConstants.CENTER);
        endProgramButton.setPreferredSize(new Dimension(120, 30));
        endProgramButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                endProgramButton.setBackground(Color.WHITE);
                endProgramButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                endProgramButton.setBackground(Color.BLACK);
                endProgramButton.setForeground(Color.WHITE);
            }
        });
        endProgramButton.addActionListener(new EndProgramButtonListener());
        bottomPanel.add(endProgramButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private class InputFileButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePathTextField.setText(selectedFile.getAbsolutePath());
            } else {
                imagePathTextField.setText("No file selected");
            }
        }
    }

    private class OutputButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                txtPathTextField.setText(selectedFile.getAbsolutePath() + ".txt");
            } else {
                txtPathTextField.setText("No file selected");
            }
        }
    }

    private class EncryptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String imagePath = imagePathTextField.getText();
            String txtPath = txtPathTextField.getText();
            if (imagePath.isEmpty() || txtPath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select both image file and output TXT file");
                return;
            }
            try {
                encryptImage(imagePath, txtPath);
                JOptionPane.showMessageDialog(frame, "Image encrypted successfully");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error encrypting image: " + ex.getMessage());
            }
        }
    }

    private class EndProgramButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static void encryptImage(String imagePath, String txtPath) throws IOException {
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
        String base64Encoded = Base64.getEncoder().encodeToString(imageData);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtPath))) {
            writer.write(base64Encoded);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EncryptionImage();
            }
        });
    }
}