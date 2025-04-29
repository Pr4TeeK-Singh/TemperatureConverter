import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverter extends JFrame {
    private JComboBox<String> inputScaleCombo;
    private JComboBox<String> outputScaleCombo;
    private JTextField inputField;
    private JTextField resultField;
    private JButton convertButton;
    private JButton clearButton;

    public TemperatureConverter() {
        setTitle("Temperature Converter");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        String[] scales = {"Celsius", "Fahrenheit", "Kelvin"};
        
        inputScaleCombo = new JComboBox<>(scales);
        outputScaleCombo = new JComboBox<>(scales);
        outputScaleCombo.setSelectedIndex(1); // Default to Fahrenheit
        
        inputField = new JTextField(10);
        inputField.setToolTipText("Enter a numeric temperature value");
        
        resultField = new JTextField(10);
        resultField.setEditable(false);
        resultField.setToolTipText("Converted temperature will be shown here");
        
        convertButton = new JButton("Convert");
        convertButton.setToolTipText("Click to convert temperature");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });
        
        clearButton = new JButton("Clear");
        clearButton.setToolTipText("Clear both input and result fields");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                resultField.setText("");
            }
        });
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Input Temperature:"), gbc);
        gbc.gridx = 1;
        panel.add(inputField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("From:"), gbc);
        gbc.gridx = 1;
        panel.add(inputScaleCombo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("To:"), gbc);
        gbc.gridx = 1;
        panel.add(outputScaleCombo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Result:"), gbc);
        gbc.gridx = 1;
        panel.add(resultField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        add(panel);
    }

    private void convertTemperature() {
        try {
            String inputText = inputField.getText().trim();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Input is empty. Please enter a value.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double inputTemp = Double.parseDouble(inputText);
            String inputScale = (String) inputScaleCombo.getSelectedItem();
            String outputScale = (String) outputScaleCombo.getSelectedItem();
            double result;

            // Convert input to Celsius
            switch (inputScale) {
                case "Fahrenheit":
                    inputTemp = (inputTemp - 32) * 5 / 9;
                    break;
                case "Kelvin":
                    inputTemp = inputTemp - 273.15;
                    break;
                // If Celsius, no change needed
            }

            // Convert from Celsius to desired scale
            switch (outputScale) {
                case "Fahrenheit":
                    result = (inputTemp * 9 / 5) + 32;
                    break;
                case "Kelvin":
                    result = inputTemp + 273.15;
                    break;
                default:
                    result = inputTemp;
            }

            String unit = outputScale.equals("Kelvin") ? "K" :
                        outputScale.equals("Fahrenheit") ? "°F" : "°C";

            resultField.setText(String.format("%.2f %s", result, unit));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric temperature value.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TemperatureConverter().setVisible(true));
    }
}
