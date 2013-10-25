/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.init.DBMSManager;
import engine.init.Settings;
import java.util.AbstractList;
import java.util.ArrayList;


/**
 *
 * @author noni
 */
public class Operator extends Thread {

    private static DAL dataAccesLayer;

    public static DAL getDataAccesLayer() {
        return dataAccesLayer;
    }

    public static void setDataAccesLayer(DAL dataAccesLayer) {
        Operator.dataAccesLayer = dataAccesLayer;
    }

    public static void learnDependencies() {
       // A learnDependencies meg kell hívja ezt a metódust és egy lokális változóba be kell tegye a táblák nevének a halmazát.
       //learnDependencies meg kell hívja minden táblára a getFDs-t, amelyik visszatérít egy FD halmazt. 
       // System.out.println(isFd(getTable_name(), getDeterminant_columns_array(), getDependent_columns_array())) 
        //String tableName=null;
        AbstractList<FDScenario> FDs =new ArrayList<FDScenario>(); 
        AbstractList<String> tableNames = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames(); 
        for (int i = 0; i < tableNames.size(); ++i) { 
            FDs.addAll( (DBMSManager.DALFactory(Settings.getRdbms()).getFDs(tableNames.get(i)))); 
        }
    }
}
