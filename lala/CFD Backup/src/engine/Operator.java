/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.init.DBMSManager;
import engine.init.Settings;
import java.util.AbstractList;
import java.util.ArrayList;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

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

    public static void learnFDs() {
        AbstractList<FDScenario> FDs = new ArrayList<FDScenario>();
        AbstractList<String> tableNames = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        for (int i = 0; i < tableNames.size(); ++i) {
            String currentTableName = tableNames.get(i);
            AbstractList<String> columnNames = DBMSManager.DALFactory(Settings.getRdbms()).getColumnsOfTable(currentTableName);
            FDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getFDs(currentTableName, columnNames)));
            DBMSManager.DALFactory(Settings.getRdbms()).createDependency(FDs);
        }
    }

    public static void learnCFDs() {
        AbstractList<FDScenario> CFDs = new ArrayList<FDScenario>();
        AbstractList<String> tableNames = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        for (int i = 0; i < tableNames.size(); ++i) {
            String currentTableName = tableNames.get(i);
            AbstractList<String> columnNames = DBMSManager.DALFactory(Settings.getRdbms()).getColumnsOfTable(currentTableName);
            CFDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getCFDs(currentTableName, columnNames)));
            DBMSManager.DALFactory(Settings.getRdbms()).createDependency(CFDs);
        }
    }

    public static void learnARs() {
        AbstractList<FDScenario> AR = new ArrayList<FDScenario>();
        AbstractList<String> tableNames = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        for (int i = 0; i < tableNames.size(); ++i) {
            String currentTableName = tableNames.get(i);
            AbstractList<String> columnNames = DBMSManager.DALFactory(Settings.getRdbms()).getColumnsOfTable(currentTableName);
            AR.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getAR(currentTableName, columnNames)));
            DBMSManager.DALFactory(Settings.getRdbms()).createDependency(AR);
        }
    }

    public static void learnDependencies() {
//        AbstractList<FDScenario> FDs = new ArrayList<FDScenario>();
//        AbstractList<FDScenario> CFDs = new ArrayList<FDScenario>();
//        AbstractList<FDScenario> AR = new ArrayList<FDScenario>();
        AbstractList<String> tableNames = DBMSManager.DALFactory(Settings.getRdbms()).getTableNames();
        for (int i = 0; i < tableNames.size(); ++i) {
//            String currentTableName = tableNames.get(i);
//            AbstractList<String> columnNames = DBMSManager.DALFactory(Settings.getRdbms()).getColumnsOfTable(currentTableName);
//            // AbstractList<String> conditions = DBMSManager.DALFactory(Settings.getRdbms()).getConditions(currentTableName, columnNames.get(i));
//            FDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getFDs(currentTableName, columnNames)));
//            CFDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getCFDs(currentTableName, columnNames)));
//            AR.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getAR(currentTableName, columnNames)));
//            DBMSManager.DALFactory(Settings.getRdbms()).createDependency(FDs);
//            DBMSManager.DALFactory(Settings.getRdbms()).createDependency(CFDs);
//            DBMSManager.DALFactory(Settings.getRdbms()).createDependency(AR);
            learnDependencies(tableNames.get(i));

        }
    }

    public static void learnDependencies(String currentTableName) {
        AbstractList<FDScenario> FDs = new ArrayList<FDScenario>();
        AbstractList<FDScenario> CFDs = new ArrayList<FDScenario>();
        AbstractList<FDScenario> AR = new ArrayList<FDScenario>();

        //String currentTableName = tableNames.get(i);
        AbstractList<String> columnNames = DBMSManager.DALFactory(Settings.getRdbms()).getColumnsOfTable(currentTableName);
        // AbstractList<String> conditions = DBMSManager.DALFactory(Settings.getRdbms()).getConditions(currentTableName, columnNames.get(i));
        FDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getFDs(currentTableName, columnNames)));
        CFDs.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getCFDs(currentTableName, columnNames)));
        AR.addAll((DBMSManager.DALFactory(Settings.getRdbms()).getAR(currentTableName, columnNames)));
        DBMSManager.DALFactory(Settings.getRdbms()).createDependency(FDs);
        DBMSManager.DALFactory(Settings.getRdbms()).createDependency(CFDs);
        DBMSManager.DALFactory(Settings.getRdbms()).createDependency(AR);


    }
}
