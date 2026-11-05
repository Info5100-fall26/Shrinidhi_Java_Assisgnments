import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ChatClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    private JFrame frame;
    private JTextArea conversation;
    private JTextField messageField;
    private JLabel statusLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClient().showAuthentication());
    }

    private String enc(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
    private String dec(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }

    private void connect() throws IOException {
        socket = new Socket(HOST, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    private void showAuthentication() {
        JFrame auth = new JFrame("INFO5100 Chat - Sign in");
        auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auth.setSize(420, 330);
        auth.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(24, 32, 24, 32));
        panel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(7, 5, 7, 5);

        JLabel title = new JLabel("Welcome to INFO5100 Chat", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 21));
        panel.add(title, c);

        c.gridy++; JLabel userLabel = new JLabel("Username"); c.gridwidth = 1; panel.add(userLabel, c);
        c.gridx = 1; JTextField userField = new JTextField(17); panel.add(userField, c);
        c.gridx = 0; c.gridy++; JLabel passLabel = new JLabel("Password"); panel.add(passLabel, c);
        c.gridx = 1; JPasswordField passField = new JPasswordField(17); panel.add(passField, c);

        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        JLabel hint = new JLabel("Create an account or sign in to start chatting.", SwingConstants.CENTER);
        hint.setForeground(new Color(90, 100, 115)); panel.add(hint, c);

        c.gridy++; JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 2));
        JButton login = new JButton("Log in");
        JButton signup = new JButton("Sign up");
        buttons.add(login); buttons.add(signup); panel.add(buttons, c);

        Runnable connectIfNeeded = () -> {
            if (socket == null || socket.isClosed()) {
                try { connect(); } catch (IOException ex) {
                    JOptionPane.showMessageDialog(auth, "Cannot connect to server. Start ChatServer first.\\n" + ex.getMessage(),
                            "Connection error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        };

        login.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());
            if (u.isEmpty() || p.isEmpty()) { JOptionPane.showMessageDialog(auth, "Enter username and password."); return; }
            try {
                connectIfNeeded.run();
                out.println("LOGIN|" + enc(u) + "|" + enc(p));
                String response = in.readLine();
                if (response == null) throw new IOException("Server disconnected.");
                if (response.startsWith("WELCOME|")) {
                    username = dec(response.substring(8));
                    auth.dispose();
                    showChatWindow();
                    startReader();
                } else if (response.startsWith("ERROR|")) {
                    JOptionPane.showMessageDialog(auth, response.substring(6), "Login failed", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(auth, "Login error: " + ex.getMessage());
                closeConnection();
            }
        });

        signup.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());
            if (u.isEmpty() || p.isEmpty()) { JOptionPane.showMessageDialog(auth, "Enter a username and password."); return; }
            try {
                connectIfNeeded.run();
                out.println("SIGNUP|" + enc(u) + "|" + enc(p));
                String response = in.readLine();
                if (response == null) throw new IOException("Server disconnected.");
                String message = response.startsWith("OK|") ? response.substring(3) : response.substring(6);
                JOptionPane.showMessageDialog(auth, message);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(auth, "Sign-up error: " + ex.getMessage());
                closeConnection();
            }
        });

        auth.add(panel);
        auth.setVisible(true);
    }

    private void showChatWindow() {
        frame = new JFrame("INFO5100 Chat - " + username);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(650, 520);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(14, 14, 14, 14));
        root.setBackground(new Color(245, 247, 250));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Chat room");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        statusLabel = new JLabel("Signed in as " + username + "  •  Connected");
        statusLabel.setForeground(new Color(70, 110, 90));
        header.add(title, BorderLayout.WEST);
        header.add(statusLabel, BorderLayout.EAST);

        conversation = new JTextArea();
        conversation.setEditable(false);
        conversation.setLineWrap(true);
        conversation.setWrapStyleWord(true);
        conversation.setFont(new Font("SansSerif", Font.PLAIN, 14));
        conversation.setMargin(new Insets(12, 12, 12, 12));
        JScrollPane scroll = new JScrollPane(conversation);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 232)));

        JPanel composer = new JPanel(new BorderLayout(8, 0));
        composer.setOpaque(false);
        messageField = new JTextField();
        JButton send = new JButton("Send");
        send.setPreferredSize(new Dimension(90, 38));
        composer.add(messageField, BorderLayout.CENTER);
        composer.add(send, BorderLayout.EAST);
        JLabel note = new JLabel("Messages are visible to everyone connected to this chat room.");
        note.setForeground(Color.GRAY);
        JPanel bottom = new JPanel(new BorderLayout(0, 6));
        bottom.setOpaque(false);
        bottom.add(composer, BorderLayout.CENTER);
        bottom.add(note, BorderLayout.SOUTH);

        ActionListener sendAction = e -> sendMessage();
        send.addActionListener(sendAction);
        messageField.addActionListener(sendAction);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Log out and close the chat?", "Log out",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (out != null) out.println("QUIT");
                    closeConnection();
                    frame.dispose();
                }
            }
        });

        root.add(header, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);
        frame.setContentPane(root);
        frame.setVisible(true);
        messageField.requestFocusInWindow();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) return;
        if (message.length() > 1000) {
            JOptionPane.showMessageDialog(frame, "Messages must be 1,000 characters or fewer.");
            return;
        }
        out.println("MSG|" + enc(message));
        messageField.setText("");
    }

    private void startReader() {
        Thread reader = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("MSG|")) {
                        String[] p = line.split("\\|", 3);
                        String sender = dec(p[1]);
                        String message = dec(p[2]);
                        SwingUtilities.invokeLater(() -> {
                            conversation.append(sender + ": " + message + "\\n");
                            conversation.setCaretPosition(conversation.getDocument().getLength());
                        });
                    } else if (line.startsWith("ERROR|")) {
                        String errorMessage = line.substring(6);
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, errorMessage));
                    }
                }
            } catch (IOException ignored) {
            } finally {
                SwingUtilities.invokeLater(() -> {
                    if (statusLabel != null) statusLabel.setText("Disconnected");
                });
            }
        }, "chat-reader");
        reader.setDaemon(true);
        reader.start();
    }

    private void closeConnection() {
        try { if (socket != null) socket.close(); } catch (IOException ignored) {}
    }
}
