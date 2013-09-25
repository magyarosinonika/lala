/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author noni
 */
public class ConnectToDatabase {
    
    private JFrame connect_frame;

    public ConnectToDatabase() {
        connect_frame=new JFrame();
        initComponents();
        connect_frame.setSize(400, 250);
        connect_frame.setVisible(true);
        connect_frame.setResizable(false);
        connect_frame.setLocationRelativeTo(null);
    }
    
    public void initComponents() {

        connect_frame.setLayout(new BorderLayout());
        connect_frame.add(createInformationPanel(),BorderLayout.CENTER);
        JButton btConnect = new JButton();
        btConnect.setText("Connect");
        connect_frame.add(btConnect,BorderLayout.SOUTH);
        connect_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connect_frame.setResizable(false);

    }
    
    public JPanel createInformationPanel(){
        JPanel infPanel = new JPanel();
        JPanel labelsPanel = new JPanel();
        JPanel textsPanel = new JPanel();
        //infPanel.setLayout(new GridLayout(5,2));
        infPanel.setLayout(new BorderLayout());
        labelsPanel.setLayout(new GridLayout(5,1));
        textsPanel.setLayout(new GridLayout(5,1));
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
        JTextField txtUrl = new JTextField();
        JTextField txtUserName = new JTextField();
        JTextField txtPassword = new JTextField();
        JTextField txtPort = new JTextField();
        JTextField txtDbName = new JTextField();
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
        infPanel.add(labelsPanel,BorderLayout.WEST);
        infPanel.add(textsPanel,BorderLayout.CENTER);
        infPanel.setBorder(BorderFactory.createTitledBorder("Please fill the fields to connect!"));
        return infPanel;
    }
    
    
    
    
    
    
}
