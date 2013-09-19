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
    private static Dal dataAccesLayer;

    public static Dal getDataAccesLayer() {
        return dataAccesLayer;
    }

    public static void setDataAccesLayer(Dal dataAccesLayer) {
        Operator.dataAccesLayer = dataAccesLayer;
    }
    
    public static void learnDependencies(){
        
    }
}
