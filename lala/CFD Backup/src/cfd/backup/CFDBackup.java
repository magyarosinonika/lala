/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd.backup;

import UI.JFrameManager;
import UI.forms.ConnectToDatabase;
import engine.DAL;
import engine.FDScenario;
import engine.MySql;
import engine.init.DBMSManager;
import engine.init.Settings;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author noni
 */
public class CFDBackup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Settings.initializeSettings();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
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
            //ConnectToDatabase mainForm = new ConnectToDatabase();
           ConnectToDatabase mainForm = (ConnectToDatabase)JFrameManager.createJFrame(ConnectToDatabase.class, "Pityokha", null);
        }
    }
}
