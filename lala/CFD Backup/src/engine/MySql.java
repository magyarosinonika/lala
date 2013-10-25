/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import engine.init.ColumnHelper;
import engine.init.Settings;
import java.util.AbstractList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author noni
 */
public class MySql implements DAL {

    private static Connection connection;
    private ArrayList<String> columnsArray = new ArrayList<String>();
    private ColumnHelper columnHelper;

    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * 
     * @param table The name of the table
     * @param determinantColumns The set of determinant columns
     * @param dependentColumns The set of dependent columns
     * @return 
     */
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

            stmt_fd = connection.createStatement();
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

    /** 
     * Checks whether the parameters describe a CFD 
     * @param table The name of the table 
     * @param determinantColumns The set of determinant columns 
     * @param dependentColumns The set of dependent columns 
     * @param condition The condition of the potential CFD 
     * @return true if the parameters describe a CFD and false otherwise 
     */
    @Override
    public boolean isCfd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition) {
        String query = "select count(*) "
                + "from " + table + " t1, " + table + " t2"
                + " where (" + condition + ") and ( ";

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
        System.out.println(query);
        return true;
    }

    @Override
    public boolean isAr(String table, String condition, AbstractList<String> dependentColumns) {
        String query = "select count(*) "
                + "from " + table + " t1, " + table + " t2"
                + " where (" + condition + ") and ( ";

        for (int i = 0; i < dependentColumns.size(); i++) {
            if (i > 0) {
                query += " or ";
            }
            query += "(t1." + dependentColumns.get(i) + " <> t2." + dependentColumns.get(i) + " )";
        }
        query += " ) ";
        System.out.println(query);
        return true;
    }

    @Override
    public AbstractList<String> getConditions(String table, String column) {
        AbstractList<String> conditionList = new ArrayList<String>();
        String query = "select " + column
                + " from " + table
                + " group by " + column
                + " having count(*) >= "
                + Settings.getOccurrenceNumber();
        Statement stmt_conditions = null;
        ResultSet conditions = null;
        try {
            stmt_conditions = connection.createStatement();//statement kiirva es nem alulvonas
            conditions = null;
            conditions = stmt_conditions.executeQuery(query);
            while (conditions.next()) {
                conditionList.add(column + "='" + conditions.getString(1) + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);//joption pane hasznalni 
        }
        //System.out.println("////////"+query); 
        //System.out.println("////"+condition_list.toString()); 
        return conditionList;// a conditionList ben vannak az osszetett stringek
    }

    @Override
    public boolean checkConnection() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //conn.isClosed();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select 1 from dual;");
            return true; // connection is valid
            //}
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean connect(String url, String userName, String password, int port, String dbName) {
        String drivers = "com.mysql.jdbc.Driver";
        System.setProperty(drivers, "");
        try {
            connection = DriverManager.getConnection(url + port + "/" + dbName, userName, password);
            System.out.println("Database Connected!");
            return true;
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Communications link failure")) {
                JOptionPane.showMessageDialog(null, "Connection failed!", "Warning", 0);
            } else {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
            }
            return false;
        }
//        try {
//            Settings connSettings = new Settings();
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
//        }

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

    @Override
    public void generate() {
        //System.out.println(connection);
        try {
            Statement stmt_tables = null;
            ResultSet tables = null;
            stmt_tables = connection.createStatement();
            tables = null;
            tables = stmt_tables.executeQuery("show tables;");
            while (tables.next()) {
                System.out.println("Table name: " + tables.getString(1));
                //column helpert felvittem az adattagokhoz hogy erjem  el a getFD-be
                //table_name = tables.getString(1);
                columnHelper = new ColumnHelper(tables.getString(1));
                Statement stmt_columns = null;
                ResultSet columns = null;
                stmt_columns = connection.createStatement();
                columns = null;
                //columns = stmt_columns.executeQuery("show columns from " + tables.getString(1) + ";");
                columns = stmt_columns.executeQuery("show columns from " + columnHelper.getTableName() + ";");
                int key = 1;
                while (columns.next()) {
                    if (!(columns.getString(4)).equalsIgnoreCase("PRI")) {
                        columnsArray.add(columns.getString(1));
                        ++key;
                    }
                }
                columnHelper.setColumnsArray(columnsArray);
                System.out.print("Columns: ");
                for (int i = 0; i < columnHelper.getColumnsArray().size(); ++i) {
                    System.out.print(columnHelper.getColumnsArray().get(i) + " ");
                }
                System.out.println();
                int[] helpNumbers = new int[columnHelper.getColumnsArray().size()];
                for (int i = 0; i < columnHelper.getColumnsArray().size(); ++i) {
                    helpNumbers[i] = i + 1;
                }

                for (int i = 1; i <= (columnHelper.getColumnsArray().size() - 1); ++i) {
                    columnHelper.combinations(columnHelper.getColumnsArray(), helpNumbers, i);
                }
                columnsArray.clear();

            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);

        }
    }

    public AbstractList<String> getTableNames() {
        AbstractList<String> tableNameArray = new ArrayList<String>();
        try {
            Statement statementTables = null;
            ResultSet tables = null;
            statementTables = connection.createStatement();
            tables = null;
            tables = statementTables.executeQuery("show tables;");
            while (tables.next()) {
                System.out.println("Table name: " + tables.getString(1));
                tableNameArray.add(tables.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
        }
        return tableNameArray;
    }
    
    public AbstractList<FDScenario> getFDs(String tableName){ 
        AbstractList<FDScenario> listOfFDs = new ArrayList<FDScenario>(); 
        AbstractList<FDScenario> combinations = columnHelper.getAllCombinations() ; 
        for (int i=0;i<combinations.size();++i){ 
            if(isFd(tableName,combinations.get(i).determinantColumns,combinations.get(i).dependentColumns )){
                listOfFDs.add(combinations.get(i));
            }
        }
        return listOfFDs; 
    }
    
    /*public AbstractList<String> getCFDs(String tableName){ 
         * AbstractList<String> listOfFDs =new ArrayList<String>(); 
         * AbstractList<String> conditions = new ArrayList<String>();
         * FDScenario combinations[]=columnHelper.getAllCombinations() ; 
         * return listOfFDs; 
     * }*/ 
    /* AbstractList<CFD> getCFDs(tableName)
         * foreach columnName in getColumnsOfTable(tableName) do 
         * conditions <- getConditions(tableName, columnName)
             * foreach condition in conditions do
                 * while currentCombination <- nextCombination(someParameters) do
                 * if (isCFD(currentCombination, condition)) then 
                     * returnCFDs.add(new CFD(currentCombination)) 
                 * end if 
                 * end while
             * end for 
         * end for 
     * end getCFDs*/
}
