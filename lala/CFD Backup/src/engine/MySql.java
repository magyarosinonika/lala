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
public class MySql implements DAL {

    @Override
    public boolean isFD(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns) {
        return true;
    }

    @Override
    public boolean isCFD(String table, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition) {
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
    public boolean connect(String URL, String userName, String password, int port, String dbName) {
        return true;
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
