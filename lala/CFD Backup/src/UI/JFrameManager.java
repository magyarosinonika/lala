/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import UI.forms.*;
import exceptionhandler.SetMessage;

/**
 *
 * @author noni
 */
public class JFrameManager  {
    
    public static JFrame createJFrame(Class c, String name, Component parentComponent)
    {
        JFrame frame = null;
        try
        {
            frame = (JFrame)c.getDeclaredConstructor(new Class[] {String.class}).newInstance(name);
            if (parentComponent == null)
            {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            else
            {
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            frame.setLocationRelativeTo(parentComponent);
            frame.setSize(450, 300);
            frame.setResizable(false);
            frame.setTitle(name);
            frame.setVisible(true);
        }
        catch (InstantiationException instantiationException)
        {
            SetMessage.SetMessageError("Class object cannot be instantiated!");
            //ExceptionHandler.handleException(instantiationException, parentComponent, Language.getMessage(Language.InstantiationExceptionKey), c.getName());
        }
        catch(NoSuchMethodException noSuchMethodException)
        {
            SetMessage.SetMessageError("Method cannot be found!");
        }
        catch (IllegalAccessException illegalAccessException)
        {
            SetMessage.SetMessageError("The currently executing method does not have access to the definition of the specified class, field, method or constructor!");
        }
        catch (InvocationTargetException invocationTargetException)
        {
            SetMessage.SetMessageError("InvocationTargetException");
        }
        catch(Exception exception){
            //JOptionPane.showMessageDialog(null, exception.getMessage(),"ERROR",0);
            for(int i=0;i<exception.getStackTrace().length;++i){
                System.out.println(exception.getStackTrace()[i]);
            }
        }
        finally {
            return frame;
        }
    }
}
