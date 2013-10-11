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
    
     public AbstractList<Integer> determinantColumns; 
     public AbstractList<Integer> dependentColumns;
     
     public FDScenario(AbstractList<Integer> determinantColumns, AbstractList<Integer> dependentColumns) { 
         this.determinantColumns = determinantColumns; 
         this.dependentColumns = dependentColumns; 
     }
    
}
