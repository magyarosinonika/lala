/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptionhandler;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JOptionPane;

/**
 *
 * @author nonika
 */
public class ExceptionHandlerCaller extends Exception {

    private Exception myException;

    public void show(Component parentComponent, String message, String title, int tp) {
        JOptionPane.showMessageDialog(parentComponent, message, title, tp);
    }

    public ExceptionHandlerCaller(Exception myException) {
        this.myException = myException;
    }
    
    public static void handleFileNotFoundException(FileNotFoundException fileNotFoundException, Component parentComponent, String name) {
        (new ExceptionHandlerCaller(fileNotFoundException)).show(parentComponent, name + " was not found", "ERROR", 0);
    }
    
    public static void handleNumberFormatException(NumberFormatException numberFormatException, Component parentComponent, String name) {
        (new ExceptionHandlerCaller(numberFormatException)).show(parentComponent, "Please enter number to the " + name, "ERROR", 0);
    }
    
    public static void handleInstantiationException(InstantiationException instantiationException, Component parentComponent, String name) {
        (new ExceptionHandlerCaller(instantiationException)).show(parentComponent, "Class object cannot be instantiated!" + name, "ERROR", 0);
    }
    
    public static void handleNoSuchMethodException(NoSuchMethodException noSuchMethodException, Component parentComponent, String name) {
        (new ExceptionHandlerCaller(noSuchMethodException)).show(parentComponent, "Method cannot be found!" + name, "ERROR", 0);
    }
    
    public static void handleIllegalAccessException(IllegalAccessException illegalAccessException, Component parentComponent, String name) {
        (new ExceptionHandlerCaller(illegalAccessException)).show(parentComponent, "The currently executing method does not have access to the definition of the specified class, field, method or constructor!" + name, "ERROR", 0);
    }
    
    public static void handleInvocationTargetException(InvocationTargetException invocationTargetException, Component parentComponent, String name) {
        (new ExceptionHandlerCaller(invocationTargetException)).show(parentComponent, "InvocationTargetException" + name, "ERROR", 0);
    }
        
        
        
        
    
    
    
    
    
    

}
