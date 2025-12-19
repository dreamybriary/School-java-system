import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class enrollmentDashboard extends JFrame {
    private JPanel contentPane;
    private JLabel lblWelcome;
    private JTable table;
    private DefaultTableModel tableModel;
    private String userEmail;
    private int userId;

    public enrollmentDashboard(String email) {
        this.userEmail = email;

        // Ensure user exists in DB
        checkOrCreateUser();

        initialize();
        loadApplications(); // Load from DB on startup
    }

    private void checkOrCreateUser() {
        userId = Queries.getUserId(userEmail);
        if (userId == -1) {
            String defaultUsername = userEmail.split("@")[0];
            String defaultPassword = "1234";
            boolean created = Queries.createAccount(defaultUsername, userEmail, defaultPassword);
            if (created) {
                userId = Queries.getUserId(userEmail);
                System.out.println("User created: " + defaultUsername);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create user. Cannot continue.");
                System.exit(0);
            }
        }
    }

    private void initialize() {
        setTitle("BN University | Student Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel(null);
        setContentPane(contentPane);

        // Background image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\PersonalPC\\Downloads\\background.jpg");
        Image img = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);

        JLabel lblBackground = new JLabel(new ImageIcon(img));
        lblBackground.setBounds(0, 0, 800, 600);
        contentPane.add(lblBackground);
        lblBackground.setLayout(null);

        // Welcome label
        lblWelcome = new JLabel(
                "Welcome, " + (userEmail != null ? userEmail : "Student") + "!",
                SwingConstants.CENTER
        );
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblWelcome.setBounds(0, 20, 800, 40);
        lblBackground.add(lblWelcome);

        // Buttons
        int btnWidth = 200;
        int btnHeight = 40;
        int x = (800 - btnWidth) / 2;
        int startY = 80;
        int gap = 10;

        JButton btnSubmit = new JButton("Submit Application");
        btnSubmit.setBounds(x, startY, btnWidth, btnHeight);
        lblBackground.add(btnSubmit);

        JButton btnView = new JButton("View Application");
        btnView.setBounds(x, startY + (btnHeight + gap), btnWidth, btnHeight);
        lblBackground.add(btnView);

        JButton btnReview = new JButton("Review Application");
        btnReview.setBounds(x, startY + 2 * (btnHeight + gap), btnWidth, btnHeight);
        lblBackground.add(btnReview);

        JButton btnWithdraw = new JButton("Withdraw Application");
        btnWithdraw.setBounds(x, startY + 3 * (btnHeight + gap), btnWidth, btnHeight);
        lblBackground.add(btnWithdraw);

        // Table with Status column
        tableModel = new DefaultTableModel(
                new Object[]{"Application ID", "Name", "Course", "Year Level", "Date Submitted", "Status"},
                0
        );

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 280, 700, 250);
        lblBackground.add(scrollPane);

        // Button actions
        btnSubmit.addActionListener(e -> openSubmitForm());
        btnView.addActionListener(e -> viewApplication());
        btnReview.addActionListener(e -> reviewApplication());
        btnWithdraw.addActionListener(e -> withdrawApplication());
    }

    // ------------------- DATABASE INTEGRATION -------------------

    private void loadApplications() {
        tableModel.setRowCount(0);
        if (userId == -1) return;

        try {
            List<Application> apps = Queries.getApplicationsList(userId);
            for (Application app : apps) {
                tableModel.addRow(new Object[]{
                        app.getApplicationId(),
                        app.getName(),
                        app.getCourse(),
                        app.getYearLevel(),
                        app.getDateSubmitted().toString(),
                        app.getStatus()  // status
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSubmitForm() {
        JDialog formDialog = new JDialog(this, "Submit Application", true);
        formDialog.setSize(400, 350);
        formDialog.setLocationRelativeTo(this);
        formDialog.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 30, 120, 25);
        formDialog.getContentPane().add(lblName);

        JTextField txtName = new JTextField();
        txtName.setBounds(160, 30, 200, 25);
        formDialog.getContentPane().add(txtName);

        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setBounds(30, 70, 120, 25);
        formDialog.getContentPane().add(lblCourse);

        // Hardcoded courses
        String[] courses = {
            "Bachelor of Science in Computer Science",
            "Bachelor of Science in Information Technology",
            "Bachelor of Science in Nursing",
            "Bachelor of Business Administration",
            "Bachelor of Arts in Psychology",
            "Bachelor of Engineering",
            "Bachelor of Science in Computer"
        };

        JComboBox<String> comboCourse = new JComboBox<>(courses);
        comboCourse.setBounds(160, 70, 200, 25);
        comboCourse.setMaximumRowCount(5); // scrollable
        formDialog.getContentPane().add(comboCourse);

        JLabel lblYear = new JLabel("Year Level:");
        lblYear.setBounds(30, 110, 120, 25);
        formDialog.getContentPane().add(lblYear);

        String[] yearLevels = {"Freshman", "2nd Year", "3rd Year", "4th Year"};
        JComboBox<String> comboYear = new JComboBox<>(yearLevels);
        comboYear.setBounds(160, 110, 200, 25);
        comboYear.setMaximumRowCount(4);
        formDialog.getContentPane().add(comboYear);

        JButton btnSubmitForm = new JButton("Submit");
        btnSubmitForm.setBounds(140, 170, 100, 30);
        formDialog.getContentPane().add(btnSubmitForm);

        btnSubmitForm.addActionListener(e -> {
            String name = txtName.getText().trim();
            String course = (String) comboCourse.getSelectedItem();
            String year = (String) comboYear.getSelectedItem();

            if (name.isEmpty() || course.isEmpty() || year.isEmpty()) {
                JOptionPane.showMessageDialog(formDialog, "Please fill all fields!");
                return;
            }

            int courseId = Queries.getCourseId(course); // still works if you have DB mapping
            if (courseId == -1) {
                JOptionPane.showMessageDialog(formDialog, "Selected course not found!");
                return;
            }

            String appId = Queries.generateApplicationId();
            boolean success = Queries.submitApplication(appId, userId, courseId, year);

            if (success) {
                JOptionPane.showMessageDialog(formDialog, "Application submitted!");
                formDialog.dispose();
                loadApplications();
            } else {
                JOptionPane.showMessageDialog(formDialog, "Failed to submit application!");
            }
        });

        formDialog.setVisible(true);
    }

    private void viewApplication() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Application ID: " + tableModel.getValueAt(row, 0) +
                            "\nName: " + tableModel.getValueAt(row, 1) +
                            "\nCourse: " + tableModel.getValueAt(row, 2) +
                            "\nYear Level: " + tableModel.getValueAt(row, 3) +
                            "\nDate Submitted: " + tableModel.getValueAt(row, 4) +
                            "\nStatus: " + tableModel.getValueAt(row, 5),
                    "View Application",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(this, "Please select an application to view!");
        }
    }

    private void reviewApplication() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String currentStatus = tableModel.getValueAt(row, 5).toString();
            String newStatus = JOptionPane.showInputDialog(
                    this,
                    "Enter new status:",
                    currentStatus != null ? currentStatus : "Submitted"
            );

            if (newStatus != null && !newStatus.isEmpty()) {
                String appId = tableModel.getValueAt(row, 0).toString();
                boolean updated = Queries.updateApplicationStatus(appId, newStatus); // DB update
                if (updated) {
                    tableModel.setValueAt(newStatus, row, 5); // update table
                    JOptionPane.showMessageDialog(this, "Status updated to: " + newStatus);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update status!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an application to review!");
        }
    }

    private void withdrawApplication() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to withdraw this application?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                String appId = tableModel.getValueAt(row, 0).toString();
                if (Queries.withdrawApplication(appId)) {
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(this, "Application withdrawn successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to withdraw application!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an application to withdraw!");
        }
    }

    public void showWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new enrollmentDashboard("test@example.com").showWindow());
    }
}
