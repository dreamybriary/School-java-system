import javax.swing.*;
import java.awt.*;

public class DashboardWindow extends JFrame {

    private JPanel contentPane;
    private String userEmail;
    private String username;

    public DashboardWindow(String email, String username) {
        this.userEmail = email;
        this.username = username;
        initialize();
    }

    private void initialize() {
        setTitle("BN University | Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create content pane
        contentPane = new JPanel(null);
        setContentPane(contentPane);

        // Load and scale background image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\PersonalPC\\Downloads\\597878372_830289386679263_5585286675610054155_n (1).png");
        Image img = originalIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(img));
        background.setBounds(0, 0, getWidth(), getHeight());
        contentPane.add(background);

        // Make a panel to hold buttons and labels on top of background
        JPanel overlay = new JPanel(null);
        overlay.setOpaque(false); // transparent panel
        overlay.setBounds(0, 0, getWidth(), getHeight());
        background.add(overlay);

        // Open Enrollment button
        JButton btnEnrollment = new JButton("Open Enrollment");
        btnEnrollment.setBounds(60, 276, 200, 40);
        btnEnrollment.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnEnrollment.addActionListener(e -> openEnrollment());
        overlay.add(btnEnrollment);

        // View Profile button
        JButton btnProfile = new JButton("View Profile");
        btnProfile.setBounds(60, 350, 200, 40);
        btnProfile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnProfile.addActionListener(e -> viewProfile());
        overlay.add(btnProfile);

        // Logout button
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(60, 422, 200, 40);
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogout.addActionListener(e -> logout());
        overlay.add(btnLogout);
    }

    private void openEnrollment() {
        enrollmentDashboard enrollment = new enrollmentDashboard(userEmail);
        enrollment.showWindow();
        this.dispose();
    }

    private void viewProfile() {
        JOptionPane.showMessageDialog(
            this,
            "User Profile: " + (username != null ? username : "Student") +
            "\nEmail: " + (userEmail != null ? userEmail : "-"),
            "Profile",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void logout() {
        dispose();
        LoginWindow login = new LoginWindow();
        login.showWindow();
    }

    public void showWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new DashboardWindow("test@example.com", "John Doe").showWindow()
        );
    }
}
