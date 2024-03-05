import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class Options extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Options frame = new Options();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Options() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(36, 36, 36)); // Charcoal black background color
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel optionslabel = new JLabel("Employee Management System");
        optionslabel.setForeground(Color.RED); // Adjusted text color
        optionslabel.setFont(new Font("Arial", Font.BOLD, 20));
        optionslabel.setBounds(50, 20, 350, 24);
        contentPane.add(optionslabel);

        JButton addButton = new JButton("Add Employee Details");
        addButton.setBackground(Color.ORANGE); // Set button background color
        addButton.setForeground(Color.WHITE); // Set button text color
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addButton.setBounds(50, 83, 200, 30);
        contentPane.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AddEmp optionsPage = new AddEmp();
                optionsPage.setVisible(true);
                dispose();
            }
        });

        JButton showButton = new JButton("Show Employee Details");
        showButton.setBackground(Color.BLUE); // Adjusted button background color
        showButton.setForeground(Color.WHITE);
        showButton.setFont(new Font("Arial", Font.PLAIN, 14));
        showButton.setBounds(50, 144, 200, 30);
        contentPane.add(showButton);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayEmployeeDetails();
            }
        });

        JButton signoutButton = new JButton("Sign Out");
        signoutButton.setBackground(Color.LIGHT_GRAY); // Light gray color for signout button
        signoutButton.setForeground(Color.BLACK); // Black text color for signout button
        signoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        signoutButton.setBounds(310, 220, 100, 30);
        contentPane.add(signoutButton);

        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the Login page
                Login loginpage = new Login();
                loginpage.setVisible(true);
                dispose(); // Close the current frame that is AddEmp file
            }
        });
    }

    private void displayEmployeeDetails() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "root");
            String query = "SELECT * FROM empdata";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get metadata to determine the number of columns
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create a Vector of Vectors to hold the data for the JTable
            Vector<Vector<Object>> data = new Vector<>();

            // Iterate through the result set and populate the data Vector
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                data.add(row);
            }

            // Create a Vector of column names
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Create JTable with data and column names
            JTable table = new JTable(data, columnNames);

            // Create a JScrollPane to hold the JTable
            JScrollPane scrollPane = new JScrollPane(table);

            // Create and set up the JFrame to display the JTable
            JFrame frame = new JFrame("Employee Details");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
