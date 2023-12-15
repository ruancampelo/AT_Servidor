package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {

        Servidor();
    }

    private static void Servidor() {

        JFrame frame = new JFrame("Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 330);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JTextField chave = new JTextField();
        JTextField result = new JTextField();
        result.disable();


        JLabel label1 = new JLabel("Chave: ");
        label1.setFont((new Font("Arial", Font.BOLD, 35)));

        JLabel label2 = new JLabel("Resultado: ");
        label1.setFont((new Font("Arial", Font.BOLD, 35)));


        chave.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextFieldValue();
            }

            private void updateTextFieldValue() {
                // Obtém e imprime o valor do JTextField em tempo real
                System.out.println("Valor em tempo real: " + chave.getText());
            }
        });

        panel.add(label1);
        panel.add(chave);
        panel.add(label2);
        panel.add(result);

        frame.add(panel);
        frame.setVisible(true);


        port(8080);
        post("/api", (req, res) -> {
            String corpoRequisicao = req.body();
            System.out.println("Corpo json: "+ corpoRequisicao);

            JsonElement jsonElement = JsonParser.parseString(corpoRequisicao);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if(jsonObject.get("dados").getAsString().equals(chave.getText())){
                result.setText("ACK");
                return "{\"resultado\": ACK}";

            } else {
                result.setText("NACK");
                return "{\"resultado\": NACK}";
            }
        });
    }
}