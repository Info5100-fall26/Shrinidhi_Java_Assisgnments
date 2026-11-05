import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 5050;
    private static final Path USERS_FILE = Paths.get("users.txt");
    private static final Path HISTORY_FILE = Paths.get("chat-history.txt");
    private static final Map<String, String> users = new ConcurrentHashMap<>();
    private static final Map<String, ClientHandler> online = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        loadUsers();
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Chat server running on port " + PORT);
            while (true) {
                Socket socket = server.accept();
                new Thread(new ClientHandler(socket), "chat-client").start();
            }
        }
    }

    private static synchronized void loadUsers() throws IOException {
        if (!Files.exists(USERS_FILE)) Files.createFile(USERS_FILE);
        for (String line : Files.readAllLines(USERS_FILE, StandardCharsets.UTF_8)) {
            String[] parts = line.split("\\t", 2);
            if (parts.length == 2) users.put(parts[0], parts[1]);
        }
    }

    private static synchronized boolean register(String username, String password) throws IOException {
        if (!username.matches("[A-Za-z0-9_]{3,20}") || password.length() < 4 || users.containsKey(username))
            return false;
        String hash = hash(password);
        users.put(username, hash);
        Files.writeString(USERS_FILE, username + "\t" + hash + System.lineSeparator(),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return true;
    }

    private static String hash(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
    }

    private static String enc(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
    private static String dec(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }

    private static void broadcast(String sender, String message) {
        String packet = "MSG|" + enc(sender) + "|" + enc(message);
        for (ClientHandler client : online.values()) client.send(packet);
        String record = java.time.LocalDateTime.now() + " [" + sender + "] " + message + System.lineSeparator();
        try { Files.writeString(HISTORY_FILE, record, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND); }
        catch (IOException e) { System.err.println("Could not save chat history: " + e.getMessage()); }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private String username;

        ClientHandler(Socket socket) { this.socket = socket; }

        void send(String line) { if (out != null) out.println(line); }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                String line;
                while ((line = in.readLine()) != null) {
                    String[] p = line.split("\\|", -1);
                    if (p.length == 3 && p[0].equals("SIGNUP")) {
                        boolean ok = register(dec(p[1]), dec(p[2]));
                        send(ok ? "OK|Account created. You can log in." : "ERROR|Username must be unique (3-20 letters/numbers/underscores) and password at least 4 characters.");
                    } else if (p.length == 3 && p[0].equals("LOGIN")) {
                        String user = dec(p[1]), pass = dec(p[2]);
                        if (!Objects.equals(users.get(user), hash(pass))) {
                            send("ERROR|Invalid username or password.");
                        } else if (online.containsKey(user)) {
                            send("ERROR|This user is already logged in.");
                        } else {
                            username = user;
                            online.put(username, this);
                            send("WELCOME|" + enc(username));
                            broadcast("SERVER", username + " joined the chat.");
                        }
                    } else if (p.length == 2 && p[0].equals("MSG")) {
                        if (username == null) send("ERROR|Please log in first.");
                        else {
                            String message = dec(p[1]).trim();
                            if (!message.isEmpty() && message.length() <= 1000) broadcast(username, message);
                        }
                    } else if (p.length == 1 && p[0].equals("QUIT")) break;
                }
            } catch (Exception e) {
                System.err.println("Client connection ended: " + e.getMessage());
            } finally {
                if (username != null) {
                    online.remove(username, this);
                    broadcast("SERVER", username + " left the chat.");
                }
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }
}
