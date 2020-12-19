import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class TimeClock {
    private static final Database database = new Database();
    private static Person person;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showLoginFrame();
            }
        });
    }

    private static void showLoginFrame()   {
        JFrame loginFrame = new JFrame();
        loginFrame.setTitle("Login");
        loginFrame.setSize(300, 150);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setVisible(true);

        Container northContent = new Container();
        northContent.setLayout(new FlowLayout());
        northContent.add(new JLabel("Login below or click \"Create an Account\""));
        loginFrame.add(northContent, BorderLayout.NORTH);

        JPanel eastBlank = new JPanel();
        loginFrame.add(eastBlank, BorderLayout.EAST);

        Container southContent = new Container();
        southContent.setLayout(new GridLayout(1, 2));
        JButton loginButton = new JButton("Login");
        southContent.add(loginButton);
        JButton createAccountButton = new JButton("Create an Account");
        southContent.add(createAccountButton);
        loginFrame.add(southContent, BorderLayout.SOUTH);

        JPanel westBlank = new JPanel();
        loginFrame.add(westBlank, BorderLayout.WEST);

        Container centerContent = new Container();
        centerContent.setLayout(new GridLayout(0, 2));
        centerContent.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        centerContent.add(usernameField);
        centerContent.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        centerContent.add(passwordField);
        loginFrame.add(centerContent, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String password = "";
                    for (int i = 0; i < passwordField.getPassword().length; i++) {
                        password = password + passwordField.getPassword()[i];
                    }

                    if (database.userExists(usernameField.getText(), password) != null)   {
                        loginFrame.dispose();
                        person = database.userExists(usernameField.getText(), password);
                        showMainFrame();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                showCreateAccountFrame();
            }
        });
    }

    private static void showCreateAccountFrame()    {
        JFrame createAccountFrame = new JFrame();
        createAccountFrame.setTitle("Create an Account");
        createAccountFrame.setSize(300, 150);
        createAccountFrame.setLocationRelativeTo(null);
        createAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createAccountFrame.setLayout(new BorderLayout());
        createAccountFrame.setVisible(true);

        Container northContent = new Container();
        northContent.add(new JLabel("Please fill in all of the required fields below."));
        createAccountFrame.add(northContent, BorderLayout.NORTH);

        JPanel eastBlank = new JPanel();
        createAccountFrame.add(eastBlank, BorderLayout.EAST);

        Container southContent = new Container();
        southContent.setLayout(new GridLayout(1, 2));
        JButton createButton = new JButton("Create");
        southContent.add(createButton);
        createAccountFrame.add(southContent, BorderLayout.SOUTH);

        JPanel westBlank = new JPanel();
        createAccountFrame.add(westBlank, BorderLayout.WEST);

        Container centerContent = new Container();
        centerContent.setLayout(new GridLayout(0, 2));
        centerContent.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        centerContent.add(nameField);
        centerContent.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        centerContent.add(usernameField);
        centerContent.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        centerContent.add(passwordField);
        createAccountFrame.add(centerContent, BorderLayout.CENTER);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = "";
                for (int i = 0; i < passwordField.getPassword().length; i++) {
                    password = password + passwordField.getPassword()[i];
                }
                person = new Person(nameField.getText(), usernameField.getText(), password);
                try {
                    database.addPerson(person);
                    createAccountFrame.dispose();
                    showMainFrame();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    private static void showMainFrame() throws IOException {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Time Clock");
        mainFrame.setSize(600, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setVisible(true);

        Container centerContent = new Container();
        centerContent.setLayout(new GridLayout(0, 1));
        ArrayList<String> lines = database.getInformation(person.getUsername() + ".bin");
        for (int i = 0; i < lines.size(); i++) {
            centerContent.add(new JLabel(lines.get(i)));
        }
        mainFrame.add(centerContent, BorderLayout.CENTER);

        Container northContent = new Container();
        northContent.setLayout(new FlowLayout());
        northContent.add(new JLabel("Welcome, " + person.getName().substring(0, person.getName().indexOf(" ")) + "."));
        mainFrame.add(northContent, BorderLayout.NORTH);

        JPanel eastBlank = new JPanel();
        mainFrame.add(eastBlank, BorderLayout.EAST);

        Container southContent = new Container();
        southContent.setLayout(new GridLayout(1, 2));
        JButton toggleButton = new JButton();
        boolean running = database.isRunning();
        if (!running) {
            toggleButton.setText("Start Clock");
        } else  {
            toggleButton.setText("Stop Clock");
        }
        southContent.add(toggleButton);
        mainFrame.add(southContent, BorderLayout.SOUTH);

        JPanel westBlank = new JPanel();
        mainFrame.add(westBlank, BorderLayout.WEST);

        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean running = database.isRunning();
                    if (!running) {
                        database.startClock();
                        toggleButton.setText("Stop Clock");
                    } else  {
                        toggleButton.setText("Start Clock");
                        database.stopClock();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
