import javax.swing.*;
import java.awt.*;

public class LoginWindow {

    private JFrame frame;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    public LoginWindow() {
        initialize();
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    private void initialize() {
        // Main frame
        frame = new JFrame("BN University | Login");
        frame.setSize(800, 600); // full window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        // Load and scale full-screen background image
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\PersonalPC\\Downloads\\597153512_885597733898607_2571718313204384359_n.jpg");
        Image bgImg = bgIcon.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        JLabel lblBackground = new JLabel(new ImageIcon(bgImg));
        lblBackground.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        lblBackground.setLayout(null);
        frame.getContentPane().add(lblBackground);

        // University title at top
        JLabel lblTitle = new JLabel("Biringan National University", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(0, 50, frame.getWidth(), 50);
        lblBackground.add(lblTitle);

        // Semi-transparent login box panel
        JPanel loginBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 153)); // 60% opacity white
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // rounded corners
                g2d.dispose();
            }
        };
        loginBox.setBounds(250, 200, 300, 220); // centered
        loginBox.setLayout(null);
        loginBox.setOpaque(false); // needed for transparency
        lblBackground.add(loginBox);

        // Login box title
        JLabel lblLoginTitle = new JLabel("Account Login", SwingConstants.CENTER);
        lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLoginTitle.setBounds(0, 10, 300, 30);
        loginBox.add(lblLoginTitle);

        // Email field
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 60, 80, 25);
        loginBox.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(100, 60, 170, 25);
        loginBox.add(txtEmail);

        // Password field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(20, 100, 80, 25);
        loginBox.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 100, 170, 25);
        loginBox.add(txtPassword);

        // Login button
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(60, 150, 80, 30);
        styleButton(btnLogin);
        loginBox.add(btnLogin);

        // Sign Up button
        JButton btnCreate = new JButton("Sign Up");
        btnCreate.setBounds(160, 150, 80, 30);
        styleButton(btnCreate);
        loginBox.add(btnCreate);

        // Button actions
        btnLogin.addActionListener(e -> login());
        btnCreate.addActionListener(e -> {
            frame.setVisible(false);
            new CreateWindow(null).showWindow();
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255));
            }
        });
    }

    private void login() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter email and password.");
            return;
        }

        String username = Queries.checkLogin(email, password);

        if (username != null) {
            JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!");
            frame.dispose();
            new DashboardWindow(email, username).showWindow();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid email or password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow().showWindow());
    }
}
