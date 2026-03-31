import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 500;
    int boardHeight = 600;

    Color customLightGray = new Color (212,212,210);
    Color customDarkGray = new Color (80,80,80);
    Color customBlack = new Color (28,28,28);
    Color customOrange = new Color (255, 149, 0);

    String[] buttonValues = {
        "%","CE","AC","←",
        "1/x","x²","√","÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "+/-","0", ".", "="
    };

    String[] rightSymbols = {"←","÷", "×", "-", "+", "="};
    String[] topSymbols = {"%","CE","AC"};
    String[] bottomSymbols = {"1/x","x²","√"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JLabel secLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    //A+B, A-B, A*B, A/B, sqrt(A)
    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        secLabel.setBackground(customBlack);
        secLabel.setForeground(customDarkGray);
        secLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        secLabel.setHorizontalAlignment(JLabel.RIGHT);
        secLabel.setText("0");
        secLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(secLabel, BorderLayout.NORTH);
        displayPanel.add(displayLabel, BorderLayout.SOUTH);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(6, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 25));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            
            if(Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if(Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if(Arrays.asList(rightSymbols).contains(buttonValue)){
                        if (buttonValue == "←") {
                            String currentText = displayLabel.getText();
                            if (currentText.length() > 1) {
                                displayLabel.setText(currentText.substring(0, currentText.length() - 1));
                            } else {
                                displayLabel.setText("0");
                            }
                        }
                        else if (buttonValue == "=") {
                            if(A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);
                                
                                if (operator == "+") {
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                }
                                else if (operator == "-") {
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                }
                                else if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                }
                                else if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }                            
                                clearAll();
                            }
                        }
                        else if ("+-×÷√".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if (buttonValue == "CE") {
                            clearEntry();
                        }
                        else if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        }
                    }
                    else if (Arrays.asList(bottomSymbols).contains(buttonValue)) {
                        if (buttonValue == "1/x") {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText("0");
                            }
                            else {
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                numDisplay = 1/numDisplay;
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                        else if (buttonValue == "x²") {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText("0");
                            }
                            else {
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                numDisplay = Math.pow(numDisplay, 2);
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                        else if (buttonValue == "√") {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText("0");
                            }
                            else {
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                numDisplay = Math.sqrt(numDisplay);
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                    }
                    else {
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if (buttonValue == "+/-") {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText("0");
                            }
                            else {
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                numDisplay *= -1;
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                                secLabel.setText(displayLabel.getText() + buttonValue);
                                
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);
        }
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    void clearEntry() {
        displayLabel.setText("0");
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
