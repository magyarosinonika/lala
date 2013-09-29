/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd.backup;

import UI.forms.ConnectToDatabase;
import engine.DAL;
import engine.MySql;
import engine.init.DBMSManager;
import engine.init.Settings;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            // TODO code application logic here
            Settings.initializeSettings();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        //minden ami beallitas szukseges a kapcs megvan 
        if (true) {
            DBMSManager.DALFactory(Settings.getRdbms());
        }
        ConnectToDatabase connectDatabase = new ConnectToDatabase();
        

        
    }
}
