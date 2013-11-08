/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import UI.JFrameManager;
import engine.init.DBMSManager;
import engine.init.Settings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author noni
 */
public class MainForm extends JFrame{
    
    public MainForm(String name){
        super(name);
        initComponents();
    }
    
     public void initComponents(){
        createMain();
        try {
            Settings.initializeSettings();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Settings.getDbName() != null && Settings.getHost() != null && Settings.getPassword() != null && Settings.getPort() != 0 && Settings.getRdbms() != null && Settings.getUserName() != null) {
            DBMSManager.DALFactory(Settings.getRdbms());
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                DBMSManager.DALFactory(Settings.getRdbms()).connect("jdbc:mysql://" + Settings.getHost() + ":", Settings.getUserName(), Settings.getPassword(), Settings.getPort(), Settings.getDbName());
                DBMSManager.DALFactory(Settings.getRdbms()).generate();
            } catch (Exception ex) {
                if (ex.getMessage().contains("For input string")) {
                    JOptionPane.showMessageDialog(null, "Please enter number to the Port Field!", "Warning", 0);
                } else {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
                }
            }
        } else {
           SettingsForm settingsForm = (SettingsForm)JFrameManager.createJFrame(SettingsForm.class, "General settings", this);
           //settingsForm.setSize(600, 600);
           settingsForm.getTabbedPane().setSelectedIndex(0);
        }
        if (Settings.getOccurrenceNumber() == 0 || Settings.getMaxThreadNumber() == 0){
            SettingsForm settingsForm = (SettingsForm)JFrameManager.createJFrame(SettingsForm.class, "General settings", this);
            settingsForm.getTabbedPane().setSelectedIndex(1);
        }
     }
    
     public void createMain(){
        final JFrame myframe = this;
        JMenuBar menuBar = new JMenuBar();
        JMenu appMenu = new JMenu("Application");
        menuBar.add(appMenu);
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsForm settingsForm = (SettingsForm)JFrameManager.createJFrame(SettingsForm.class, "General settings", myframe);
            }
        });
        appMenu.add(settingsMenuItem);
        this.setJMenuBar(menuBar);
     }
}
