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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author noni
 */
public class MySql implements Dal {
    
    ArrayList<String> primary_keys_array = new ArrayList<String>();
    ArrayList<String> columns_array = new ArrayList<String>();

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
                primary_keys = stmt_primary_keys.executeQuery("SHOW KEYS FROM " + tables.getString(1) + " WHERE Key_name = 'PRIMARY';");

                while (primary_keys.next()) {
                    //System.out.println("Primary key column: " + result3.getString("Column_name"));
                    primary_keys_array.add(primary_keys.getString("Column_name"));
                }

                while (columns.next()) {
                    if (!(primary_keys_array.contains(columns.getString(1)))) {
                        //System.out.println("Column name: " + columns.getString(1));
                        columns_array.add(columns.getString(1));
                    }
                }
                System.out.print("Columns: ");
                for (int i = 0; i < columns_array.size(); ++i) {
                    System.out.print(columns_array.get(i) + " ");
                }
                System.out.println();

                //System.out.println(columns_array.size());
                //for (int i = 1; i <= columns_array.size(); ++i) {
                   for (int i = 1; i <= (columns_array.size() - 1); ++i) {
                       System.out.println("megtarozo");
                        combinations(columns_array.size(), i);
//                        for (int j = 1; j <= columns_array.size(); ++j) {
//                            for (int k = 1; k <= columns_array.size(); ++k) {
//                                System.out.println("megtarozott");
//                                combinations(columns_array.size(), j, k);
//                            }
//                        }
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

    public void combinations(final int n, final int r) {
        int[] res = new int[r];
        //ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < res.length; i++) {
            res[i]=i+1;
        }
        boolean done = false;
        while (!done) {
            //System.out.println(res);
            System.out.println(Arrays.toString(res));
//            for(int i=0;i<columns_array.size()-1;++i){
//                combinations(columns_array.size(), i, res);
//            }
//            combinations(columns_array.size(), r, null);
//            Integer[] temp_res = new Integer[r];
//            temp_res=res.toArray(temp_res);
            done = getNext(res, n, r);
        }
    }
    
    public void combinations(final int n, final int r, final ArrayList<Integer> melyikne) {
        //System.out.println("From tabel: " + tableName);
        int[] res = new int[r];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        boolean done = false;
        boolean jo = true;
        while (!done) {
            for (int k = 0; k < res.length; ++k) {
                
                    if (melyikne.contains(res[k])) {
                        jo = false;
                        break;
                    }
                

            }
            if (jo) {
                System.out.println(Arrays.toString(res));
//                System.out.println("Meghatarozo: "+columnsName.get(melyikne-1));
//                System.out.print("Meghatarozott: " );
//                for(int i=0;i<res.length;++i){
//                    
//                    System.out.print(columnsName.get(res[i]-1) + " ");
//                }
//                System.out.println();

                //isFd(tableName, columnsName.get(melyikne-1), columnsName);

            }
            done = getNext(res, n, r);
            jo = true;
        }
    }

/////////
    public boolean getNext(final int[] num, final int n, final int r) {
        int target = r - 1;
        num[target]++;
        if (num[target] > ((n - (r - target)) + 1)) {
            // Carry the One
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
