/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackupHelper;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DependencyPattern {
    
    private static int currentId = 0;
    private int id;
    private AbstractList<String> determinantColumnValues;
    private AbstractList<String> dependentColumnValues;
    //dependencyObject ??
    
    public DependencyPattern() {
        currentId ++;
        id = currentId;
    }
    
    
    public boolean isMatchingRow(){
        return true;
    }
    
    
    public ArrayList<String> getColumnNames(){
        return null;
    }
    
    public ArrayList<String> getColumnValues(){
        return null;
    }
    
     
}
