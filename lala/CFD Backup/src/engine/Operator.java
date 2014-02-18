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
        AbstractList<FDScenario> FDs = new ArrayList<FDScenario>();
        AbstractList<FDScenario> CFDs = new ArrayList<FDScenario>();
        AbstractList<FDScenario> AR = new ArrayList<FDScenario>();
        AbstractList<String> tableNames = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        for (int i = 0; i < tableNames.size(); ++i) {
            String currentTableName = tableNames.get(i);
            AbstractList<String> columnNames = DBMSManager.DALFactory(Settings.getRdbms()).getColumnsOfTable(currentTableName);
           // AbstractList<String> conditions = DBMSManager.DALFactory(Settings.getRdbms()).getConditions(currentTableName, columnNames.get(i));
            
           //FDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getFDs(currentTableName, columnNames)));
           CFDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getCFDs(currentTableName, columnNames)));
           //AR.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getAR(currentTableName, columnNames)));
           //System.out.println(DBMSManager.DALFactory(Settings.getRdbms()).getFDs(currentTableName, columnNames));
           //System.out.println(DBMSManager.DALFactory(Settings.getRdbms()).getCFDs(currentTableName, columnNames));
           //System.out.println(DBMSManager.DALFactory(Settings.getRdbms()).getAR(currentTableName, columnNames));
            for(int k=0;k < FDs.size();++k){
               System.out.println(FDs.get(k).determinantColumns);
               System.out.println(FDs.get(k).dependentColumns);
            }
//           for(int k=0;k < CFDs.size();++k){
//               System.out.println(CFDs.get(k).determinantColumns);
//               System.out.println(CFDs.get(k).dependentColumns);
//               System.out.println(CFDs.get(k).condition);
//            }
            
        }
    }
}
