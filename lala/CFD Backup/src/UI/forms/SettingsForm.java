/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import UI.formvalidator.SettingsHandler;
import engine.init.DBMSManager;
import engine.init.Settings;
import exceptionhandler.SetMessage;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTextField txtMaxOccurenceNumber;
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

    public Checkbox addToTables(String label, boolean state) {
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
        if ((Settings.getRdbms() == null) || (Settings.getTabels() == null) || DBMSManager.DALFactory(Settings.getRdbms()).getConnection() == null) {
            return new JPanel();
        }
        tabelsBox = new ArrayList<Checkbox>();
        JPanel tabelPanel = new JPanel();
        tabelPanel.setLayout(new BorderLayout());
        JPanel panelInformation = new JPanel();
        AbstractList<String> tableNameArray = new ArrayList<String>();
        tableNameArray = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        panelInformation.setLayout(new GridLayout(tableNameArray.size(), 1));
        int k = 0;
        for (int i = 0; i < tableNameArray.size(); ++i) {
            if (Settings.getTabels().contains(tableNameArray.get(i))) {
                addToTables(tableNameArray.get(i), true);
            } else {
                addToTables(tableNameArray.get(i), false);
            }
        }
        for (int i = 0; i < tabelsBox.size(); ++i) {
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
                Settings.setOccurrenceNumber(Integer.parseInt(txtMaxOccurenceNumber.getText()));
                Settings.setPassword(new String(txtPassword.getPassword()));
                Settings.setPort(Integer.parseInt(txtPort.getText()));
                Settings.setRdbms(cbRDBMS.getSelectedItem() + "");
                if (tabelsBox != null) {
                    AbstractList<String> tableElements = new ArrayList<String>();
                    for (int tabelsIndex = 0; tabelsIndex < tabelsBox.size(); tabelsIndex++) {
                        if (tabelsBox.get(tabelsIndex).getState()) {
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
                if(SettingsHandler.checkGeneralSettings(txtMaxOccurenceNumber.getText(), txtMaxThreadNumber.getText())){
                    Settings.setUserName(txtUserName.getText());
                    Settings.setDbName(txtDbName.getText());
                    Settings.setHost(txtHost.getText());
                    Settings.setMaxThreadNumber(Integer.parseInt(txtMaxThreadNumber.getText()));
                    Settings.setOccurrenceNumber(Integer.parseInt(txtMaxOccurenceNumber.getText()));
                    Settings.setPassword(new String(txtPassword.getPassword()));
                    Settings.setPort(Integer.parseInt(txtPort.getText()));
                    Settings.setRdbms(cbRDBMS.getSelectedItem() + "");
                    if (tabelsBox != null) {
                        AbstractList<String> tableElements = new ArrayList<String>();
                        for (int tabelsIndex = 0; tabelsIndex < tabelsBox.size(); tabelsIndex++) {
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
        btTestConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (SettingsHandler.checkConnectionSettings(txtUserName.getText(), new String(txtPassword.getPassword()), new String(txtPassword2.getPassword()), txtHost.getText(), txtPort.getText(), txtDbName.getText())) {
                    DBMSManager.DALFactory(cbRDBMS.getSelectedItem() + "").connect("jdbc:mysql://" + txtHost.getText() + ":", txtUserName.getText(), new String(txtPassword.getPassword()), Integer.parseInt(txtPort.getText()), txtDbName.getText());
                    DBMSManager.DALFactory(cbRDBMS.getSelectedItem() + "").setConnection(null);
                }


//                if (DBMSManager.DALFactory(Settings.getRdbms()).getConnection() == null) {
//                    SetMessage.SetMessageInformation("Faild!");
//                } else {
//                    SetMessage.SetMessageInformation("Ok!");
//                    DBMSManager.DALFactory(Settings.getRdbms()).setConnection(null);
//                }
            }
        });

        btSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (SettingsHandler.checkConnectionSettings(txtUserName.getText(), new String(txtPassword.getPassword()), new String(txtPassword2.getPassword()), txtHost.getText(), txtPort.getText(), txtDbName.getText())) {
                    Settings.setUserName(txtUserName.getText());
                    Settings.setDbName(txtDbName.getText());
                    Settings.setHost(txtHost.getText());
                    Settings.setMaxThreadNumber(Integer.parseInt(txtMaxThreadNumber.getText()));
                    Settings.setOccurrenceNumber(Integer.parseInt(txtMaxOccurenceNumber.getText()));
                    Settings.setPassword(new String(txtPassword.getPassword()));
                    Settings.setPort(Integer.parseInt(txtPort.getText()));
                    Settings.setRdbms(cbRDBMS.getSelectedItem() + "");
                    if (tabelsBox != null) {
                        AbstractList<String> tableElements = new ArrayList<String>();
                        for (int tabelsIndex = 0; tabelsIndex < tabelsBox.size(); tabelsIndex++) {
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

        txtMaxOccurenceNumber = new JTextField(Settings.getOccurrenceNumber() + "");
        txtMaxThreadNumber = new JTextField(Settings.getMaxThreadNumber() + "");

        labelsPanel.add(lbOccurenceNumber);
        textsPanel.add(txtMaxOccurenceNumber);

        labelsPanel.add(lbKMaxThreadNumber);
        textsPanel.add(txtMaxThreadNumber);

        infPanel.add(labelsPanel, BorderLayout.WEST);
        infPanel.add(textsPanel, BorderLayout.CENTER);
        infPanel.setBorder(BorderFactory.createTitledBorder("Please fill the fields to connect!"));
        return infPanel;
    }
}
