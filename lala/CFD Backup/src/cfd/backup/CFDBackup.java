/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd.backup;

import UI.JFrameManager;
import UI.forms.MainForm;


/**
 *
 * @author noni
 */
public class CFDBackup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        try {
//            Settings.initializeSettings();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(CFDBackup.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (Settings.getDbName() != null && Settings.getHost() != null && Settings.getPassword() != null && Settings.getPort() != 0 && Settings.getRdbms() != null && Settings.getUserName() != null) {
//            DBMSManager.DALFactory(Settings.getRdbms());
//            try {
//                Class.forName("com.mysql.jdbc.Driver").newInstance();
//                DBMSManager.DALFactory(Settings.getRdbms()).connect("jdbc:mysql://" + Settings.getHost() + ":", Settings.getUserName(), Settings.getPassword(), Settings.getPort(), Settings.getDbName());
//                //DBMSManager.DALFactory(Settings.getRdbms()).generate();
//            } catch (Exception ex) {
//                if (ex.getMessage().contains("For input string")) {
//                    JOptionPane.showMessageDialog(null, "Please enter number to the Port Field!", "Warning", 0);
//                } else {
//                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
//                }
//            }
//        } else {
//           ConnectToDatabase settingsConnectForm = (ConnectToDatabase)JFrameManager.createJFrame(ConnectToDatabase.class, "Settings for connection", null);
//        }
     
        
        MainForm  homeFrame = (MainForm)JFrameManager.createJFrame(MainForm.class, "Home", null);
    }
}
