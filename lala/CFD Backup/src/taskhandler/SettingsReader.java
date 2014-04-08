/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

import engine.init.Settings;

/**
 *
 * @author noni
 */
public class SettingsReader extends BaseRunnable {
    
    public void run() {
        Settings.initializeSettings();
    }
    
}
