/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import UI.JFrameManager;
import UI.forms.SettingsForm;
import com.mysql.jdbc.PreparedStatement;
import engine.init.ColumnHelper;
import engine.init.Combination;
import engine.init.Settings;
import java.math.BigInteger;
import java.security.SecureRandom;
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
    private int insertCount;

    @Override
    public Connection getConnection() {
        if (connection == null) {
            prepareConnection();
        }
        return connection;
    }

    @Override
    public void setConnection(Connection conn) {
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

    @Override
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

    @Override
    public AbstractList<FDScenario> getFDs(String tableName, AbstractList<String> columnNames) {
        int[] currentScenario = null;
        AbstractList<FDScenario> listOfFDs = new ArrayList<FDScenario>();
        do {
            if (currentScenario == null) {
                currentScenario = Combination.getNextScenario(columnNames.size(), 3);
            } else {
                currentScenario = Combination.getNextScenario(currentScenario, 3);
            }

            if (currentScenario != null) {

                AbstractList<String> determinantColumns = new ArrayList<String>();
                AbstractList<String> dependentColumns = new ArrayList<String>();

                for (int currentScenarioIndex = 0; currentScenarioIndex < currentScenario.length; ++currentScenarioIndex) {
                    switch (currentScenario[currentScenarioIndex]) {
                        case 1:
                            determinantColumns.add(columnNames.get(currentScenarioIndex));
                            break;
                        case 2:
                            dependentColumns.add(columnNames.get(currentScenarioIndex));
                            break;
                    }
                }


                if (isFd(tableName, determinantColumns, dependentColumns)) {
//                    if (listOfFDs.contains(new FDScenario(determinantColumns, dependentColumns))) {
//                        System.out.println("Mar tartalmazza ezt a sort!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    } else 
                    listOfFDs.add(new FDScenario(determinantColumns, dependentColumns));

                }
            }
        } while (currentScenario != null);

//        System.out.println(listOfFDs.size());
//        System.out.println("az fd-k listaja");
//         for(int k=0; k<listOfFDs.size();++k){
//            System.out.println("determinant: "+ listOfFDs.get(k).determinantColumns );
//            System.out.println("dependent: "+listOfFDs.get(k).dependentColumns);  
//            System.out.println();
//            }


        return listOfFDs;
    }

    public AbstractList<FDScenario> getCFDs(String tableName, AbstractList<String> columnNames) {
        AbstractList<FDScenario> listOfCFDs = new ArrayList<FDScenario>();
        int[] currentScenario = null;
        int columnsSize = columnNames.size();//columns number
        AbstractList<String>[] columnsConditions = new ArrayList[columnsSize];//mekkor nagysagu strineket foglaljak le?

        for (int i = 0; i < columnsSize; i++) {
            columnsConditions[i] = new ArrayList<String>();
        }

        for (int column = 0; column < columnsSize; ++column) {//annyi eleme van a tombnek ahany oszlop van
            columnsConditions[column].addAll(new ArrayList<String>(getConditions(tableName, columnNames.get(column))));
        }
        System.out.println("Kondiciok oszloponkent a parameterkent atadott tablabol:" + tableName);
        for (int i = 0; i < columnsConditions.length; ++i) {
            for (int j = 0; j < columnsConditions[i].size(); ++j) {
                System.out.println(columnsConditions[i].get(j));
            }
        }

        //felepitem az uj oszlopokat tartalmazo tombot kiveve a kondicio oszlopot
        for (int i = 0; i < columnsConditions.length; ++i) {
            //minden elem egy feltetel halmaz
            //for(int j=0 ;j<columnsConditions[i].size() ; ++j){
            //j az i. oszlop halamazank az indexe
            if (!columnsConditions[i].isEmpty()) {
                String myColumn = columnsConditions[i].get(0).substring(0, columnsConditions[i].get(0).indexOf("="));
                System.out.println("Kondicio oszlop: " + myColumn);

                AbstractList<String> columnNameswithoutConditionColumn = new ArrayList<String>();

                for (int column = 0; column < columnsSize; ++column) {

                    if (!columnNames.get(column).equalsIgnoreCase(myColumn)) {
                        columnNameswithoutConditionColumn.add(columnNames.get(column));
                    }
                }
//                for (int ii = 0; ii < columnNameswithoutConditionColumn.size(); ++ii) {
//                    System.out.println(" kondicio oszlop nelkuli oszlopnevek   " + columnNameswithoutConditionColumn.get(ii));
//                }

                do {


                    if (currentScenario == null) {
                        currentScenario = Combination.getNextScenario(columnNameswithoutConditionColumn.size(), 3);
                    } else {
                        currentScenario = Combination.getNextScenario(currentScenario, 3);
                    }

                    if (currentScenario != null) {

                        AbstractList<String> determinantColumns = new ArrayList<String>();
                        AbstractList<String> dependentColumns = new ArrayList<String>();

                        for (int currentScenarioIndex = 0; currentScenarioIndex < currentScenario.length; ++currentScenarioIndex) {
                            switch (currentScenario[currentScenarioIndex]) {
                                case 1:
                                    determinantColumns.add(columnNameswithoutConditionColumn.get(currentScenarioIndex));//hibat dob
                                    break;
                                case 2:
                                    dependentColumns.add(columnNameswithoutConditionColumn.get(currentScenarioIndex));
                                    break;

                            }
                        }
                        for (int column = 0; column < columnNameswithoutConditionColumn.size(); ++column) {
                            AbstractList<String> conditions = getConditions(tableName, columnNameswithoutConditionColumn.get(column));
                            for (int condition = 0; condition < conditions.size(); ++condition) {
                                if (isCfd(tableName, determinantColumns, dependentColumns, conditions.get(condition))) {
                                    listOfCFDs.add(new FDScenario(determinantColumns, dependentColumns, conditions.get(condition)));
                                }
                            }
                        }
                    }
                } while (currentScenario != null);

            }
            for (int k = 0; k < listOfCFDs.size(); ++k) {
                System.out.println("a cfd-k listaja: " + "determinant: " + listOfCFDs.get(k).determinantColumns + "dependent: " + listOfCFDs.get(k).dependentColumns + "kondicio " + listOfCFDs.get(k).condition);
            }


        }
        return listOfCFDs;
    }

    @Override
    public AbstractList<FDScenario> getAR(String tableName, AbstractList<String> columnNames) {
        AbstractList<FDScenario> listOfAr = new ArrayList<FDScenario>();
        int[] currentScenario = null;
        int columnsSize = columnNames.size();//columns number
        AbstractList<String>[] columnsConditions = new ArrayList[columnsSize];//mekkor nagysagu strineket foglaljak le?

        for (int i = 0; i < columnsSize; i++) {
            columnsConditions[i] = new ArrayList<String>();
        }

        for (int column = 0; column < columnsSize; ++column) {//annyi eleme van a tombnek ahany oszlop van
            columnsConditions[column].addAll(new ArrayList<String>(getConditions(tableName, columnNames.get(column))));
        }
        System.out.println("Kondiciok oszloponkent a parameterkent atadott tablabol:" + tableName);
        for (int i = 0; i < columnsConditions.length; ++i) {
            for (int j = 0; j < columnsConditions[i].size(); ++j) {
                System.out.println(columnsConditions[i].get(j));
            }
        }

        //felepitem az uj oszlopokat tartalmazo tombot kiveve a kondicio oszlopot
        for (int i = 0; i < columnsConditions.length; ++i) {
            //minden elem egy feltetel halmaz
            //for(int j=0 ;j<columnsConditions[i].size() ; ++j){
            //j az i. oszlop halamazank az indexe
            if (!columnsConditions[i].isEmpty()) {
                String myColumn = columnsConditions[i].get(0).substring(0, columnsConditions[i].get(0).indexOf("="));
                System.out.println("Kondicio oszlop: " + myColumn);

                AbstractList<String> columnNameswithoutConditionColumn = new ArrayList<String>();

                for (int column = 0; column < columnsSize; ++column) {

                    if (!columnNames.get(column).equalsIgnoreCase(myColumn)) {
                        columnNameswithoutConditionColumn.add(columnNames.get(column));
                    }
                }
//                for (int ii = 0; ii < columnNameswithoutConditionColumn.size(); ++ii) {
//                    System.out.println(" kondicio oszlop nelkuli oszlopnevek   " + columnNameswithoutConditionColumn.get(ii));
//                }

                do {


                    if (currentScenario == null) {
                        currentScenario = Combination.getNextScenario(columnNameswithoutConditionColumn.size(), 2);
                    } else {
                        currentScenario = Combination.getNextScenario(currentScenario, 2);
                    }

                    if (currentScenario != null) {

                        AbstractList<String> dependentColumns = new ArrayList<String>();

                        for (int currentScenarioIndex = 0; currentScenarioIndex < currentScenario.length; ++currentScenarioIndex) {
                            switch (currentScenario[currentScenarioIndex]) {
                                case 1:
                                    dependentColumns.add(columnNameswithoutConditionColumn.get(currentScenarioIndex));
                                    break;
                            }
                        }
                        for (int column = 0; column < columnNameswithoutConditionColumn.size(); ++column) {
                            AbstractList<String> conditions = getConditions(tableName, columnNameswithoutConditionColumn.get(column));
                            for (int condition = 0; condition < conditions.size(); ++condition) {
                                if (isAr(tableName, conditions.get(condition), dependentColumns)) {
                                    listOfAr.add(new FDScenario(dependentColumns, conditions.get(condition)));
                                }
                            }
                        }
                    }
                } while (currentScenario != null);

            }
//            for(int k=0; k<listOfAr.size();++k){
//            System.out.println("a ar-k listaja: "+"dependent: "+listOfAr.get(k).dependentColumns+"kondicio"+listOfAr.get(k).condition);
//            }
        }

        return listOfAr;
    }

    @Override
    public void createDependency(AbstractList<FDScenario> dependencies) {
        //String token = new BigInteger(8192, new SecureRandom()).toString(512);
        PreparedStatement preparedStatement = null;

        try {

            for (int k = 0; k < dependencies.size(); ++k) {
                String sql = "INSERT INTO Dependency (condi, insertToken) VALUES(?,?); ";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql);


                //tobbszor fog szerepelni egy kondicio mert mas osszefuggesben is van veve,
                //mas a meghatarozo es meghatarozott oszlop de uugyan az a kondicio
                String token = new BigInteger(1192, new SecureRandom()).toString(512);

                FDScenario currentDependency = dependencies.get(k);
                preparedStatement.setString(1, currentDependency.condition);
                preparedStatement.setString(2, token);
                insertCount = preparedStatement.executeUpdate();

                //TODO : get the last dependendency id matching the generated token
                //select max(id) from Dependency where insertToken = ? 
                //the ? will represent our token variable----->lehet kell fetch elni

                sql = "select max(id) as maxId from Dependency where insertToken = ? ";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                preparedStatement.setString(1, token);
                ResultSet idMax = preparedStatement.executeQuery();
                int myDependencyId = -1;
                while (idMax.next()) {
                    myDependencyId = idMax.getInt("maxId");
                }


                //int dependencyId = -1;

                sql = "INSERT INTO dependencycolumn (dependencyId, columnName , isDeterminantColumn, value  ) VALUES(?,?,?,?); ";

                preparedStatement = (PreparedStatement) connection.prepareStatement(sql);


                if ((currentDependency.determinantColumns != null) && (!currentDependency.determinantColumns.isEmpty())) {
                    for (int determinantColumnIndex = 0; determinantColumnIndex < currentDependency.determinantColumns.size(); determinantColumnIndex++) {
                        preparedStatement.setInt(1, myDependencyId);
                        preparedStatement.setString(2, currentDependency.determinantColumns.get(determinantColumnIndex));
                        preparedStatement.setInt(3, 1);
                        preparedStatement.setString(4, "");
                        preparedStatement.addBatch();
                    }
                }
                for (int dependentColumnIndex = 0; dependentColumnIndex < currentDependency.dependentColumns.size(); dependentColumnIndex++) {
                    preparedStatement.setInt(1, myDependencyId);
                    preparedStatement.setString(2, currentDependency.dependentColumns.get(dependentColumnIndex));
                    preparedStatement.setInt(3, 0);
                    preparedStatement.setString(4, currentDependency.values.get(dependentColumnIndex));
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();

                //TODO update the insert token of the currently inserted dependency to null

                sql = "update Dependency set insertToken = null where id = ?";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                preparedStatement.setInt(1, myDependencyId);
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println("Could not insert data to the database " + e.getStackTrace());
        }
    }
}
