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
public class FDScenario {

    public AbstractList<String> determinantColumns;
    public AbstractList<String> dependentColumns;
    public AbstractList<String> values;
    public String condition;
    public int id;
    public String tableName;

    public FDScenario(AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String tableName) {
        //this.determinantColumns = determinantColumns; 
        //this.dependentColumns = dependentColumns; 
        this(determinantColumns, dependentColumns, null,tableName);

    }

    public FDScenario(AbstractList<String> dependentColumns, String condition,String tableName) {
        //this.determinantColumns = determinantColumns; 
        //this.dependentColumns = dependentColumns; 
        this(null, dependentColumns, condition,tableName);

    }

    public FDScenario(AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition,String tableName) {
        this.determinantColumns = determinantColumns;
        this.dependentColumns = dependentColumns;
        this.condition = condition;
        this.tableName = tableName;
        this.values = new java.util.ArrayList<String>();
        for (int valueIndex = 0; valueIndex < dependentColumns.size(); valueIndex++) {
            values.add("");
        }
    }
    

    public FDScenario(AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition, AbstractList<String> values) {
        this.determinantColumns = determinantColumns;
        this.dependentColumns = dependentColumns;
        this.condition = condition;
        this.values = values;
    }
    
    public FDScenario(int id,AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition) {
        this.id = id;
        this.determinantColumns = determinantColumns;
        this.dependentColumns = dependentColumns;
        this.condition = condition;
        
    }
    
    public FDScenario(String tableName, int id, AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition) {
        this.id = id;
        this.determinantColumns = determinantColumns;
        this.dependentColumns = dependentColumns;
        this.condition = condition;
        this.tableName = tableName;
        
    }

    
}
