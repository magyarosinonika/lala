/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd.backup;

import UI.JFrameManager;
import UI.forms.MainForm;
import engine.init.Settings;
import exceptionhandler.SetMessage;
import java.io.IOException;
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
            new taskhandler.SettingsReader();
        } catch (Exception e) {
           SetMessage.SetMessageError(e.getMessage());
        }
         MainForm  homeFrame = (MainForm)JFrameManager.createJFrame(MainForm.class, "Home", null);
    }
}
