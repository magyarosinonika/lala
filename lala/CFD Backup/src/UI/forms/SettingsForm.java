/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import engine.init.DBMSManager;
import engine.init.Settings;
import exceptionhandler.SetMessage;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
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
    private AbstractList<Checkbox> tabelsBox;

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
    
    public Checkbox addToTables(String label, boolean state)
    {
        Checkbox table = new Checkbox(label, state);
        tabelsBox.add(table);
        return table;
    }

    public void createTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Settings for connection", createConnectionPanel());
        tabbedPane.addTab("General settings", createGeneralPanel());
        tabbedPane.addTab("Choose tables", createTabelsPanel());
        this.add(tabbedPane);
    }
    
    public JPanel createTabelsPanel() {
        if ((Settings.getRdbms() == null) || (Settings.getTabels() == null)) {
            return new JPanel();
        }
        tabelsBox = new ArrayList<Checkbox>();
        JPanel tabelPanel = new JPanel();
        tabelPanel.setLayout(new BorderLayout());
        JPanel panelInformation = new JPanel();
        AbstractList<String> tableNameArray = new ArrayList<String>();
        tableNameArray = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        panelInformation.setLayout(new GridLayout(tableNameArray.size(), 1));
        int k=0;
        for (int i = 0; i < tableNameArray.size(); ++i) {
            if(Settings.getTabels().contains(tableNameArray.get(i))){
                addToTables(tableNameArray.get(i), true);
            }else{
                addToTables(tableNameArray.get(i), false);
            }
        }
        for(int i=0;i<tabelsBox.size();++i){
            panelInformation.add(tabelsBox.get(i));
        }
        tabelPanel.add(panelInformation, BorderLayout.CENTER);
        JPanel savePanel = new JPanel();
        tabelPanel.add(savePanel, BorderLayout.SOUTH);
        JButton btSave = new JButton();
        btSave.setText("Save");
        btSave.setPreferredSize(new Dimension(200, 25));
        savePanel.add(btSave);
        btSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               Settings.setUserName(txtUserName.getText());
                    Settings.setDbName(txtDbName.getText());
                    Settings.setHost(txtHost.getText());
                    Settings.setMaxThreadNumber(Integer.parseInt(txtMaxThreadNumber.getText()));
                    Settings.setOccurrenceNumber(Integer.parseInt(txtOccurenceNumber.getText()));
                    Settings.setPassword(new String(txtPassword.getPassword()));
                    Settings.setPort(Integer.parseInt(txtPort.getText()));
                    Settings.setRdbms(cbRDBMS.getSelectedItem() + "");
                    if ( tabelsBox != null ){
                        AbstractList<String> tableElements = new ArrayList<String>();
                        for (int tabelsIndex = 0; tabelsIndex < tabelsBox.size() ; tabelsIndex++) {
                            if(tabelsBox.get(tabelsIndex).getState()) {
                                tableElements.add(tabelsBox.get(tabelsIndex).getLabel());
                            }
                        }
                        Settings.setTabels(tableElements);
                    }
                    Settings.save();
                    SetMessage.SetMessageInformation("Save!");
                    
            }
        });
        return tabelPanel;
    }

    public JPanel createGeneralPanel() {
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout());
        generalPanel.add(createGeneralInformationPanel(), BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton btSave = new JButton();
        btSave.setPreferredSize(new Dimension(200, 25));
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
                        SetMessage.SetMessageError("Please enter number to the Occurence number and to the Max Thread number Field!");
                    }
                }

                if (txtOccurenceNumber.getText().equalsIgnoreCase("")) {
                    SetMessage.SetMessageError("Please enter the Occurence number!");
                } else if (txtMaxThreadNumber.getText().equalsIgnoreCase("")) {
                    SetMessage.SetMessageError("Please enter the max Thread number!");
                } else {
                    Settings.setUserName(txtUserName.getText());
                    Settings.setDbName(txtDbName.getText());
                    Settings.setHost(txtHost.getText());
                    Settings.setMaxThreadNumber(Integer.parseInt(txtMaxThreadNumber.getText()));
                    Settings.setOccurrenceNumber(Integer.parseInt(txtOccurenceNumber.getText()));
                    Settings.setPassword(new String(txtPassword.getPassword()));
                    Settings.setPort(Integer.parseInt(txtPort.getText()));
                    Settings.setRdbms(cbRDBMS.getSelectedItem() + "");
                    if ( tabelsBox != null ){
                        AbstractList<String> tableElements = new ArrayList<String>();
                        for (int tabelsIndex = 0; tabelsIndex < tabelsBox.size() ; tabelsIndex++) {
                            tableElements.add(tabelsBox.get(tabelsIndex).getLabel());
                        }
                        Settings.setTabels(tableElements);
                    }
                    
                    Settings.save();
                    SetMessage.SetMessageInformation("Save!");

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
//        btTestConnect.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                if (txtUserName.getText().equalsIgnoreCase("")) {
//                    JOptionPane.showMessageDialog(null, "Please enter your User name!", "Warning", 0);
//                } else if (!(txtPassword.getText().equalsIgnoreCase(txtPassword2.getText()))) {
//                    JOptionPane.showMessageDialog(null, "The password and confirmation password do not match!", "Warning", 0);
//                } else if (txtHost.getText().equalsIgnoreCase("")) {
//                    JOptionPane.showMessageDialog(null, "Please enter the host!", "Warning", 0);
//                } else if (txtPort.getText().equalsIgnoreCase("")) {
//                    JOptionPane.showMessageDialog(null, "Please enter the port!", "Warning", 0);
//                } else if (txtDbName.getText().equalsIgnoreCase("")) {
//                    JOptionPane.showMessageDialog(null, "Please enter the Database name!", "Warning", 0);
//                } else {
//                    if ((String.valueOf(cbRDBMS.getSelectedItem())).equalsIgnoreCase("MySQL")) {
//                        try {
//                            String drivers = "com.mysql.jdbc.Driver";
//                            System.setProperty(drivers, "");
//                            Connection connection = null;
//                            try {
//                                connection = DriverManager.getConnection("jdbc:mysql://" + txtHost.getText() + ":" + Integer.parseInt(txtPort.getText()) + "/" + txtDbName.getText(), txtUserName.getText(), txtPassword.getText());
//                                JOptionPane.showMessageDialog(null, "Is a valid connection!", "Warning", 0);
//                                connection.close();
//                            } catch (SQLException ex) {
//                                if (ex.getMessage().contains("Communications link failure")) {
//                                    JOptionPane.showMessageDialog(null, "Connection failed!", "Warning", 0);
//                                } else {
//                                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
//                                }
//                            }
//                        } catch (Exception ex) {
//                            if (ex.getMessage().contains("For input string")) {
//                                JOptionPane.showMessageDialog(null, "Please enter number to the Port Field!", "Warning", 0);
//                            } else {
//                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
//                            }
//                        }
//                    }
//                }
//            }
//        });

        btSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtUserName.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter your User name!", "Warning", 0);
                } else if ((txtPassword.getPassword().equals(txtPassword2.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "The password and confirmation password do not match!", "Warning", 0);
                } else if (txtHost.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the host!", "Warning", 0);
                } else if (txtPort.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the port!", "Warning", 0);
                } else if (txtDbName.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Database name!", "Warning", 0);
                } else {
                    Settings.setUserName(txtUserName.getText());
                    Settings.setDbName(txtDbName.getText());
                    Settings.setHost(txtHost.getText());
                    Settings.setMaxThreadNumber(Integer.parseInt(txtMaxThreadNumber.getText()));
                    Settings.setOccurrenceNumber(Integer.parseInt(txtOccurenceNumber.getText()));
                    Settings.setPassword(new String(txtPassword.getPassword()));
                    Settings.setPort(Integer.parseInt(txtPort.getText()));
                    Settings.setRdbms(cbRDBMS.getSelectedItem() + "");
                    if ( tabelsBox != null ){
                        AbstractList<String> tableElements = new ArrayList<String>();
                        for (int tabelsIndex = 0; tabelsIndex < tabelsBox.size() ; tabelsIndex++) {
                            tableElements.add(tabelsBox.get(tabelsIndex).getLabel());
                        }
                        Settings.setTabels(tableElements);
                    }
                    
                    Settings.save();
                    SetMessage.SetMessageInformation("Save!");
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

        txtUserName = new JTextField(Settings.getUserName());
        txtPassword = new JPasswordField(Settings.getPassword());
        txtPassword2 = new JPasswordField(Settings.getPassword());
        txtHost = new JTextField(Settings.getHost());
        txtPort = new JTextField(Settings.getPort() + "");
        txtDbName = new JTextField(Settings.getDbName());
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

        txtOccurenceNumber = new JTextField(Settings.getOccurrenceNumber() + "");
        txtMaxThreadNumber = new JTextField(Settings.getMaxThreadNumber() + "");

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
