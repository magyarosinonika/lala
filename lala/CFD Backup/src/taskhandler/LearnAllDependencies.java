/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class LearnAllDependencies extends BaseRunnable{
    
    public void run() {
        try {
            DependencyLearner.learnAllDependencies();
        } catch (InterruptedException ex) {
            Logger.getLogger(LearnAllDependencies.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
