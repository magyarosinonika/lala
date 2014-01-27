/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptionhandler;

import javax.swing.JOptionPane;

/**
 *
 * @author noni
 */
public class SetMessage {
    
    private static String message;

    public static void SetMessageError(String message) {
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void SetMessageInformation(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    } 
    
}
