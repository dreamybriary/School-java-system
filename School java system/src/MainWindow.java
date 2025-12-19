import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainWindow frame = new MainWindow();
                frame.showWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainWindow() {
        setTitle("BN University | Welcome");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 599);
        setResizable(false);
        setLocationRelativeTo(null); // center on screen

        contentPane = new JPanel(null);
        setContentPane(contentPane);

        // Load and scale background image
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\PersonalPC\\Downloads\\597153512_885597733898607_2571718313204384359_n.jpg");
        Image bgImg = bgIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImg));
        background.setBounds(0, 0, getWidth(), getHeight());
        contentPane.add(background);

        // Transparent overlay panel for buttons and labels
        JPanel overlay = new JPanel(null);
        overlay.setOpaque(false);
        overlay.setBounds(0, 0, getWidth(), getHeight());
        background.add(overlay);

        // University name
        JLabel lblTitle = new JLabel("Biringan National University", SwingConstants.CENTER);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setBounds(0, 73, getWidth(), 40);
        overlay.add(lblTitle);

        // University tagline
        JLabel lblTagline = new JLabel("Shining Through Knowledge, Leading with Purpose!", SwingConstants.CENTER);
        lblTagline.setForeground(new Color(255, 255, 0));
        lblTagline.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTagline.setBounds(0, 124, getWidth(), 30);
        overlay.add(lblTagline);

        // Button dimensions
        int btnWidth = 150;
        int btnHeight = 45;
        int spacing = 20;
        int startY = 150;

        // Login button
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(325, 222, btnWidth, btnHeight);
        styleButton(btnLogin);
        overlay.add(btnLogin);

        // Sign Up button
        JButton btnCreateAccount = new JButton("Sign Up");
        btnCreateAccount.setBounds(325, 289, btnWidth, btnHeight);
        styleButton(btnCreateAccount);
        overlay.add(btnCreateAccount);

        // Exit button
        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(325, 363, btnWidth, btnHeight);
        styleButton(btnExit);
        overlay.add(btnExit);

        // Logo image
        JLabel lblLogo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\PersonalPC\\Downloads\\Biringan1LOGO.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoImg));
        lblLogo.setBounds(10, 10, 100, 100);
        overlay.add(lblLogo);

        // Button actions
        btnLogin.addActionListener(e -> {
            dispose();
            new LoginWindow().showWindow();
        });

        btnCreateAccount.addActionListener(e -> {
            dispose();
            new CreateWindow(null).showWindow();
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
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

    public void showWindow() {
        setVisible(true);
    }
}
