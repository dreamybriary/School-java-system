import javax.swing.*;
import java.awt.*;

public class CreateWindow extends JFrame {

    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JTextField txtOTP;

    private JButton btnSendOTP;
    private JButton btnVerifyOTP;

    private LoginWindow loginWindow;

    public CreateWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
        initialize();
    }

    private void initialize() {
        setTitle("BN University | Sign Up");
        setSize(800, 600); // full size
        setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        // Full-screen background image
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\PersonalPC\\Downloads\\598713188_883435454342700_7772630111758139937_n.png");
        Image bgImg = bgIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel lblBackground = new JLabel(new ImageIcon(bgImg));
        lblBackground.setBounds(0, 0, getWidth(), getHeight());
        getContentPane().add(lblBackground);
        lblBackground.setLayout(null); // so we can add form on top

        // Centered form panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds((getWidth() - 400) / 2, (getHeight() - 350) / 2, 400, 350);
        formPanel.setBackground(new Color(255, 255, 255, 230)); // semi-transparent
        formPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        lblBackground.add(formPanel);

        // Title
        JLabel lblTitle = new JLabel("Create New Account", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(0, 20, 400, 30);
        formPanel.add(lblTitle);

        // Username
        JLabel lblUsername = new JLabel("Full Name:");
        lblUsername.setBounds(40, 70, 100, 25);
        formPanel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 70, 200, 25);
        formPanel.add(txtUsername);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 110, 100, 25);
        formPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 110, 200, 25);
        formPanel.add(txtEmail);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40, 150, 100, 25);
        formPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 150, 200, 25);
        formPanel.add(txtPassword);

        // OTP fields (hidden initially)
        JLabel lblOTP = new JLabel("Enter OTP:");
        lblOTP.setBounds(40, 190, 100, 25);
        lblOTP.setVisible(false);
        formPanel.add(lblOTP);

        txtOTP = new JTextField();
        txtOTP.setBounds(150, 190, 100, 25);
        txtOTP.setVisible(false);
        formPanel.add(txtOTP);

        btnSendOTP = new JButton("Send OTP");
        btnSendOTP.setBounds(260, 190, 90, 25);
        formPanel.add(btnSendOTP);

        btnVerifyOTP = new JButton("Verify OTP");
        btnVerifyOTP.setBounds(150, 230, 120, 25);
        btnVerifyOTP.setVisible(false);
        formPanel.add(btnVerifyOTP);

        // Action listeners
        btnSendOTP.addActionListener(e -> sendOTP(lblOTP));
        btnVerifyOTP.addActionListener(e -> verifyAndCreateAccount());
    }

    private void sendOTP(JLabel lblOTP) {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an email first!");
            return;
        }

        if (SendOTP.sendOTP(email)) {
            JOptionPane.showMessageDialog(this, "OTP sent to email!");
            lblOTP.setVisible(true);
            txtOTP.setVisible(true);
            btnVerifyOTP.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to send OTP!");
        }
    }

    private void verifyAndCreateAccount() {
        String enteredOTP = txtOTP.getText().trim();

        if (!SendOTP.verifyOTP(enteredOTP)) {
            JOptionPane.showMessageDialog(this, "Incorrect OTP!");
            return;
        }

        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if (Queries.emailExist(email, username)) {
            JOptionPane.showMessageDialog(this, "Email or Username already registered!");
            return;
        }

        if (Queries.createAccount(username, email, password)) {
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            new LoginWindow().showWindow();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create account!");
        }
    }

    public void showWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateWindow(null).showWindow());
    }
}
