import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Create extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField createUsernameField;
    private JPasswordField createPasswordField;
    private Connection connection;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Create frame = new Create();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Create() {
        // Establish database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Create.this, "Failed to connect to the database.");
            System.exit(1);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK); // Jet Black Background
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel createAccountLabel = new JLabel("Create Account");
        createAccountLabel.setForeground(Color.WHITE); // White Text
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createAccountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        createAccountLabel.setBounds(110, 20, 220, 30);
        contentPane.add(createAccountLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // White Text
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(50, 80, 100, 20);
        contentPane.add(usernameLabel);

        createUsernameField = new JTextField();
        createUsernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        createUsernameField.setBounds(160, 80, 200, 25);
        contentPane.add(createUsernameField);
        createUsernameField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // White Text
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(50, 120, 100, 20);
        contentPane.add(passwordLabel);

        createPasswordField = new JPasswordField();
        createPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        createPasswordField.setBounds(160, 120, 200, 25);
        contentPane.add(createPasswordField);
        createPasswordField.setColumns(10);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setForeground(Color.WHITE); // White Text
        signUpButton.setBackground(new Color(30, 144, 255)); // Dodger Blue Background
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        signUpButton.setBounds(160, 170, 120, 30);
        contentPane.add(signUpButton);

        // ActionListener for the signUpButton
        // ActionListener for the signUpButton
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve username and password
                String username = createUsernameField.getText();
                String password = new String(createPasswordField.getPassword());

                // Check if both fields are filled
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Insert new user into database
                    if (insertUser(username, password)) {
                        // If insertion is successful, show success message
                        JOptionPane.showMessageDialog(Create.this, "User created successfully.");
                        // Clear all text fields when user is successfully signed up
                        createUsernameField.setText("");
                        createPasswordField.setText("");
                        Options optionsPage = new Options();
                        optionsPage.setVisible(true);
                        dispose(); 
                    } else {
                        // If insertion fails, show error message
                        JOptionPane.showMessageDialog(Create.this, "Failed to create user.");
                    }
                } else {
                    // If either or both fields are empty, display an error message
                    JOptionPane.showMessageDialog(Create.this, "Please fill in both username and password fields.");
                }
            }
        });
    }

    private boolean insertUser(String username, String password) {
        try {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            // Check if the exception is due to duplicate entry
            if (e.getErrorCode() == 1062) { // MySQL error code for duplicate entry
                JOptionPane.showMessageDialog(Create.this, "Username already exists. Please choose a different username.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
}
