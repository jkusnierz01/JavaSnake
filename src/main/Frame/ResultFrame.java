package main.Frame;

import main.Model.ResultReader;

import javax.swing.*;
import java.io.FileNotFoundException;

public class ResultFrame extends JFrame {
    private JTextArea resultTextAre;

    public ResultFrame() throws FileNotFoundException {
        super("Results Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        // Inicjalizacja JTextArea
        resultTextAre = new JTextArea();
        resultTextAre.setEditable(false); // Ustawienie jako nieedytowalne, jeśli tylko do wyświetlania

        // Dodanie JScrollPane dla przewijania
        JScrollPane scrollPane = new JScrollPane(resultTextAre);
        add(scrollPane);

        setTextArea1();
        setVisible(true);
    }

    public void setTextArea1() throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name\tResult\t\n");
        var results = ResultReader.getAllResult();
        for (var result : results) {
            stringBuilder.append(result.getName()).append("\t").append(result.getResult()).append("\t\n");
        }
        resultTextAre.append(stringBuilder.toString());
    }
}
