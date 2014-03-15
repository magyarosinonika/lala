/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

/**
 *
 * @author Admin
 */
public class LearnDependenciesThread extends BaseThread{
    
    public void run() {
        engine.Operator.learnDependencies();
    }
    
}
