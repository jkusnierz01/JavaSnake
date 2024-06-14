package main.Frame;

import main.Model.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The ResultingFrame class represents a JFrame where the player can input their name
 * to save their result.
 */
public class ResultingFrame extends JFrame implements ActionListener {
    private final JTextField name;
    private final int result;
    private final JButton confirmButton;

    /**
     * Constructs a ResultingFrame object with the specified result.
     *
     * @param result the result of the player
     */
    public ResultingFrame(int result) {
        setSize(300, 300);
        setLayout(new GridLayout(3, 1));
        this.result = result;
        JLabel text = new JLabel("Set your name");
        add(text);
        name = new JTextField();
        add(name);
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this);
        add(confirmButton);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Handles the action event when the confirm button is pressed.
     * Saves the result with the player's name and exits the application.
     *
     * @param e the ActionEvent triggered by the confirm button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == confirmButton) {
            var newResult = new Result(result, name.getText());
            try {
                newResult.saveResult();
                dispose();
                System.exit(0);   // Exit the application
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
