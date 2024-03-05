import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usertext;
    private JPasswordField passtext;
    private Connection connection;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        // Establish database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Login.this, "Failed to connect to the database.");
            System.exit(1);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255)); // Light Blue Background
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel signin = new JLabel("<html><u>Sign In</u></html>");
        signin.setFont(new Font("Consolas", Font.BOLD, 24));
        signin.setBounds(170, 10, 123, 47);
        contentPane.add(signin);
        
        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setBounds(52, 70, 110, 20);
        contentPane.add(username);
        
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setBounds(52, 115, 110, 20);
        contentPane.add(password);
        
        usertext = new JTextField();
        usertext.setBounds(170, 70, 200, 25);
        contentPane.add(usertext);
        usertext.setColumns(10);
        
        passtext = new JPasswordField();
        passtext.setBounds(170, 115, 200, 25);
        contentPane.add(passtext);
        
        JButton signingin = new JButton("Sign in");
        signingin.setForeground(new Color(255, 255, 255)); // White Text
        signingin.setBackground(new Color(30, 144, 255)); // Dodger Blue Background
        signingin.setFont(new Font("Arial", Font.BOLD, 16));
        signingin.setBounds(280, 160, 90, 30);
        contentPane.add(signingin);
        
        signingin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check user credentials
                String username = usertext.getText();
                String password = new String(passtext.getPassword());

                if (checkCredentials(username, password)) {
                    // If credentials are correct, navigate to the Options page
                    Options optionsPage = new Options();
                    optionsPage.setVisible(true);
                    dispose(); // Close the login page
                } else {
                    // If credentials are incorrect, display an error message
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password.");
                }
            }
        });

        
        JButton createaccount = new JButton("Create");
        createaccount.setBackground(new Color(30, 144, 255)); // Dodger Blue Background
        createaccount.setForeground(new Color(255, 255, 255)); // White Text
        createaccount.setFont(new Font("Arial", Font.BOLD, 16));
        createaccount.setBounds(280, 210, 90, 30);
        contentPane.add(createaccount);
        
        createaccount.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Instantiate the CreateAccount frame and make it visible
                Create createAccountFrame = new Create();
                createAccountFrame.setVisible(true);
            }
        });
        
        JLabel whycreate = new JLabel("Don't have an account?");
        whycreate.setForeground(new Color(0, 0, 128)); // Navy Text
        whycreate.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
        whycreate.setBounds(100, 215, 180, 20);
        contentPane.add(whycreate);
    }

    private boolean checkCredentials(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            return result.next(); // If result.next() returns true, user exists with given credentials
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
    }
}
