/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

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
            frame.setSize(400, 250);
            frame.setResizable(false);
            frame.setTitle(name);
            frame.setVisible(true);
        }
        catch (InstantiationException instantiationException)
        {
            //ExceptionHandler.handleException(instantiationException, parentComponent, Language.getMessage(Language.InstantiationExceptionKey), c.getName());
        }
        catch(NoSuchMethodException noSuchMethodException)
        {
            //ExceptionHandler.handleException(noSuchMethodException, parentComponent, Language.NoSuchMethodExceptionKey, "NamedConstructor");
            //ExceptionHandler.handleException(noSuchMethodException, parentComponent, Language.getMessage(Language.NoSuchMethodExceptionKey), Language.getMessage(Language.ConstructorOrJFrameMethod));
        }
        catch (IllegalAccessException illegalAccessException)
        {
            //ExceptionHandler.handleException(illegalAccessException, parentComponent, Language.getMessage(Language.IllegalAccessExceptionKey));
        }
        catch (InvocationTargetException invocationTargetException)
        {
            //ExceptionHandler.handleException(invocationTargetException, parentComponent, Language.getMessage(Language.InvocationTargetExceptionKey));
        } finally {
            return frame;
        }
    }
}
