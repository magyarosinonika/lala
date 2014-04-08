/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.formvalidator;

import exceptionhandler.ExceptionHandlerCaller;
import exceptionhandler.SetMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class SettingsHandler {

    public static boolean checkConnectionSettings(String userName, String password1, String password2, String host, String port, String dbName) {

        if (userName.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Please enter your User name!", "Warning", 0);
            return false;
        } else if (!(password1.equals(password2))) {
            JOptionPane.showMessageDialog(null, "The password and confirmation password do not match!", "Warning", 0);
            return false;
        } else if (host.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Please enter the host!", "Warning", 0);
            return false;
        } else if (port.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Please enter the port!", "Warning", 0);
            return false;
        } else if (dbName.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Please enter the Database name!", "Warning", 0);
            return false;
        } else {
            try {
                Integer.parseInt(port);
            }catch(NumberFormatException ex){
                ExceptionHandlerCaller.handleNumberFormatException(ex, null,"Port Field");
                return false;
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
                return false;
            }
        }
        return true;
    }

    public static boolean checkGeneralSettings(String maxOccurenceNumber, String maxThreadNumber) {

        try {
            Integer.parseInt(maxOccurenceNumber);
            Integer.parseInt(maxThreadNumber);
        } catch (NumberFormatException ex) {
            
            ExceptionHandlerCaller.handleNumberFormatException(ex, null,"Occurence number and to the Max Thread number Field!");
            return false;
        }

        if (maxOccurenceNumber.equalsIgnoreCase("")) {
            SetMessage.SetMessageError("Please enter the Occurence number!");
            return false;
        } else if (maxThreadNumber.equalsIgnoreCase("")) {
            SetMessage.SetMessageError("Please enter the max Thread number!");
            return false;
        } else {
            return true;
        }
    }
}
