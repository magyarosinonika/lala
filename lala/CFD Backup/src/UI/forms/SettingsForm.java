/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import engine.init.DBMSManager;
import engine.init.Settings;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author noni
 */
public class SettingsForm extends JFrame {

    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JPasswordField txtPassword2;
    private JTextField txtHost;
    private JTextField txtPort;
    private JTextField txtDbName;
    private JComboBox cbRDBMS;
    private JTextField txtOccurenceNumber;
    private JTextField txtMaxThreadNumber;
    private JTabbedPane tabbedPane;
    private ArrayList<Checkbox> tabelsBox;

    public SettingsForm(String name) {
        super(name);
        initComponents();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public void initComponents() {
        createTabs();
    }

    public void createTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Settings for connection", createConnectionPanel());
        tabbedPane.addTab("General settings", createGeneralPanel());
        tabbedPane.addTab("Choose tables", createTabelsPanel());
        this.add(tabbedPane);
    }

    public JPanel createTabelsPanel() {
        tabelsBox = new ArrayList<Checkbox>();
        JPanel tabelPanel = new JPanel();
        tabelPanel.setLayout(new GridLayout(2, 1));
        JPanel panelInformation = new JPanel();
        AbstractList<String> tableNameArray = new ArrayList<String>();
        tableNameArray = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        panelInformation.setLayout(new GridLayout(tableNameArray.size(), 1));
        Checkbox checkbox;
        for (int i = 0; i < tableNameArray.size(); ++i) {
            checkbox = new Checkbox(tableNameArray.get(i), true);
            panelInformation.add(checkbox = new Checkbox(tableNameArray.get(i), true));
            tabelsBox.add(checkbox);
        }
        tabelPanel.add(panelInformation, BorderLayout.CENTER);
        JPanel savePanel = new JPanel();
        tabelPanel.add(savePanel, BorderLayout.SOUTH);
        JButton btSave = new JButton();
        btSave.setText("Save");
        savePanel.add(btSave);
        
        btSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

//                try {
//                    File file = new File("settings.ini");
//                    BufferedReader reader = new BufferedReader(new FileReader(file));
//                    String line = "", text = "";
//                    boolean isTabels = false;
//                    while ((line = reader.readLine()) != null) {
//                        if (line.contains("Tabels:")) {
//                            isTabels = true;
//                        }
//                        if (isTabels) {
//                            line = 
//                        }
//                        isTabels = false;
//                        text += line + System.getProperty("line.separator");
//                    }
//
//                    reader.close();
//                    FileWriter writer = new FileWriter("settings.ini");
//                    writer.write(text);
//                    writer.close();
//                    JOptionPane.showMessageDialog(null, "Save!", "Warning", 0);
//                } catch (IOException ioe) {
//                    System.err.println("IOException: " + ioe.getMessage());
//                }
            }
        });
        return tabelPanel;
    }

    public JPanel createGeneralPanel() {
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout());
        generalPanel.add(createGeneralInformationPanel(), BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        JButton btSave = new JButton();
        buttonsPanel.add(btSave);
        btSave.setText("Save");
        btSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer.parseInt(txtOccurenceNumber.getText());
                    Integer.parseInt(txtMaxThreadNumber.getText());
                } catch (Exception ex) {
                    if (ex.getMessage().contains("For input string")) {
                        JOptionPane.showMessageDialog(null, "Please enter number to the Occurence number and to the Max Thread number Field!", "Warning", 0);
                    }
                }

                if (txtOccurenceNumber.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Occurence number!", "Warning", 0);
                } else if (txtMaxThreadNumber.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the max Thread number!", "Warning", 0);
                } else {
                    try {
                        File file = new File("settings.ini");
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line = "", text = "";
                        boolean isOccurenceNumber = false;
                        boolean isMaxThreadNumber = false;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains("Occurrence number:")) {
                                isOccurenceNumber = true;
                            }
                            if (line.contains("Max Thread number:")) {
                                isMaxThreadNumber = true;
                            }

                            if (isOccurenceNumber) {
                                line = "Occurrence number:" + txtOccurenceNumber.getText();
                            }
                            if (isMaxThreadNumber) {
                                line = "Max Thread number:" + txtMaxThreadNumber.getText();
                            }
                            isOccurenceNumber = false;
                            isMaxThreadNumber = false;
                            text += line + System.getProperty("line.separator");
                        }

                        reader.close();
                        FileWriter writer = new FileWriter("settings.ini");
                        writer.write(text);
                        writer.close();
                        JOptionPane.showMessageDialog(null, "Save!", "Warning", 0);
                    } catch (IOException ioe) {
                        System.err.println("IOException: " + ioe.getMessage());
                    }

                }
            }
        });
        generalPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return generalPanel;
    }

    public JPanel createConnectionPanel() {
        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new BorderLayout());
        connectionPanel.add(createConnectionInformationPanel(), BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        JButton btTestConnect = new JButton();
        JButton btSave = new JButton();
        buttonsPanel.add(btTestConnect);
        buttonsPanel.add(btSave);
        btTestConnect.setText("Test connection");
        btSave.setText("Save");
        btTestConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtUserName.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter your User name!", "Warning", 0);
                } else if (!(txtPassword.getText().equalsIgnoreCase(txtPassword2.getText()))) {
                    JOptionPane.showMessageDialog(null, "The password and confirmation password do not match!", "Warning", 0);
                } else if (txtHost.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the host!", "Warning", 0);
                } else if (txtPort.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the port!", "Warning", 0);
                } else if (txtDbName.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Database name!", "Warning", 0);
                } else {
                    if ((String.valueOf(cbRDBMS.getSelectedItem())).equalsIgnoreCase("MySQL")) {
                        try {
                            String drivers = "com.mysql.jdbc.Driver";
                            System.setProperty(drivers, "");
                            Connection connection = null;
                            try {
                                connection = DriverManager.getConnection("jdbc:mysql://" + txtHost.getText() + ":" + Integer.parseInt(txtPort.getText()) + "/" + txtDbName.getText(), txtUserName.getText(), txtPassword.getText());
                                JOptionPane.showMessageDialog(null, "Is a valid connection!", "Warning", 0);
                                connection.close();
                            } catch (SQLException ex) {
                                if (ex.getMessage().contains("Communications link failure")) {
                                    JOptionPane.showMessageDialog(null, "Connection failed!", "Warning", 0);
                                } else {
                                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
                                }
                            }
                        } catch (Exception ex) {
                            if (ex.getMessage().contains("For input string")) {
                                JOptionPane.showMessageDialog(null, "Please enter number to the Port Field!", "Warning", 0);
                            } else {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
                            }
                        }
                    }
                }
            }
        });

        btSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtUserName.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter your User name!", "Warning", 0);
                } else if (!(txtPassword.getText().equalsIgnoreCase(txtPassword2.getText()))) {
                    JOptionPane.showMessageDialog(null, "The password and confirmation password do not match!", "Warning", 0);
                } else if (txtHost.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the host!", "Warning", 0);
                } else if (txtPort.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the port!", "Warning", 0);
                } else if (txtDbName.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Database name!", "Warning", 0);
                } else {
                    if ((String.valueOf(cbRDBMS.getSelectedItem())).equalsIgnoreCase("MySQL")) {
                        try {
                            File file = new File("settings.ini");
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line = "", text = "";
                            boolean isUsername = false;
                            boolean isPassword = false;
                            boolean isHost = false;
                            boolean isPort = false;
                            boolean isDatabaseName = false;
                            boolean isRdbms = false;
                            while ((line = reader.readLine()) != null) {
                                if (line.contains("User name:")) {
                                    isUsername = true;
                                }
                                if (line.contains("Password:")) {
                                    isPassword = true;
                                }
                                if (line.contains("Host:")) {
                                    isHost = true;
                                }
                                if (line.contains("Port:")) {
                                    isPort = true;
                                }
                                if (line.contains("Database name:")) {
                                    isDatabaseName = true;
                                }
                                if (line.contains("Rdbms:")) {
                                    isRdbms = true;
                                }
                                if (isUsername) {
                                    line = "User name:" + txtUserName.getText();
                                }
                                if (isPassword) {
                                    line = "Password:" + txtPassword.getText();
                                }
                                if (isHost) {
                                    line = "Host:" + txtHost.getText();
                                }
                                if (isPort) {
                                    line = "Port:" + txtPort.getText();
                                }
                                if (isDatabaseName) {
                                    line = "Database name:" + txtDbName.getText();
                                }
                                if (isRdbms) {
                                    line = "Rdbms:mysql";
                                }
                                isUsername = false;
                                isPassword = false;
                                isHost = false;
                                isPort = false;
                                isDatabaseName = false;
                                isRdbms = false;
                                text += line + System.getProperty("line.separator");
                            }

                            reader.close();
                            FileWriter writer = new FileWriter("settings.ini");
                            writer.write(text);
                            writer.close();
                            JOptionPane.showMessageDialog(null, "Save!", "Warning", 0);
                        } catch (IOException ioe) {
                            System.err.println("IOException: " + ioe.getMessage());
                        }
                    }
                }
            }
        });
        connectionPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return connectionPanel;
    }

    public JPanel createConnectionInformationPanel() {
        JPanel infPanel = new JPanel();
        JPanel labelsPanel = new JPanel();
        JPanel textsPanel = new JPanel();
        infPanel.setLayout(new BorderLayout());
        labelsPanel.setLayout(new GridLayout(7, 1));
        textsPanel.setLayout(new GridLayout(7, 1));

        JLabel lbUserName = new JLabel();
        lbUserName.setText("User name: ");

        JLabel lbPassword = new JLabel();
        lbPassword.setText("Password: ");

        JLabel lbConfirmPassword = new JLabel();
        lbConfirmPassword.setText("Confirm Password: ");

        JLabel lbHost = new JLabel();
        lbHost.setText("Host: ");

        JLabel lbPort = new JLabel();
        lbPort.setText("Port: ");

        JLabel lbDbName = new JLabel();
        lbDbName.setText("Database name: ");

        JLabel lbRDBMS = new JLabel();
        lbRDBMS.setText("RDBMS: ");

        txtUserName = new JTextField();
        txtPassword = new JPasswordField();
        txtPassword2 = new JPasswordField();
        txtHost = new JTextField();
        txtPort = new JTextField();
        txtDbName = new JTextField();
        String[] cbRDBMSStrings = {"MySQL", "PostgreSQL", "SQLite"};
        cbRDBMS = new JComboBox(cbRDBMSStrings);
        cbRDBMS.setSelectedIndex(0);

        labelsPanel.add(lbUserName);
        textsPanel.add(txtUserName);

        labelsPanel.add(lbPassword);
        textsPanel.add(txtPassword);

        labelsPanel.add(lbConfirmPassword);
        textsPanel.add(txtPassword2);

        labelsPanel.add(lbHost);
        textsPanel.add(txtHost);

        labelsPanel.add(lbPort);
        textsPanel.add(txtPort);

        labelsPanel.add(lbDbName);
        textsPanel.add(txtDbName);

        labelsPanel.add(lbRDBMS);
        textsPanel.add(cbRDBMS);

        infPanel.add(labelsPanel, BorderLayout.WEST);
        infPanel.add(textsPanel, BorderLayout.CENTER);
        infPanel.setBorder(BorderFactory.createTitledBorder("Please fill the fields to connect!"));
        return infPanel;
    }

    public JPanel createGeneralInformationPanel() {
        JPanel infPanel = new JPanel();
        JPanel labelsPanel = new JPanel();
        JPanel textsPanel = new JPanel();
        infPanel.setLayout(new BorderLayout());
        labelsPanel.setLayout(new GridLayout(7, 1));
        textsPanel.setLayout(new GridLayout(7, 1));

        JLabel lbOccurenceNumber = new JLabel();
        lbOccurenceNumber.setText("Occurence number: ");

        JLabel lbKMaxThreadNumber = new JLabel();
        lbKMaxThreadNumber.setText("Max thread number: ");

        txtOccurenceNumber = new JTextField();
        txtMaxThreadNumber = new JTextField();

        labelsPanel.add(lbOccurenceNumber);
        textsPanel.add(txtOccurenceNumber);

        labelsPanel.add(lbKMaxThreadNumber);
        textsPanel.add(txtMaxThreadNumber);

        infPanel.add(labelsPanel, BorderLayout.WEST);
        infPanel.add(textsPanel, BorderLayout.CENTER);
        infPanel.setBorder(BorderFactory.createTitledBorder("Please fill the fields to connect!"));
        return infPanel;
    }
}
