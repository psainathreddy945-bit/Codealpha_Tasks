import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class AIChatbot extends JFrame implements ActionListener {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton, clearButton, exitButton;

    private HashMap<String, String> faq;
    private final String FILE_NAME = "knowledge.txt";

    public AIChatbot() {

        faq = new HashMap<>();

        loadKnowledge();

        // Default knowledge
        faq.putIfAbsent("hello", "Hello! How can I help you?");
        faq.putIfAbsent("hi", "Hi! Nice to meet you.");
        faq.putIfAbsent("java", "Java is an object-oriented programming language.");
        faq.putIfAbsent("python", "Python is a high-level programming language.");
        faq.putIfAbsent("ai", "Artificial Intelligence enables machines to think and learn.");
        faq.putIfAbsent("machine learning", "Machine Learning is a subset of AI.");
        faq.putIfAbsent("data science", "Data Science extracts insights from data.");
        faq.putIfAbsent("thank you", "You're welcome!");
        faq.putIfAbsent("bye", "Goodbye! Have a nice day.");

        setTitle("AI Chatbot - CodeAlpha");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        inputField.addActionListener(this);

        sendButton = new JButton("Send");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        sendButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        chatArea.append("Bot: Hello! I am an AI Chatbot.\n");
        chatArea.append("Bot: Ask me anything.\n\n");

        setVisible(true);
    }

    private String getResponse(String input) {

        input = input.toLowerCase().trim();

        // NLP-like preprocessing
        input = input.replace("?", "");
        input = input.replace(".", "");

        // Date
        if (input.contains("date")) {
            return "Today's Date: " +
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        // Time
        if (input.contains("time")) {
            return "Current Time: " +
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("hh:mm a"));
        }

        // Greeting
        if (input.contains("how are you")) {
            return "I am fine. Thank you for asking.";
        }

        // Search FAQ
        for (String key : faq.keySet()) {
            if (input.contains(key)) {
                return faq.get(key);
            }
        }

        return null;
    }

    private void trainBot(String question) {

        String answer = JOptionPane.showInputDialog(
                this,
                "I don't know the answer.\nPlease teach me:");

        if (answer != null && !answer.trim().isEmpty()) {

            faq.put(question.toLowerCase(), answer);

            saveKnowledge(question.toLowerCase(), answer);

            chatArea.append("Bot: Thank you! I learned something new.\n\n");
        }
    }

    private void saveKnowledge(String question, String answer) {

        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            bw.write(question + "=" + answer);
            bw.newLine();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving knowledge.");
        }
    }

    private void loadKnowledge() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    faq.put(parts[0], parts[1]);
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading knowledge.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sendButton ||
                e.getSource() == inputField) {

            String userText = inputField.getText().trim();

            if (userText.isEmpty()) {
                return;
            }

            chatArea.append("You: " + userText + "\n");

            String response = getResponse(userText);

            if (response != null) {
                chatArea.append("Bot: " + response + "\n\n");
            } else {

                chatArea.append(
                        "Bot: Sorry, I don't know the answer.\n");

                trainBot(userText);
            }

            inputField.setText("");
        }

        if (e.getSource() == clearButton) {
            chatArea.setText("");
        }

        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new AIChatbot());
    }
}