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

    public FDScenario(AbstractList<String> determinantColumns, AbstractList<String> dependentColumns) {
        //this.determinantColumns = determinantColumns; 
        //this.dependentColumns = dependentColumns; 
        this(determinantColumns, dependentColumns, null);

    }

    public FDScenario(AbstractList<String> dependentColumns, String condition) {
        //this.determinantColumns = determinantColumns; 
        //this.dependentColumns = dependentColumns; 
        this(null, dependentColumns, condition);

    }

    public FDScenario(AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition) {
        this.determinantColumns = determinantColumns;
        this.dependentColumns = dependentColumns;
        this.condition = condition;
        this.values = new java.util.ArrayList<String>();
        for (int valueIndex = 0; valueIndex < dependentColumns.size(); valueIndex++) {
            values.add("");
        }
    }
    //ar

    public FDScenario(AbstractList<String> determinantColumns, AbstractList<String> dependentColumns, String condition, AbstractList<String> values) {
        this.determinantColumns = determinantColumns;
        this.dependentColumns = dependentColumns;
        this.condition = condition;
        this.values = values;
    }
}
