/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import UI.JFrameManager;
import engine.init.DBMSManager;
import engine.init.Settings;
import exceptionhandler.SetMessage;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author noni
 */
public class MainForm extends JFrame {

    final JFrame myframe = this;

    public MainForm(String name) {
        super(name);
        initComponents();
    }

    public void initComponents() {
        createMain();

    }

    public void createMain() {
        JMenuBar menuBar = new JMenuBar();
        JMenu appMenu = new JMenu("Application");
        menuBar.add(appMenu);
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsForm settingsForm = (SettingsForm) JFrameManager.createJFrame(SettingsForm.class, "General settings", myframe);
            }
        });
        appMenu.add(settingsMenuItem);
        JMenuItem dependenciesMenuItem = new JMenuItem("Dependencies");
        dependenciesMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DependenciesForm dependenciesForm = (DependenciesForm) JFrameManager.createJFrame(DependenciesForm.class, "Dependencies", myframe);
            }
        });
        appMenu.add(dependenciesMenuItem);



        JMenuItem backupMenuItem = new JMenuItem("Backup");
        backupMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                //BackupForm backupForm = (BackupForm) JFrameManager.createJFrame(BackupForm.class, "Backup", myframe);


                try {
                    Object[] options = {"Normal backup", "Backup with dependencies"};
                    int action = JOptionPane.showOptionDialog(null, "Please choose ", "Backup",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                    if (action == 0) {
                        System.out.println("Normal");
                        Date d1 = new Date();

                        for (int i = 0; i < 10000000; ++i) {
                            for (int j = 0; j < 20000; ++j) {
                                //
                            }

                        }
                        Date d2 = new Date();
                        long diff = d2.getTime() - d1.getTime();

                        long diffSeconds = diff / 1000 % 60;
                        long diffMinutes = diff / (60 * 1000) % 60;
                        long diffHours = diff / (60 * 60 * 1000) % 24;
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        JOptionPane.showMessageDialog(myframe, "Time: " + diffDays + " days ," + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds." ,"Backup time",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println();
                        System.out.print(diffDays + " days, ");
                        System.out.print(diffHours + " hours, ");
                        System.out.print(diffMinutes + " minutes, ");
                        System.out.print(diffSeconds + " seconds.");
                    } else if (action == 1) {
                        System.out.println("With dependencies");
                    }
                } catch (Exception ex) {
                    //TODO taskhandler
                    JOptionPane.showMessageDialog(myframe, ex.getMessage());
                }
            }
        });
        appMenu.add(backupMenuItem);


        this.setJMenuBar(menuBar);
    }
}
