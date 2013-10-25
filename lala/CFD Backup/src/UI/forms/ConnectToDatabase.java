/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author noni
 */
public class ConnectToDatabase extends JFrame {
//extends jframe
    //private JFrame connect_frame;

    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JPasswordField txtPassword2;
    private JTextField txtHost;
    private JTextField txtPort;
    private JTextField txtDbName;
    private JComboBox cbRDBMS;


    public ConnectToDatabase() {
        //kiserlet noni
        //nincs aktiv kapcs es nincsenek beallitasok es valamilyen adatbazisos muveletet akarok vegrehajtani
        //settings learndependencisis,handledependencies,beallitasokba be lehessen allitani hogy milyen tablakat ne vegyen igenybe
        //egy ablak amelyik backupot fog kesziteni - 2 modszer
        //amikor elfogadunk 
        //ha modosult valami az adatbazisban akkor ki neke toroolni az osszs olyan esetet ahol szerepelt
        //legyen egy help
        //this = new JFrame();
        initComponents();
        
        //this.setTitle("Nonika ablak");
    }
    
    public ConnectToDatabase(String name){
        super(name);
        initComponents();
    }

    public void initComponents() {
        this.setLayout(new BorderLayout());
        this.add(createInformationPanel(), BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        JButton btTestConnect = new JButton();
        JButton btSave = new JButton();
        buttonsPanel.add(btTestConnect);
        buttonsPanel.add(btSave);
        btTestConnect.setText("Test connection");
        btSave.setText("Save");
        final JFrame myFrame = this;
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
                            String filename = "settings.ini";
                            FileWriter fw = new FileWriter(filename);
                            fw.write("User name:"+txtUserName.getText()+System.getProperty("line.separator")
                                    +"Password:"+txtPassword.getText()+System.getProperty("line.separator")
                                    +"Host:"+txtHost.getText()+System.getProperty("line.separator")
                                    +"Port:"+txtPort.getText()+System.getProperty("line.separator")
                                    +"Database name:"+txtDbName.getText()+System.getProperty("line.separator")
                                    +"Rdbms:mysql");//appends the string to the file
                            fw.close();
                            JOptionPane.showMessageDialog(null, "Save!", "Warning", 0);
                        } catch (IOException ioe) {
                            System.err.println("IOException: " + ioe.getMessage());
                        }
                    }
                }
            }
        });
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

    }

    public JPanel createInformationPanel() {
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
}
