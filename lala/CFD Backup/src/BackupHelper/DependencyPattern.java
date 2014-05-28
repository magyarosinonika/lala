/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackupHelper;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DependencyPattern {
    
    private static int currentId = 0;
    private int id;
    private ArrayList<String> determinantColumnValues;
    private ArrayList<String> dependentColumnValues;
    //dependencyObject ??
    
    
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
