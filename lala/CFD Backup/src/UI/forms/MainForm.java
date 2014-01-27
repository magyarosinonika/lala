/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import UI.JFrameManager;
import engine.init.DBMSManager;
import engine.init.Settings;
import java.awt.MenuItem;
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
    
    final JFrame myframe = this;
    public MainForm(String name){
        super(name);
        initComponents();
    }
    
     public void initComponents(){
        createMain();
        
     }
    
     public void createMain(){
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
        JMenuItem correlationMenuItem = new JMenuItem("Correlations");
        correlationMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CorrelationsForm correlationsForm = (CorrelationsForm)JFrameManager.createJFrame(CorrelationsForm.class, "Correlations", myframe);
            }
        });
        appMenu.add(correlationMenuItem);
        this.setJMenuBar(menuBar);
     }
}
