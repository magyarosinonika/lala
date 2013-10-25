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
     //betesszuk a table nevet es a combinations nel ellenorizzuk hogy ugyan az a tabla neve csak akkor ellenorizzuk az isfd t stb
     public FDScenario(AbstractList<String> determinantColumns, AbstractList<String> dependentColumns) { 
         this.determinantColumns = determinantColumns; 
         this.dependentColumns = dependentColumns; 
     }
    
}
