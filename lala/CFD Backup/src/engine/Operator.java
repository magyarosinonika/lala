/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author noni
 */
public class Operator extends Thread{
    private static DAL dataAccesLayer;

    public static DAL getDataAccesLayer() {
        return dataAccesLayer;
    }

    public static void setDataAccesLayer(DAL dataAccesLayer) {
        Operator.dataAccesLayer = dataAccesLayer;
    }
    
    public static void learnDependencies(){
        
    }
}
