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
import engine.init.DBMSManager;
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
        //System.out.println(query);
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
        //System.out.println(query);
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

                FDScenario currentFDscenario = new FDScenario(determinantColumns, dependentColumns);
                try {
                    if (isFd(tableName, determinantColumns, dependentColumns) && !ispPresentFD(currentFDscenario) ) {
                        System.out.println("FD");
                        listOfFDs.add(new FDScenario(determinantColumns, dependentColumns));

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } while (currentScenario != null);

        return listOfFDs;
    }
    
    public boolean ispPresentFD(FDScenario currentFDscenario) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        boolean exists = false;
        String sql = "select signature from "
                + "(select condi, concat(concat(',', group_concat(concat(concat(concat(concat(columnName, '!^!'),"
                + " isDeterminantColumn),'!^!'), value)  separator ',')), ',')"
                + " as signature from dependency join dependencycolumn "
                + "on dependency.id = dependencycolumn.dependencyId) SignatureTable where 1";

        for (int index = 0; index < currentFDscenario.determinantColumns.size(); index++) {
            sql += " and signature LIKE ? ";
        }
        for (int index = 0; index < currentFDscenario.dependentColumns.size(); index++) {
            sql += " and signature  LIKE ? ";
        }
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            for (int index = 0; index < currentFDscenario.determinantColumns.size(); index++) {
                preparedStatement.setString(index + 1, "%," + currentFDscenario.determinantColumns.get(index) + "!^!1!^!,%");
                //String determin = "%," + currentFDscenario.determinantColumns.get(index) + "!^!1!^!,%";
            }

            for (int index = 0; index < currentFDscenario.dependentColumns.size(); index++) {
                preparedStatement.setString(currentFDscenario.determinantColumns.size() + 1 + index, "%," + currentFDscenario.dependentColumns.get(index) + "!^!0!^!" + currentFDscenario.values.get(index) + ",%");
                //preparedStatement.setString(currentFDscenario.determinantColumns.size() + 2 + index, "%," + currentFDscenario.dependentColumns.get(index) + "!^!0!^!" + 0 + ",%");
            }


            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("van ilyen sor");
                exists = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;

    }

    @Override
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
                                FDScenario currentFDscenario = new FDScenario(determinantColumns, dependentColumns, conditions.get(condition));
                                try {
                                    if (isCfd(tableName, determinantColumns, dependentColumns, conditions.get(condition)) && !ispPresentCFD(currentFDscenario)) {
                                        System.out.println("CFD");
                                        listOfCFDs.add(new FDScenario(determinantColumns, dependentColumns, conditions.get(condition)));
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                } while (currentScenario != null);

            }
        }
        return listOfCFDs;
    }
    
    public boolean ispPresentCFD(FDScenario currentFDscenario) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean exists = false;
        String sql = "select signature from "
                + "(select condi, concat(concat(',', group_concat(concat(concat(concat(concat(columnName, '!^!'),"
                + " isDeterminantColumn),'!^!'), IFNULL(value, ','))  separator ',')), ',')" //kijavitottam if null ra mert ha  a value null akkor nullt terit vissza
                + " as signature from dependency join dependencycolumn "
                + "on dependency.id = dependencycolumn.dependencyId"
                + " where condi=?) SignatureTable where 1 ";

        for (int index = 0; index < currentFDscenario.determinantColumns.size(); index++) {
            sql += " and signature LIKE ? ";
        }
        for (int index = 0; index < currentFDscenario.dependentColumns.size(); index++) {
            sql += " and signature  LIKE ? ";
        }
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.setString(1, currentFDscenario.condition);
            int paramMetaData = preparedStatement.getParameterMetaData().getParameterCount();

            for (int index = 0; index < currentFDscenario.determinantColumns.size(); index++) {
                preparedStatement.setString(index + 2, "%," + currentFDscenario.determinantColumns.get(index) + "!^!1!^!,%");
            }

            for (int index = 0; index < currentFDscenario.dependentColumns.size(); index++) {

                preparedStatement.setString(currentFDscenario.determinantColumns.size() + 2 + index, "%," + currentFDscenario.dependentColumns.get(index) + "!^!0!^!" + currentFDscenario.values.get(index) + ",%");
            }


            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                //System.out.println("van ilyen sor");
                exists = true;
                // curser has moved to first result of the ResultSet 
                // thus here are matches with this query.
            }


        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;

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
//                                if (isAr(tableName, conditions.get(condition), dependentColumns)) {
//                                    System.out.println("AR");
//                                    listOfAr.add(new FDScenario(dependentColumns, conditions.get(condition)));
//                                }
                                
                                FDScenario currentFDscenario = new FDScenario(dependentColumns, conditions.get(condition));
                                try {
                                    if (isAr(tableName, conditions.get(condition), dependentColumns) && !ispPresentAR(currentFDscenario)) {
                                        listOfAr.add(new FDScenario(dependentColumns, conditions.get(condition)));
                                        System.out.println("AR");
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public boolean ispPresentAR(FDScenario currentFDscenario) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean exists = false;
        String sql = "select signature from "
                + "(select condi, concat(concat(',', group_concat(concat(concat(concat(concat(columnName, '!^!'),"
                + " isDeterminantColumn),'!^!'), value)  separator ',')), ',')"
                + " as signature from dependency join dependencycolumn "
                + "on dependency.id = dependencycolumn.dependencyId"
                + " where condi=? ) SignatureTable where 1";

        for (int index = 0; index < currentFDscenario.dependentColumns.size(); index++) {
            sql += " and signature  LIKE ? ";
        }
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            preparedStatement.setString(1, currentFDscenario.condition);

            for (int index = 0; index < currentFDscenario.dependentColumns.size(); index++) {
                preparedStatement.setString(index + 2, "%," + currentFDscenario.dependentColumns.get(index) + "!^!0!^!,%");
            }
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                //System.out.println("van ilyen ar sor");
                exists = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;

    }

    @Override
    public void createDependency(AbstractList<FDScenario> dependencies) {
        //String token = new BigInteger(8192, new SecureRandom()).toString(512);
        PreparedStatement preparedStatement = null;

        try {

            for (int k = 0; k < dependencies.size(); ++k) {
                String sql = "INSERT INTO Dependency (condi, insertToken) VALUES(?,?); ";
                preparedStatement = (PreparedStatement) getConnection().prepareStatement(sql);


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
                preparedStatement = (PreparedStatement) getConnection().prepareStatement(sql);
                preparedStatement.setString(1, token);
                ResultSet idMax = preparedStatement.executeQuery();
                int myDependencyId = -1;
                while (idMax.next()) {
                    myDependencyId = idMax.getInt("maxId");
                }

                sql = "INSERT INTO dependencycolumn (dependencyId, columnName , isDeterminantColumn, value  ) VALUES(?,?,?,?); ";

                preparedStatement = (PreparedStatement) getConnection().prepareStatement(sql);


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

//                sql = "update Dependency set insertToken = null where id = ?";
//                preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
//                preparedStatement.setInt(1, myDependencyId);
//                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println("Could not insert data to the database " + e.getStackTrace());
        }
    }
    
    
    public AbstractList<FDScenario> getListOfMU(AbstractList<FDScenario> list) {
        AbstractList<FDScenario> listOfMu = new ArrayList<FDScenario>();
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).condition == null && !list.get(i).determinantColumns.isEmpty()) { //if is an fd
                FDScenario currentFDscenario1 = new FDScenario(list.get(i).determinantColumns, list.get(i).dependentColumns);
                for (int j = i + 1; j < list.size(); ++j) {
                    FDScenario currentFDscenario2 = new FDScenario(list.get(j).determinantColumns, list.get(j).dependentColumns);
                    if ((currentFDscenario1.dependentColumns.toString().matches(".*" + currentFDscenario2.dependentColumns.toString() + ".*")) && (currentFDscenario2.determinantColumns.toString().matches(".*" + currentFDscenario1.determinantColumns.toString() + ".*"))) {
                        System.out.println("mu");
                        listOfMu.add(currentFDscenario1);
                    }
                }
            } else if (list.get(i).condition != null && !list.get(i).determinantColumns.isEmpty()) {//is a cfd
                FDScenario currentFDscenario1 = new FDScenario(list.get(i).determinantColumns, list.get(i).dependentColumns, list.get(i).condition);
                for (int k = i + 1; k < list.size(); ++k) {
                    FDScenario currentFDscenario2 = new FDScenario(list.get(k).determinantColumns, list.get(k).dependentColumns, list.get(k).condition);
                    if (currentFDscenario1.dependentColumns.toString().matches(".*" + currentFDscenario2.dependentColumns.toString() + ".*") && (currentFDscenario2.determinantColumns.toString().matches(".*" + currentFDscenario1.determinantColumns.toString() + ".*")) && (currentFDscenario2.condition.matches(".*" + currentFDscenario1.condition + ".*"))) {
                        listOfMu.add(currentFDscenario1);
                    }
                }
            }

        }
        return listOfMu;
    }

    @Override
    public void getListOfMuSql() throws SQLException {
        AbstractList<String> listOfMu = new ArrayList<String>();

        String sql = "select result.id "
                + "from dependency result "
                + "where  result.status = '0'  and result.id not in"
                + "(select d1.id "
                + "from dependency d1 ,  dependency d2 "
                + "where d1.status = '0' "
                + "and d2.status = '0' "
                + "and ((d2.condi is null) or (d2.condi = d1.condi) )"
                + "and   (select count(*) "
                + "from dependencycolumn d1col, dependencycolumn d2col "
                + "where 0 = d2col.isdeterminantcolumn "
                + "and d1col.columnName = d2col.columnName "
                + "and ((d2col.value is null) or (IFNULL(d1col.value, '') = IFNULL(d2col.value, ''))) "
                + "and d1col.isdeterminantcolumn = 0 "
                + "and d1col.dependencyId = d1.id "
                + "and d2col.dependencyId = d2.id )"
                + "="
                + "(select count(*) "
                + "from dependencycolumn "
                + "where dependencycolumn.dependencyId = d1.id "
                + "and  dependencycolumn.isdeterminantcolumn = 0)"
                + "and (select count(*) "
                + "from dependencycolumn d1col, dependencycolumn d2col "
                + "where 1 = d2col.isdeterminantcolumn "
                + "and d1col.columnName = d2col.columnName "
                + "and d1col.isdeterminantcolumn = 1 "
                + "and d1col.dependencyId = d1.id "
                + "and d2col.dependencyId = d2.id ) "
                + "="
                + "(select count(*)"
                + "from dependencycolumn "
                + "where dependencycolumn.dependencyId = d2.id "
                + "and dependencycolumn.isdeterminantcolumn = 1) "
                + "and d1.id <> d2.id)";

        Statement stmt_conditions = null;
        ResultSet conditions = null;
        try {
            stmt_conditions = getConnection().createStatement();
            conditions = null;
            conditions = stmt_conditions.executeQuery(sql);
            while (conditions.next()) {
                listOfMu.add(conditions.getString(1));
                //conditionList.add(column + "='" + conditions.getString(1) + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < listOfMu.size(); ++i) {
            System.out.println(listOfMu.get(i));
        }
    }

    public AbstractList<Integer> getListOfMuSqlFromSelectedTables() throws SQLException {
        AbstractList<Integer> listOfMu = new ArrayList<Integer>();

        String sql = "select result.id "
                + "from dependencySelected result "
                + "where  result.status = '0'  and result.id not in"
                + "(select d1.id "
                + "from dependencySelected d1 ,  dependencySelected d2 "
                + "where d1.status = '0' "
                + "and d2.status = '0' "
                + "and ((d2.condi is null) or (d2.condi = d1.condi) )"
                + "and   (select count(*) "
                + "from dependencycolumnselected d1col, dependencycolumnselected d2col "
                + "where 0 = d2col.isdeterminantcolumn "
                + "and d1col.columnName = d2col.columnName "
                + "and ((d2col.value is null) or (IFNULL(d1col.value, '') = IFNULL(d2col.value, ''))) "
                + "and d1col.isdeterminantcolumn = 0 "
                + "and d1col.dependencyId = d1.id "
                + "and d2col.dependencyId = d2.id )"
                + "="
                + "(select count(*) "
                + "from dependencycolumnselected "
                + "where dependencycolumnselected.dependencyId = d1.id "
                + "and  dependencycolumnselected.isdeterminantcolumn = 0)"
                + "and (select count(*) "
                + "from dependencycolumnselected d1col, dependencycolumnselected d2col "
                + "where 1 = d2col.isdeterminantcolumn "
                + "and d1col.columnName = d2col.columnName "
                + "and d1col.isdeterminantcolumn = 1 "
                + "and d1col.dependencyId = d1.id "
                + "and d2col.dependencyId = d2.id ) "
                + "="
                + "(select count(*)"
                + "from dependencycolumnselected "
                + "where dependencycolumnselected.dependencyId = d2.id "
                + "and dependencycolumnselected.isdeterminantcolumn = 1) "
                + "and d1.id <> d2.id)";

        Statement stmt_conditions = null;
        ResultSet conditions = null;
        try {
            stmt_conditions = getConnection().createStatement();
            conditions = null;
            conditions = stmt_conditions.executeQuery(sql);
            while (conditions.next()) {
                listOfMu.add(conditions.getInt(1));
                //conditionList.add(column + "='" + conditions.getString(1) + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < listOfMu.size(); ++i) {
            System.out.println(listOfMu.get(i));
        }
        return listOfMu;
    }

    public void reject(ArrayList<Integer> idToReject) throws SQLException {

        PreparedStatement preparedStatement = null;
        String sql = "update dependency set status = 2 where dependency.id = ?  ";

        try {
            preparedStatement = (PreparedStatement) getConnection().prepareStatement(sql);
            for (int i = 0; i < idToReject.size(); ++i) {
                preparedStatement.setInt(1, idToReject.get(i).intValue());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void accept(ArrayList<Integer> idToAccept) throws SQLException {
        ArrayList<Integer> realIdToAccept = new ArrayList<Integer>();
        ArrayList<Integer> listToReject = new ArrayList<Integer>();

        PreparedStatement preparedStatement = null;
       
        String sql = "insert into dependencySelected (id, condi ,status, insertToken)"
                + "SELECT distinct dependency.id,dependency.condi,dependency.status, dependency.insertToken  "
                + "FROM dependency "
                + "WHERE NOT EXISTS"
                +   "(select 1 from dependencySelected as d  where d.id = dependency.id ) ";
         
         
         for(int i=0;i<idToAccept.size();++i){
             if(i==0){
                 sql+="where id = ? ";
             }
             else
             sql+= "or id = ? ";
         }
         try{         
             preparedStatement =  (PreparedStatement) getConnection().prepareStatement(sql);
             for(int i=0;i<idToAccept.size();++i){
             preparedStatement.setInt(i+1, idToAccept.get(i).intValue());
            
             }
              preparedStatement.executeUpdate();
         }
         catch(SQLException e) {
            e.printStackTrace();
         }
//         //kell egy ilyan hogy meg nem letezik az az idju elem!!!!!!!!!!!!!!!!!!!!!!!!!!

          sql = "insert into dependencycolumnSelected (id, dependencyId ,columnName, isDeterminantColumn, value )" +
                 "SELECT dependencycolumn.id,dependencycolumn.dependencyId,dependencycolumn.columnName, dependencycolumn.isDeterminantColumn ,dependencycolumn.value  "+
                 "FROM dependencycolumn "
               + "WHERE NOT EXISTS"
               + "(select 1 from dependencycolumnSelected as d  where d.id = dependencycolumn.id ) ";

         for(int i=0;i<idToAccept.size();++i){
             if(i==0){
                 sql+="where dependencyId = ? ";
             }
             else
             sql+= "or dependencyId = ? ";
         }
         try{         
             preparedStatement =  (PreparedStatement) getConnection().prepareStatement(sql);
             for(int i=0;i<idToAccept.size();++i){
             preparedStatement.setInt(i+1, idToAccept.get(i).intValue());
            
             }
              preparedStatement.executeUpdate();
         }
         catch(SQLException e) {
            e.printStackTrace();
         }
//         
        AbstractList<Integer> muFromIdToAcceptList = new ArrayList<Integer>();
        muFromIdToAcceptList = getListOfMuSqlFromSelectedTables();//megkapom a leghasznosabbakat a kivalasztotakbol

        for (int i = 0; i < idToAccept.size(); ++i) {
            if (!muFromIdToAcceptList.contains(idToAccept.get(i))) {
                //ha nem tartalmazza akkor kell rejectelni
                listToReject.add(idToAccept.get(i));//meglesza lista amit kell rejectelni
            } else {
                realIdToAccept.add(idToAccept.get(i));
            }
        }

        reject(listToReject);
//        sql = "update dependency set status = 2 where dependency.id = ?  ";
//
//        try {
//            preparedStatement = (PreparedStatement) getConnection().prepareStatement(sql);
//            for (int i = 0; i < listToReject.size(); ++i) {
//                int temp = listToReject.get(i);
//                preparedStatement.setInt(1, listToReject.get(i));//??string--int
//                preparedStatement.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        sql = "update dependency set status = 1 where dependency.id = ?  ";
        try {
            preparedStatement = (PreparedStatement) getConnection().prepareStatement(sql);
            for (int i = 0; i < realIdToAccept.size(); ++i) {
                int temp = realIdToAccept.get(i);
                preparedStatement.setInt(1, realIdToAccept.get(i));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public ResultSet readDependencies(int status) {

        PreparedStatement preparedStatement = null;


        String query = "select id,condi,signature "
                + "from (select dependency.id,condi,concat(concat(',', group_concat(concat(concat(concat(concat(columnName, '!^!'),"
                + " isDeterminantColumn),'!^!')) separator ',')), ',') as signature "
                + "from dependency join dependencycolumn on dependency.id = dependencycolumn.dependencyId "
                + "where status=? group by id) SignatureTable";
        try {
            preparedStatement = (PreparedStatement) DBMSManager.DALFactory(Settings.getRdbms()).getConnection().prepareStatement(query);
            preparedStatement.setInt(1, status);
            return preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }
}
