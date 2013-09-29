/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.init;

import engine.DAL;
import engine.MySql;

/**
 *
 * @author noni
 */
public class DBMSManager {

    public static DAL dal;

    public enum DBMSType {

        MYSQL(1);
        private final int value;

        private DBMSType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    
    public static DAL DALFactory(int key) {
        if (DBMSType.MYSQL.getValue() == key) {
            dal = new MySql();
        } else {
            dal = null;
        }
        return dal;
    }
    
    public static DAL DALFactory (String key) {
        return DALFactory(DBMSType.valueOf(key.toUpperCase()).getValue());
    }
}


