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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noni
 */
public class MySql implements DAL {

    private Connection conn = null;
    private ArrayList<String> columns_array = new ArrayList<String>();
    private String table_name;
    private ArrayList<String> determinant_columns_array = new ArrayList<String>();
    private ArrayList<String> dependent_columns_array = new ArrayList<String>();

     public String getTable_name() {
        return table_name;
    }
     
    public ArrayList<String> getDeterminant_columns_array() {
        return determinant_columns_array;
    }
    
    public ArrayList<String> getDependent_columns_array() {
        return dependent_columns_array;
    }
    
    //kiserlet erika
    @Override
    public boolean isFd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns) {
        String query = "select count(*) "
                + "from " + table + " t1, " + table + " t2"
                + " where (";
        for (int i = 0; i < determinantColumns.size(); i++) {
            if (i > 0) {
                query += " and ";
            }
            query += "(t1." + determinantColumns.get(i) + " = t2." + determinantColumns.get(i) + " )";

        }
        query += " ) and ( ";

        for (int i = 0; i < dependentColumns.size(); i++) {
            if (i > 0) {
                query += " or ";
            }
            query += "(t1." + dependentColumns.get(i) + " <> t2." + dependentColumns.get(i) + " )";

        }
        query += " ) ";
        Statement stmt_fd = null;
        ResultSet fd = null;
        try {
           
            stmt_fd = conn.createStatement();
            fd = null;
            fd = stmt_fd.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            while (fd.next()) {
                if (fd.getString(1).equalsIgnoreCase("0")) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException ex) {

            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

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

        String drivers = "com.mysql.jdbc.Driver";
        System.setProperty(drivers, "");
        try {
            conn = DriverManager.getConnection(url + port + "/" + dbName, userName, password);
            System.out.println("Database Connected!");
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
    
<<<<<<< HEAD
    
=======
    public void combinations(final AbstractList columns, int[] helpNumbers, final int k) {
        int[] result = new int[k];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        boolean done = false;
        while (!done) {
            System.out.println("Meghatarozo:" + Arrays.toString(result));
            for (int j = 0; j < result.length; ++j) {
                System.out.print(columns.get(result[j]-1) + " ");
            }
            System.out.println();
            
            for (int i = 1; i <= columns_array.size() - 1; ++i) {
                combinations(columns_array, i, result);
            }
            done = getNext(result, columns.size(), k);
        }
    }

>>>>>>> c6e70b17cf324e55130364d8ba0c78237334bce1
    @Override
    public void generate() {
        try {
            Statement stmt_tables = null;
            ResultSet tables = null;
            stmt_tables = conn.createStatement();
            tables = null;
            tables = stmt_tables.executeQuery("show tables;");
            while (tables.next()) {
                System.out.println("Table name: " + tables.getString(1));
                table_name = tables.getString(1);
                Statement stmt_columns = null;
                ResultSet columns = null;
                stmt_columns = conn.createStatement();
                columns = null;
                columns = stmt_columns.executeQuery("show columns from " + tables.getString(1) + ";");
                int key = 1;
                while (columns.next()) {
                    if (!(columns.getString(4)).equalsIgnoreCase("PRI")) {
                        columns_array.add(columns.getString(1));
                        ++key;
                    }
                }
                System.out.print("Columns: ");
                for (int i = 0; i < columns_array.size(); ++i) {
                    System.out.print(columns_array.get(i) + " ");
                }
                System.out.println();
                int[] helpNumbers = new int[columns_array.size()];
                for (int i = 0; i < columns_array.size(); ++i) {
                    helpNumbers[i] = i + 1;
                }

                for (int i = 1; i <= (columns_array.size() - 1); ++i) {
                    combinations(columns_array,helpNumbers, i);
                }
                columns_array.clear();

            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void combinations(final AbstractList columns, int[] helpNumbers, final int k) {
        int[] result = new int[k];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        boolean done = false;
        while (!done) {
            determinant_columns_array.clear();
            System.out.println("Meghatarozo:" + Arrays.toString(result));
            for (int j = 0; j < result.length; ++j) {
                System.out.print(columns.get(result[j]-1) + " ");
                determinant_columns_array.add(columns.get(result[j]-1).toString());
            }
            System.out.println();
            
            for (int i = 1; i <= columns_array.size() - 1; ++i) {
                combinations(columns_array, i, result);
            }
            done = getNext(result, columns.size(), k);
        }
        

    }

    
    public void combinations(final AbstractList columns, final int k, final int[] melyikne) {
        int[] result = new int[k];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        boolean done = false;
        boolean ok = true;
        while (!done) {
            for (int i = 0; i < result.length; ++i) {
                for (int j = 0; j < melyikne.length; ++j) {
                    if (result[i] == melyikne[j]) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                System.out.println("Meghatarozott:" + Arrays.toString(result));
                for (int j = 0; j < result.length; ++j) {
                    System.out.print(columns.get(result[j]-1) + " ");
                    dependent_columns_array.add(columns.get(result[j]-1).toString());
                }
                //System.out.println("Table name: " + table_name);
                //System.out.println("Determinant: " + determinant_columns_array);
                //System.out.println("Dependent: " + dependent_columns_array);
                if (isFd(table_name, determinant_columns_array, dependent_columns_array)){
                    System.out.println("Is FD");
                }
                else{
                    System.out.println("is not Fd");
                }
                System.out.println();
                dependent_columns_array.clear();
            }
            done = getNext(result, columns.size(), k);
            ok = true;
            
        }
    }


    public boolean getNext(final int[] helpNumbers, final int n, final int k) {
        int target = k - 1;
        helpNumbers[target]++;
        if (helpNumbers[target] > ((n - (k - target)) + 1)) {
            while (helpNumbers[target] > ((n - (k - target)))) {
                target--;
                if (target < 0) {
                    break;
                }
            }
            if (target < 0) {
                return true;
            }
            helpNumbers[target]++;
            for (int i = target + 1; i < helpNumbers.length; i++) {
                helpNumbers[i] = helpNumbers[i - 1] + 1;
            }
        }
        return false;
    }
}
