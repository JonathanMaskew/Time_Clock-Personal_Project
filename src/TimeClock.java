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
                        loginFrame.dispose();
                        showMainFrame();
                    } else  {
                        JOptionPane.showMessageDialog(null, "The username or password you entered is incorrect.\nPlease check your input and try again.\nOr, click \"Create an Account\" to create a new account.", "Error", JOptionPane.ERROR_MESSAGE);
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
                boolean nameValid = false;
                boolean usernameValid = false;
                boolean passwordValid = false;

                boolean allFieldsValid = false;

                if (nameField.getText().length() > 0 && nameField.getText().contains(" "))  {
                    nameValid = true;
                } else  {
                    JOptionPane.showMessageDialog(null, "Please include your first and last name.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                if (nameValid) {
                    try {
                        if (usernameField.getText().length() != 0) {
                            if (database.usernameTaken(usernameField.getText())) {
                                JOptionPane.showMessageDialog(null, "This username is already taken.\nPlease try another one.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else if (usernameField.getText().contains(" "))    {
                                JOptionPane.showMessageDialog(null, "Please ensure your username does not contain any spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else  {
                                usernameValid = true;
                            }
                        } else  {
                            JOptionPane.showMessageDialog(null, "Please input a username.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                String password = "";
                if (nameValid && usernameValid) {
                    for (int i = 0; i < passwordField.getPassword().length; i++) {
                        password = password + passwordField.getPassword()[i];
                    }
                    if (password.length() >= 4) {
                        if (!password.contains("0") && !password.contains("1") && !password.contains("2") && !password.contains("3") && !password.contains("4") && !password.contains("5") && !password.contains("6") && !password.contains("7") && !password.contains("8") && !password.contains("9")) {
                            JOptionPane.showMessageDialog(null, "Please ensure your password includes at least one number.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            passwordValid = true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please ensure your password is at least four character in length.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (nameValid && usernameValid && passwordValid)    {
                    allFieldsValid = true;
                }

                if (allFieldsValid) {
                    person = new Person(nameField.getText(), usernameField.getText(), password);
                    try {
                        database.addPerson(person);
                        createAccountFrame.dispose();
                        showMainFrame();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
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

        if (lines.size() == 0)  {
            JLabel label = new JLabel("Your time clocked in will appear here.");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            centerContent.add(label);
        } else {
            for (int i = lines.size() - 1; i >= 0; i--) {
                JLabel label = new JLabel(lines.get(i));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                centerContent.add(label);
            }
        }

        JScrollPane centerContentScroll = new JScrollPane(centerContent);
        mainFrame.add(centerContentScroll, BorderLayout.CENTER);

        Container northContent = new Container();
        northContent.setLayout(new FlowLayout());
        JLabel northLabel = new JLabel();
        northContent.add(northLabel);
        mainFrame.add(northContent, BorderLayout.NORTH);

        JPanel eastBlank = new JPanel();
        mainFrame.add(eastBlank, BorderLayout.EAST);

        Container southContent = new Container();
        southContent.setLayout(new GridLayout(1, 2));
        JButton toggleButton = new JButton();
        boolean running = database.isRunning();
        if (!running) {
            toggleButton.setText("Start Clock");
            northLabel.setText("Hello, " + person.getName().substring(0, person.getName().indexOf(" ")) + ". Ready to clock in?");
        } else  {
            toggleButton.setText("Stop Clock");
            northLabel.setText("Hello, " + person.getName().substring(0, person.getName().indexOf(" ")) + ". Your clock is currently running.");
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
                        JOptionPane.showMessageDialog(null, "Your clock has been started!\nYou may close the program and return at any time to stop your clock.", "Clock Started", JOptionPane.INFORMATION_MESSAGE);
                        toggleButton.setText("Stop Clock");
                        northLabel.setText("Your clock is currently running.");
                        mainFrame.repaint();
                    } else  {
                        toggleButton.setText("Start Clock");
                        database.stopClock();
                        JOptionPane.showMessageDialog(null, "Your clock has been stopped!", "Clock Stopped", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.dispose();
                        showMainFrame();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
