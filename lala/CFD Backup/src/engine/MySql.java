/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import UI.JFrameManager;
import UI.forms.SettingsForm;
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
        if (connection == null) {
            prepareConnection();
        }
        return connection;
    }
    
    @Override
    public void setConnection(Connection conn){
        connection = conn;
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

            stmt_fd = getConnection().createStatement();
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
        condition = condition.trim();
        String firstCondition = " t1." + condition + " ";
        String secondCondition = " t2." + condition + " ";
        String query = "select count(*) "
                + "from " + table + " t1, " + table + " t2"
                + " where (" + firstCondition + ") and  (" + secondCondition + ") and( ";

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
        Statement statemetCFd = null;
        ResultSet cfd = null;
        try {

            statemetCFd = getConnection().createStatement();
            cfd = null;
            cfd = statemetCFd.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (cfd.next()) {
                if (cfd.getString(1).equalsIgnoreCase("0")) {
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
    public boolean isAr(String table, String condition, AbstractList<String> dependentColumns) {
        condition = condition.trim();
        String firstCondition = " t1." + condition + " ";
        String secondCondition = " t2." + condition + " ";
        String query = "select count(*) "
                + "from " + table + " t1, " + table + " t2"
                + " where (" + firstCondition + " and  " + secondCondition + ") and( ";

        for (int i = 0; i < dependentColumns.size(); i++) {
            if (i > 0) {
                query += " or ";
            }
            query += "(t1." + dependentColumns.get(i) + " <> t2." + dependentColumns.get(i) + " )";
        }
        query += " ) ";
        System.out.println(query);
        Statement statemetAR = null;
        ResultSet ar = null;
        try {

            statemetAR = getConnection().createStatement();
            ar = null;
            ar = statemetAR.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (ar.next()) {
                if (ar.getString(1).equalsIgnoreCase("0")) {
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
            stmt_conditions = getConnection().createStatement();//statement kiirva es nem alulvonas
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
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select 1 from dual;");
            return true; // connection is valid
            //}
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void prepareConnection() {
        if (Settings.getDbName() != null && Settings.getHost() != null && Settings.getPassword() != null && Settings.getPort() != 0 && Settings.getRdbms() != null && Settings.getUserName() != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                engine.init.DBMSManager.DALFactory(Settings.getRdbms()).connect("jdbc:mysql://" + Settings.getHost() + ":", Settings.getUserName(), Settings.getPassword(), Settings.getPort(), Settings.getDbName());
            } catch (Exception ex) {
                if (ex.getMessage().contains("For input string")) {
                    JOptionPane.showMessageDialog(null, "Please enter number to the Port Field!", "Warning", 0);
                } else {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Missing settings!", "ERROR", 0);
        }
    }

    @Override
    public boolean connect(String url, String userName, String password, int port, String dbName) {
        String drivers = "com.mysql.jdbc.Driver";
        System.setProperty(drivers, "");
        try {
            connection = DriverManager.getConnection(url + port + "/" + dbName, userName, password);
            JOptionPane.showMessageDialog(null, "Database Connected!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Communications link failure")) {
                JOptionPane.showMessageDialog(null, "Connection failed!", "Warning", 0);
            } else {
                if (ex.getMessage().contains("Access denied for user")) {
                    JOptionPane.showMessageDialog(null, "Access denied for user, please check your admission details", "Warning", 0);
                } else {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
                }
                connection = null;
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
        AbstractList<String> tablesArray = new ArrayList<String>();
        AbstractList<String> columnArray = new ArrayList<String>();
        tablesArray = getTableNames();
        for (int i = 0; i < tablesArray.size(); ++i) {
            System.out.println("Table name:");
            System.out.println(tablesArray.get(i));
            columnHelper = new ColumnHelper(tablesArray.get(i));
            columnArray = getColumnsOfTable(tablesArray.get(i));
            columnHelper.setColumnsArray(columnArray);
            System.out.print("Columns: ");
            for (int k = 0; k < columnHelper.getColumnsArray().size(); ++k) {
                System.out.print(columnHelper.getColumnsArray().get(k) + " ");
            }
            System.out.println();
            int[] helpNumbers = new int[columnHelper.getColumnsArray().size()];
            for (int j = 0; j < columnHelper.getColumnsArray().size(); ++j) {
                helpNumbers[j] = j + 1;
            }

            for (int l = 1; l <= (columnHelper.getColumnsArray().size() - 1); ++l) {
                //columnHelper.combinations(columnHelper.getColumnsArray(), helpNumbers, l);
                columnHelper.combinations(columnHelper.getColumnsArray(), helpNumbers, l, tablesArray.get(i));
            }
            columnArray.clear();
        }
    }

    public AbstractList<String> getTableNames() {
        AbstractList<String> tableNameArray = new ArrayList<String>();
        try {
            Statement statementTables = null;
            ResultSet tables = null;
            statementTables = getConnection().createStatement();
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

    public AbstractList<String> getColumnsOfTable(String tableName) {
        AbstractList<String> columnsNameArray = new ArrayList<String>();
        try {
            Statement statementColumns = null;
            ResultSet columns = null;
            statementColumns = getConnection().createStatement();
            columns = null;
            columns = statementColumns.executeQuery("show columns from " + tableName + ";");
            while (columns.next()) {
                if (!(columns.getString(4)).equalsIgnoreCase("PRI")) {
                    columnsNameArray.add(columns.getString(1));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", 0);
        }
        return columnsNameArray;
    }

    public AbstractList<FDScenario> getFDs(String tableName) {
        AbstractList<FDScenario> listOfFDs = new ArrayList<FDScenario>();
        AbstractList<FDScenario> combinations = columnHelper.getAllCombinations();
        for (int i = 0; i < combinations.size(); ++i) {
            if (isFd(tableName, combinations.get(i).determinantColumns, combinations.get(i).dependentColumns)) {
                listOfFDs.add(combinations.get(i));
            }
        }
        return listOfFDs;
    }

    public AbstractList<FDScenario> getCFDs(String tableName) {
        AbstractList<FDScenario> listOfCFDs = new ArrayList<FDScenario>();
        AbstractList<String> columns = getColumnsOfTable(tableName);
        AbstractList<FDScenario> combinations = columnHelper.getAllCombinations();

        for (int columnName = 0; columnName < columns.size(); ++columnName) {
            AbstractList<String> conditions = getConditions(tableName, columns.get(columnName));
            for (int condition = 0; condition < conditions.size(); ++condition) {
                for (int i = 0; i < combinations.size(); ++i) {
                    if (isCfd(tableName, combinations.get(i).determinantColumns, combinations.get(i).dependentColumns, conditions.get(condition))) {
                        listOfCFDs.add(combinations.get(i));
                    }
                }
            }
        }
        return listOfCFDs;
    }

    public AbstractList<FDScenario> getAR(String tableName) {
        AbstractList<FDScenario> listOfAr = new ArrayList<FDScenario>();
        AbstractList<String> columns = getColumnsOfTable(tableName);
        AbstractList<FDScenario> combinations = columnHelper.getAllCombinations();
        for (int columnName = 0; columnName < columns.size(); ++columnName) {
            AbstractList<String> conditions = getConditions(tableName, columns.get(columnName));
            for (int condition = 0; condition < conditions.size(); ++condition) {
                for (int i = 0; i < combinations.size(); ++i) {
                    if (isAr(tableName, conditions.get(condition), combinations.get(i).dependentColumns)) {
                        listOfAr.add(combinations.get(i));
                    }
                }
            }
        }
        return listOfAr;
    }
}
