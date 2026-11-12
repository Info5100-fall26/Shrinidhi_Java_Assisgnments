
package edu.neu.mgen;

import javax.swing.*;
import java.awt.*;

public class Lab3 extends JFrame {

    private JComboBox<String> colorDropdown;
    private JTextField outputField;
    private CirclePanel circlePanel;

    public Lab3() {

        setTitle("Lab3 - Color Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Dropdown entry field
        String[] colors = {"Red", "Blue", "Green"};
        colorDropdown = new JComboBox<>(colors);
        colorDropdown.setSelectedIndex(-1);
        colorDropdown.setPreferredSize(new Dimension(180, 35));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(colorDropdown);

        // 2. Circle panel
        circlePanel = new CirclePanel();
        circlePanel.setPreferredSize(new Dimension(350, 260));

        // 3. Text output field
        outputField = new JTextField(15);
        outputField.setEditable(false);
        outputField.setHorizontalAlignment(JTextField.CENTER);
        outputField.setFont(new Font("Arial", Font.BOLD, 18));
        outputField.setPreferredSize(new Dimension(200, 40));

        JLabel outputLabel = new JLabel("Selected Color:");
        outputLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(
            new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)
        );

        outputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        outputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(outputLabel);
        bottomPanel.add(Box.createVerticalStrut(8));
        bottomPanel.add(outputField);
        bottomPanel.add(Box.createVerticalStrut(15));

        // Add all three components
        add(topPanel, BorderLayout.NORTH);
        add(circlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Handle dropdown selection
        colorDropdown.addActionListener(e -> {

            String selectedColor =
                (String) colorDropdown.getSelectedItem();

            Color color = null;

            switch (selectedColor) {
                case "Red":
                    color = Color.RED;
                    break;

                case "Blue":
                    color = Color.BLUE;
                    break;

                case "Green":
                    color = Color.GREEN;
                    break;
            }

            // Update circle color
            circlePanel.setCircleColor(color);

            // Display selected color
            outputField.setText(selectedColor);
        });

        // Set proper window dimensions
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Circle drawing class
    class CirclePanel extends JPanel {

        private Color circleColor = null;

        public void setCircleColor(Color color) {
            circleColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            int diameter = 150;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            // Fill circle when a color is selected
            if (circleColor != null) {
                g.setColor(circleColor);
                g.fillOval(x, y, diameter, diameter);
            }

            // Draw circle outline
            g.setColor(Color.BLACK);
            g.drawOval(x, y, diameter, diameter);
        }
    }

    // Main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Lab3();
        });
    }
}
