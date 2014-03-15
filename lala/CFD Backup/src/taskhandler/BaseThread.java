/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

/**
 *
 * @author noni
 */
public class BaseThread implements Runnable {
    
    protected Thread thread;
    
    public BaseThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public Thread getThread() {
        return this.thread;
    }
    
    public void run() {
        
    }
    
    public void start() {
        
    }
    
}
