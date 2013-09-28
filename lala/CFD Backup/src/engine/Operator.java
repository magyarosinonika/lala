/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

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
       // System.out.println(isFd(getTable_name(), getDeterminant_columns_array(), getDependent_columns_array()));
    }
}
