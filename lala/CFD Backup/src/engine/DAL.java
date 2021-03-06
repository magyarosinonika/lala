/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 *
 * @author noni
 */
public interface DAL {//javaDocs utana olvasni
    
    boolean isFd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns);
    boolean isCfd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition);
    boolean isAr(String table, String condition, AbstractList<String> dependentColumns);
    AbstractList<String> getConditions(String table, String column);
    boolean connect(String url, String userName, String password, int port, String dbName);
    void insert();
    void update();
    void delete();
    void generate();
    Connection getConnection();
    public AbstractList<String>getTableNames();
    public AbstractList<FDScenario> getFDs(String tableName, AbstractList<String> columnNames) ;
    public AbstractList<FDScenario> getCFDs(String tableName, AbstractList<String> columnNames);
    public AbstractList<FDScenario> getAR(String tableName, AbstractList<String> columnNames);
    void prepareConnection();
    public void setConnection(Connection conn);
    public void createDependency(AbstractList<FDScenario> dependencies);
    public AbstractList<String> getColumnsOfTable(String tableName);
    //void combinations(final AbstractList columns, int[] helpNumbers, final int k);
    public ResultSet readDependencies(int status);
    public void accept(AbstractList<Integer> idToAccept) throws SQLException;
    //public void getListOfMuSql() throws SQLException;
    public void reject(AbstractList<Integer> idToReject) throws SQLException;
    public AbstractList<FDScenario> getListOfMuSql() throws SQLException;
    public void forget(AbstractList<Integer> idsToForget) throws SQLException;
    public void cleanUp() throws SQLException;
    public void normalBackup() throws SQLException;
    public String createTableScript(AbstractList<String> tables) throws SQLException;
    public String createforeignKeyScript() throws SQLException ;
    public String createInsertScript(AbstractList<String> tables) throws SQLException;
    public void printToFile(String createTableScript, String insertScript, String foreignKeyScript);
}
