/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.AbstractList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author noni
 */
public class MySql implements Dal {

    @Override
    public boolean isFd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns) {
        return true;
    }

    @Override
    public boolean isCfd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition) {
        return true;
    }

    @Override
    public boolean isAr(String table, String condition, AbstractList<String> dependentColumns) {
        return true;
    }

    @Override
    public AbstractList<String> getConditions(String table, String column) {
        AbstractList<String> testlist = null;
        testlist.add("Is a test!");
        return testlist;
    }

    @Override
    public boolean connect(String url, String userName, String password, int port, String dbName) {
        //return true;
        Connection conn = null;
        String drivers = "com.mysql.jdbc.Driver";
        System.setProperty(drivers, "");
        try {
            conn = DriverManager.getConnection(url + port + "/" + dbName, userName, password);
            System.out.println("Database Connected!");
            
            
//            Statement stmt = null;
//            ResultSet result = null;
//            stmt = conn.createStatement();
//            result = null;
//            String pa,us;
//            result = stmt.executeQuery("select * from fips_regions ");
//
//            while (result.next()) {
//                us=result.getString("code");
//                pa = result.getString("name");              
//                System.out.println(us+"  "+pa);
//            }
//
//            conn.close();
//            
            
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }



    }

    @Override
    public void insert() {
        System.out.println("insert");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }
}
