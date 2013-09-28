/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import engine.DAL;
import engine.MySql;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ConnectToDatabase {

    private JFrame connect_frame;
    private JTextField txtUserName ;
    private JPasswordField txtPassword ;
    private JPasswordField txtPassword2 ;
    private JTextField txtHost ;
    private JTextField txtPort ;
    private JTextField txtDbName ;
    private JComboBox cbRDBMS;

    public ConnectToDatabase() {
        //kiserlet noni
        connect_frame = new JFrame();
        initComponents();
        connect_frame.setSize(400, 250);
        connect_frame.setVisible(true);
        connect_frame.setResizable(false);
        connect_frame.setLocationRelativeTo(null);
    }

    public void initComponents() {
        connect_frame.setLayout(new BorderLayout());
        connect_frame.add(createInformationPanel(), BorderLayout.CENTER);
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
                    JOptionPane.showMessageDialog(connect_frame, "Please enter your User name!", "Warning",0 );
                }else{
                    DAL mySql = new MySql();
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex, "ERROR", 0);
                }
                System.out.println("itt");
                if ((String.valueOf(cbRDBMS.getSelectedItem())).equalsIgnoreCase("MySQL")) {
                    System.out.println("mysql");
                    mySql.connect("jdbc:mysql://"+ txtHost.getText() +":", txtUserName.getText(), txtPassword.getText(), Integer.parseInt(txtPort.getText()), txtDbName.getText());
                }
                
                connect_frame.setVisible(false);
                mySql.generate();

                }
            }
        });
        connect_frame.add(buttonsPanel, BorderLayout.SOUTH);
        connect_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connect_frame.setResizable(false);

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
        String[] cbRDBMSStrings = { "MySQL", "PostgreSQL", "SQLite" };
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
