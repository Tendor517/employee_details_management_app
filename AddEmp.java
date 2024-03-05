import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddEmp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField fnametext;
    private JTextField dobtext;
    private JTextField dojtext;
    private JTextField salarytext;
    private JComboBox<String> workComboBox;
    private JComboBox<String> edLevelComboBox;
    private JTextField textField;
    private Connection conn;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddEmp frame = new AddEmp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AddEmp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400); // Adjusted width to 600
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255)); // Light blue background color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Labels, text fields, and combo boxes are added here
        JLabel title = new JLabel("Employee Registration Form");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 0, 139)); // Dark blue text color
        title.setBounds(140, 11, 320, 30);
        contentPane.add(title);

        JLabel fname = new JLabel("Full Name:");
        fname.setFont(new Font("Arial", Font.BOLD, 14));
        fname.setForeground(new Color(70, 130, 180)); // Navy blue text color
        fname.setBounds(31, 87, 100, 20);
        contentPane.add(fname);

        fnametext = new JTextField();
        fnametext.setBounds(253, 88, 220, 20);
        contentPane.add(fnametext);
        fnametext.setColumns(10);

        JLabel dob = new JLabel("Date of Birth (YYYY-MM-DD):");
        dob.setFont(new Font("Arial", Font.BOLD, 14));
        dob.setForeground(new Color(70, 130, 180)); // Navy blue text color
        dob.setBounds(31, 118, 200, 20);
        contentPane.add(dob);

        dobtext = new JTextField();
        dobtext.setColumns(10);
        dobtext.setBounds(253, 119, 150, 20);
        contentPane.add(dobtext);

        JLabel workmode = new JLabel("Mode of Work:");
        workmode.setFont(new Font("Arial", Font.BOLD, 14));
        workmode.setForeground(new Color(70, 130, 180)); // Navy blue text color
        workmode.setBounds(31, 149, 120, 20);
        contentPane.add(workmode);

        String[] works = { "Remote", "Hybrid", "On-Site", "Flexible" };
        workComboBox = new JComboBox<>(works);
        workComboBox.setBounds(253, 150, 220, 20);
        contentPane.add(workComboBox);

        JLabel edlevel = new JLabel("Education Level:");
        edlevel.setFont(new Font("Arial", Font.BOLD, 14));
        edlevel.setForeground(new Color(70, 130, 180)); // Navy blue text color
        edlevel.setBounds(31, 180, 120, 20);
        contentPane.add(edlevel);

        String[] levels = { "High School", "Bachelors", "Masters", "PhD" };
        edLevelComboBox = new JComboBox<>(levels);
        edLevelComboBox.setBounds(253, 181, 220, 20);
        contentPane.add(edLevelComboBox);

        JLabel doj = new JLabel("Date of Joining (YYYY-MM-DD):");
        doj.setFont(new Font("Arial", Font.BOLD, 14));
        doj.setForeground(new Color(70, 130, 180)); // Navy blue text color
        doj.setBounds(31, 236, 220, 20);
        contentPane.add(doj);

        dojtext = new JTextField();
        dojtext.setColumns(10);
        dojtext.setBounds(253, 237, 150, 20);
        contentPane.add(dojtext);

        JLabel salary = new JLabel("Salary (INR)");
        salary.setFont(new Font("Arial", Font.BOLD, 14));
        salary.setForeground(new Color(70, 130, 180)); // Navy blue text color
        salary.setBounds(31, 267, 89, 20);
        contentPane.add(salary);

        salarytext = new JTextField();
        salarytext.setColumns(10);
        salarytext.setBounds(253, 268, 150, 20);
        contentPane.add(salarytext);

        JLabel jobtitle = new JLabel("Job Titlle");
        jobtitle.setFont(new Font("Arial", Font.BOLD, 14));
        jobtitle.setForeground(new Color(70, 130, 180)); // Navy blue text color
        jobtitle.setBounds(31, 211, 89, 14);
        contentPane.add(jobtitle);

        textField = new JTextField();
        textField.setBounds(253, 212, 246, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        // Add Employee Button
        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setFont(new Font("Arial", Font.BOLD, 14));
        addEmployeeButton.setBackground(new Color(70, 130, 180)); // Navy blue button color
        addEmployeeButton.setForeground(Color.WHITE); // White text color
        addEmployeeButton.setBounds(173, 299, 150, 30);
        contentPane.add(addEmployeeButton);
        // ActionListener for the Add Employee Button
        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the values from text fields and combo boxes
                String fullName = fnametext.getText();
                String dob = dobtext.getText();
                String workMode = (String) workComboBox.getSelectedItem();
                String educationLevel = (String) edLevelComboBox.getSelectedItem();
                String dateOfJoining = dojtext.getText();
                String salary = salarytext.getText();
                String jobTitle = textField.getText();

                // Prepare the SQL statement
                String sql = "INSERT INTO empdata (full_name, dob, work_mode, education_level, date_of_joining, salary, job_title) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try {
                    // Create a PreparedStatement object
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    // Set the values for the PreparedStatement parameters
                    pstmt.setString(1, fullName);
                    pstmt.setString(2, dob);
                    pstmt.setString(3, workMode);
                    pstmt.setString(4, educationLevel);
                    pstmt.setString(5, dateOfJoining);
                    pstmt.setString(6, salary);
                    pstmt.setString(7, jobTitle);
                    // Execute the SQL statement
                    pstmt.executeUpdate();
                    // Close the PreparedStatement
                    pstmt.close();
                    // Clear all text fields after successful insertion
                    clearAllTextFields();
                    JOptionPane.showMessageDialog(null, "Employee added successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: Failed to add employee.");
                }
            }
        });

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setBackground(Color.ORANGE.RED); // Orangyred background color
        exitButton.setForeground(Color.WHITE); // White text color
        // Calculate the position based on frame size
        int buttonWidth = 89;
        int buttonHeight = 23;
        int frameWidth = 600; // Adjust this value if the frame width changes
        int frameHeight = 400; // Adjust this value if the frame height changes
        int buttonX = frameWidth - buttonWidth - 20; // 20 is padding from the right
        int buttonY = frameHeight - buttonHeight - 40; // 40 is padding from the bottom

        exitButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        contentPane.add(exitButton);

        // ActionListener for the Exit Button
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 Options optionsPage = new Options();
                 optionsPage.setVisible(true);
                 dispose();// Close the current frame that is AddEmp file
            }
        });

        // Create a connection to the database
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "root");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Failed to connect to the database.");
        }
    }

    // Method to clear all text fields
    private void clearAllTextFields() {
        fnametext.setText("");
        dobtext.setText("");
        dojtext.setText("");
        salarytext.setText("");
        textField.setText("");
        // Reset combo box selections
        workComboBox.setSelectedIndex(0);
        edLevelComboBox.setSelectedIndex(0);
    }
}
