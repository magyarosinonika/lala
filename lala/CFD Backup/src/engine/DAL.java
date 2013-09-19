/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.AbstractList;

/**
 *
 * @author noni
 */
public interface Dal {
    
    boolean isFd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns);
    boolean isCfd(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition);
    boolean isAr(String table, String condition, AbstractList<String> dependentColumns);
    AbstractList<String> getConditions(String table, String column);
    boolean connect(String url, String userName, String password, int port, String dbName);
    void insert();
    void update();
    void delete();
}
