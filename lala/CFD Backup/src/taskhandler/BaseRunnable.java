/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

/**
 *
 * @author noni
 */
public class BaseRunnable implements Runnable {
    
    protected Thread thread;
    
    public BaseRunnable() {
        runBaseRunnable();
    }
    
    public BaseRunnable(char nonSense){
        
    }
    
    protected void runBaseRunnable() {
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public Thread getThread() {
        return this.thread;
    }
    
    @Override
    public void run() {
        
    }
    
    public void start() {
        
    }

    public void join() throws InterruptedException{
        this.thread.join();
    }
    
    
}
