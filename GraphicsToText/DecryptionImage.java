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

public class DecryptionImage {
    private JFrame frame;
    private JTextField txtPathTextField;
    private JTextField imagePathTextField;

    public DecryptionImage() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Image Decryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(750, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.setBackground(Color.BLACK);

        JButton inputButton = new JButton("Select TXT File");
        inputButton.setForeground(Color.WHITE);
        inputButton.setBackground(Color.BLACK);
        inputButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        inputButton.setFocusPainted(false);
        inputButton.setHorizontalAlignment(SwingConstants.CENTER);
        inputButton.setVerticalAlignment(SwingConstants.CENTER);
        inputButton.setPreferredSize(new Dimension(120, 30));
        inputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                inputButton.setBackground(Color.WHITE);
                inputButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                inputButton.setBackground(Color.BLACK);
                inputButton.setForeground(Color.WHITE);
            }
        });
        inputButton.addActionListener(new InputButtonListener());
        topPanel.add(inputButton);

        txtPathTextField = new JTextField(30);
        txtPathTextField.setEditable(false);
        txtPathTextField.setBackground(Color.BLACK);
        txtPathTextField.setForeground(Color.WHITE);
        topPanel.add(txtPathTextField);

        JButton outputButton = new JButton("Select Output Image File");
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

        imagePathTextField = new JTextField(30);
        imagePathTextField.setEditable(false);
        imagePathTextField.setBackground(Color.BLACK);
        imagePathTextField.setForeground(Color.WHITE);
        topPanel.add(imagePathTextField);

        frame.add(topPanel, BorderLayout.NORTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);

        JButton decryptButton = new JButton("Decrypt Image");
        decryptButton.setForeground(Color.WHITE);
        decryptButton.setBackground(Color.BLACK);
        decryptButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        decryptButton.setFocusPainted(false);
        decryptButton.setHorizontalAlignment(SwingConstants.CENTER);
        decryptButton.setVerticalAlignment(SwingConstants.CENTER);
        decryptButton.setPreferredSize(new Dimension(120, 30));
        decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                decryptButton.setBackground(Color.WHITE);
                decryptButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                decryptButton.setBackground(Color.BLACK);
                decryptButton.setForeground(Color.WHITE);
            }
        });
        decryptButton.addActionListener(new DecryptButtonListener());
        bottomPanel.add(decryptButton);

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

    private class InputButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                txtPathTextField.setText(selectedFile.getAbsolutePath());
            } else {
                txtPathTextField.setText("No file selected");
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
                if (!selectedFile.getName().toLowerCase().endsWith(".jpg")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".jpg");
                }
                imagePathTextField.setText(selectedFile.getAbsolutePath());
            } else {
                imagePathTextField.setText("No file selected");
            }
        }
    }

    private class DecryptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String txtPath = txtPathTextField.getText();
            String imagePath = imagePathTextField.getText();
            if (txtPath.isEmpty() || imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select both TXT file and output image file");
                return;
            }
            try {
                decryptImage(txtPath, imagePath);
                JOptionPane.showMessageDialog(frame, "Image decrypted successfully");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error decrypting image: " + ex.getMessage());
            }
        }
    }

    private class EndProgramButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static void decryptImage(String txtPath, String imagePath) throws IOException {
        // Read the encoded string from the TXT file
        String base64Encoded;
        try (BufferedReader reader = new BufferedReader(new FileReader(txtPath))) {
            base64Encoded = reader.readLine();
        }

        // Decode the Base64 encoded string
        byte[] imageData = Base64.getDecoder().decode(base64Encoded);

        // Write the decoded byte array to an image file
        Files.write(Paths.get(imagePath), imageData);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DecryptionImage();
            }
        });
    }
}