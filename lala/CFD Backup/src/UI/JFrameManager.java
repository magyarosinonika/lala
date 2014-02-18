/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import exceptionhandler.ExceptionHandlerCaller;

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
            ExceptionHandlerCaller.handleInstantiationException(instantiationException, null,"???");
        }
        catch(NoSuchMethodException noSuchMethodException)
        {
            ExceptionHandlerCaller.handleNoSuchMethodException(noSuchMethodException, null,"???");
        }
        catch (IllegalAccessException illegalAccessException)
        {
            ExceptionHandlerCaller.handleIllegalAccessException(illegalAccessException, null,"???");
        }
        catch (InvocationTargetException invocationTargetException)
        {
            ExceptionHandlerCaller.handleInvocationTargetException(invocationTargetException, null,"???");
            
        }
        catch(Exception exception){
            for(int i=0;i<exception.getStackTrace().length;++i){
                System.out.println(exception.getStackTrace()[i]);
            }
        }
        finally {
            return frame;
        }
    }
}
