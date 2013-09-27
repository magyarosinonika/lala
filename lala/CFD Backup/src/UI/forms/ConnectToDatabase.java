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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author noni
 */
public class ConnectToDatabase {

    private JFrame connect_frame;
    private JTextField txtUrl ;
    private JTextField txtUserName ;
    private JTextField txtPassword ;
    private JTextField txtPort ;
    private JTextField txtDbName ;

    public ConnectToDatabase() {
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
        JButton btConnect = new JButton();
        btConnect.setText("Connect");
        btConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                DAL mySql = new MySql();
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex, "ERROR", 0);
                }
                mySql.connect("jdbc:mysql://localhost:", txtUserName.getText(), txtPassword.getText(), Integer.parseInt(txtPort.getText()), txtDbName.getText());//ini allomanybol a  beallitasiokat beolvassa a felulet
                connect_frame.setVisible(false);
                mySql.generate();

            }
        });
        connect_frame.add(btConnect, BorderLayout.SOUTH);
        connect_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connect_frame.setResizable(false);

    }

    public JPanel createInformationPanel() {
        JPanel infPanel = new JPanel();
        JPanel labelsPanel = new JPanel();
        JPanel textsPanel = new JPanel();
        infPanel.setLayout(new BorderLayout());
        labelsPanel.setLayout(new GridLayout(5, 1));
        textsPanel.setLayout(new GridLayout(5, 1));
        JLabel lbUrl = new JLabel();
        lbUrl.setText("Url: ");
        JLabel lbUserName = new JLabel();
        lbUserName.setText("User name: ");
        JLabel lbPassword = new JLabel();
        lbPassword.setText("Password: ");
        JLabel lbPort = new JLabel();
        lbPort.setText("Port: ");
        JLabel lbDbName = new JLabel();
        lbDbName.setText("Database name: ");
        txtUrl = new JTextField();
        txtUserName = new JTextField();
        txtPassword = new JTextField();
        txtPort = new JTextField();
        txtDbName = new JTextField();
        labelsPanel.add(lbUrl);
        textsPanel.add(txtUrl);
        labelsPanel.add(lbUserName);
        textsPanel.add(txtUserName);
        labelsPanel.add(lbPassword);
        textsPanel.add(txtPassword);
        labelsPanel.add(lbPort);
        textsPanel.add(txtPort);
        labelsPanel.add(lbDbName);
        textsPanel.add(txtDbName);
        infPanel.add(labelsPanel, BorderLayout.WEST);
        infPanel.add(textsPanel, BorderLayout.CENTER);
        infPanel.setBorder(BorderFactory.createTitledBorder("Please fill the fields to connect!"));
        return infPanel;
    }
}
