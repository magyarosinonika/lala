/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.util.AbstractList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author noni
 */
public class MySql implements DAL {
    
    ArrayList<String> primary_keys_array = new ArrayList<String>();
    //ArrayList<String> columns_array = new ArrayList<String>();
    Hashtable columns_array = new Hashtable();

    @Override
    public boolean isFd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns) {
        String query = "select count(*) " + 
                      "from " + table + " t1, " + table + " t2" +
                      " where (";
        for( int i = 0;i<determinantColumns.size();i++ ){
            if ( i > 0 ){
                query += " and ";
            }
            query += "(t1." + determinantColumns.get(i) + " = t2." + determinantColumns.get(i) + " )";
                   
        }
        
        query += " ) and ( "; 

        for( int i = 0;i<dependentColumns.size();i++ ){
            if ( i > 0 ){
                query += " or ";
            }
            query += "(t1." + dependentColumns.get(i) + " <> t2." + dependentColumns.get(i) + " )";
                   
        }
        query += " ) ";
        //System.out.println(query);
        //A queryt le kell futtatni az adatbazisban es a vissza teritett eredmenyt vizsgaljuk meg attol fugg a return
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

    // a kombinaciok kigeneralasat kulon metodusba kell rakni
    @Override
    public boolean connect(String url, String userName, String password, int port, String dbName) {
        Connection conn = null;
        String drivers = "com.mysql.jdbc.Driver";
        System.setProperty(drivers, "");
        try {
            conn = DriverManager.getConnection(url + port + "/" + dbName, userName, password);
            System.out.println("Database Connected!");

            
            Statement stmt_tables = null;
            ResultSet tables = null;
            stmt_tables = conn.createStatement();
            tables = null;
            tables = stmt_tables.executeQuery("show tables;");
            while (tables.next()) {
                System.out.println("Table name: " + tables.getString(1));
                Statement stmt_columns = null;
                ResultSet columns = null;
                stmt_columns = conn.createStatement();
                columns = null;
                columns = stmt_columns.executeQuery("show columns from " + tables.getString(1) + ";");

                Statement stmt_primary_keys = null;
                ResultSet primary_keys = null;
                stmt_primary_keys = conn.createStatement();
                primary_keys = null;
                //nem kell a show keys mert a show columns mar vissza teriti a kulcs oszlopokat:columns.getString
                primary_keys = stmt_primary_keys.executeQuery("SHOW KEYS FROM " + tables.getString(1) + " WHERE Key_name = 'PRIMARY';");

                while (primary_keys.next()) {
                    //System.out.println("Primary key column: " + result3.getString("Column_name"));
                    primary_keys_array.add(primary_keys.getString("Column_name"));
                }
                int key=1;
                while (columns.next()) {
                    if (!(primary_keys_array.contains(columns.getString(1)))) {
                        //System.out.println("Column name: " + columns.getString(1));
                        //columns_array.add(columns.getString(1));
                        columns_array.put(key, columns.getString(1));
                        ++key;
                    }
                }
                System.out.print("Columns: ");
                for (int i = 1; i <= columns_array.size(); ++i) {
                    System.out.print(columns_array.get(i) + " ");
                }
                System.out.println();

                //System.out.println(columns_array.size());
                //for (int i = 1; i <= columns_array.size(); ++i) {
                   for (int i = 1; i <= (columns_array.size() - 1); ++i) {
                       //System.out.println("megtarozo");
                        combinations(columns_array, i);
                        for (int j = 1; j <= columns_array.size(); ++j) {
                            for (int k = 1; k <= columns_array.size(); ++k) {
                                //System.out.println("megtarozott");
                                    //combinations(columns_array.size(), j, k);
                            }
                        }
                    }
                //}

                columns_array.clear();

            }
            conn.close();

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

    public void combinations(final Hashtable columns, final int r) {
        int[] res = new int[r];
        for (int i = 0; i < res.length; i++) {
            res[i]=i+1;
        }
        boolean done = false;
        while (!done) {
            System.out.println("Meghatarozo:"+Arrays.toString(res));
            for(int j=0;j<res.length;++j){
                System.out.print(columns.get(res[j])+" ");
            }
            System.out.println();
            for(int i=1;i<=columns_array.size()-1;++i){
                combinations(columns_array, i, res);
            }
            done = getNext(res, columns.size(), r);
            
            
        }
    }
    
    public void combinations(final Hashtable columns, final int r, final int[] melyikne) {
        int[] res = new int[r];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        boolean done = false;
        boolean jo = true;
        while (!done) {
            for (int k = 0; k < res.length; ++k) {
                for(int j=0;j<melyikne.length;++j){
                    if(res[k]==melyikne[j]){
                        jo = false;
                        break;
                    }
                }
            }
            if (jo) {
                System.out.println("Meghatarozott:"+Arrays.toString(res));
                for(int j=0;j<res.length;++j){
                System.out.print(columns.get(res[j])+" ");
            }
            System.out.println();
            }
            done = getNext(res, columns.size(), r);
            jo = true;
        }
    }

    //attanulmanyozni es atnevezni
    public boolean getNext(final int[] num, final int n, final int r) {
        int target = r - 1;
        num[target]++;
        if (num[target] > ((n - (r - target)) + 1)) {
            while (num[target] > ((n - (r - target)))) {
                target--;
                if (target < 0) {
                    break;
                }
            }
            if (target < 0) {
                return true;
            }
            num[target]++;
            for (int i = target + 1; i < num.length; i++) {
                num[i] = num[i - 1] + 1;
            }
        }
        return false;
    }
}
