/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import exceptionhandler.ExceptionHandlerCaller;
import java.awt.Dimension;

/**
 *
 * @author noni
 */
public class JFrameManager {

    public static JFrame createJFrame(Class c, String name, Component parentComponent) {
        JFrame frame = null;
        try {
            frame = (JFrame) c.getDeclaredConstructor(new Class[]{String.class}).newInstance(name);
            if (parentComponent == null) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } else {
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            //frame.setLocationRelativeTo(parentComponent);

            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            Dimension windowSize = new Dimension(800, 500);
            frame.setSize((int) windowSize.getWidth(), (int) windowSize.getHeight());
            frame.setLocation((int) ((screenSize.getWidth() - windowSize.getWidth()) / 2), (int) ((screenSize.getHeight() - windowSize.getHeight())) / 2);
            frame.setResizable(false);
            frame.setTitle(name);
            frame.setVisible(true);
        } catch (InstantiationException instantiationException) {
            ExceptionHandlerCaller.handleInstantiationException(instantiationException, null, "???");
        } catch (NoSuchMethodException noSuchMethodException) {
            ExceptionHandlerCaller.handleNoSuchMethodException(noSuchMethodException, null, "???");
        } catch (IllegalAccessException illegalAccessException) {
            ExceptionHandlerCaller.handleIllegalAccessException(illegalAccessException, null, "???");
        } catch (InvocationTargetException invocationTargetException) {
            ExceptionHandlerCaller.handleInvocationTargetException(invocationTargetException, null, "???");

        } catch (Exception exception) {
            for (int i = 0; i < exception.getStackTrace().length; ++i) {
                System.out.println(exception.getStackTrace()[i]);
            }
        } finally {
            return frame;
        }
    }
}
