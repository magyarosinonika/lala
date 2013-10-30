/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.init;

import engine.FDScenario;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author noni
 */
public class ColumnHelper {
    
    private AbstractList<String> columnsArray = new ArrayList<String>(); 
    private String tableName; 
    private AbstractList<String> determinantColumnsArray = new ArrayList<String>(); 
    private AbstractList<String> dependentColumnsArray = new ArrayList<String>(); 
    private String conditionColumnName; 
    private boolean stop;
    //atadni parameterkent ne legyen attributum
    private AbstractList<FDScenario> allCombinations = new ArrayList<FDScenario>();
    
    public AbstractList<FDScenario> getAllCombinations(){
        return allCombinations;
    }
        
    public ColumnHelper( String tableName, String conditionColumnName) {
        //cfd ar eseten 
        this.tableName = tableName;
        this.conditionColumnName = conditionColumnName; 
    } 
    public ColumnHelper(String tableName) {
        //fd eseten hasznalando 
        this(tableName, ""); 
    }

    public String getTableName() {
        return tableName;
    }

    public AbstractList<String> getDependentColumnsArray() {
        return dependentColumnsArray;
    }

    public void setDependentColumnsArray(AbstractList<String> dependentColumnsArray) {
        this.dependentColumnsArray = dependentColumnsArray;
    }

    public AbstractList<String> getDeterminantColumnsArray() {
        return determinantColumnsArray;
    }

    public void setDeterminantColumnsArray(AbstractList<String> determinantColumnsArray) {
        this.determinantColumnsArray = determinantColumnsArray;
    }

    public AbstractList<String> getColumnsArray() {
        return columnsArray;
    }

    public void setColumnsArray(AbstractList<String> columnsArray) {
        this.columnsArray = columnsArray;
    }
    /**
     * Find the dependent columns combinations
     * @param columns columns of the current table
     * @param k the total number of the elements in the subset
     * @param determinantColumns determinant columns
     */
     public void combinations(final AbstractList columns, final int k, final int[] determinantColumns) {
        int[] result = new int[k];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        boolean done = false;
        boolean ok = true;
        while (!done) {
            for (int i = 0; i < result.length; ++i) {
                for (int j = 0; j < determinantColumns.length; ++j) {
                    if (result[i] == determinantColumns[j]) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                dependentColumnsArray.clear();
                //System.out.println("Meghatarozott:" + Arrays.toString(result));
                for (int j = 0; j < result.length; ++j) {
                    //System.out.print(columns.get(result[j] - 1) + " ");
                    dependentColumnsArray.add(columns.get(result[j] - 1).toString());
                }
                //FDScenario fd = new FDScenario(determinantColumnsArray, dependentColumnsArray);
                allCombinations.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
                System.out.println("kombinacio :");
                for(int i=0;i<determinantColumnsArray.size();++i){
                    System.out.println("meghatarozo:"+determinantColumnsArray.get(i));
                }
                for(int i=0;i<dependentColumnsArray.size();++i){
                    System.out.println("meghatarozott:"+dependentColumnsArray.get(i));
                }
//                if (isFd(table_name, determinant_columns_array, dependent_columns_array)) {
//                    System.out.println("Is FD");
//                } else {
//                    System.out.println("is not Fd");
                //}
                System.out.println();

            }
            result = getNext(result, columns.size(), k);
            ok = true;
            done = stop;


        }
    }
     
     /**
     * Finds the next combination of helpNumbers
     * @param helpNumbers the set to combine
     * @param n the total number of elements
     * @param k the total number of the elements in the subset
     * @return the next combination
     */
    public int[] getNext(int[] helpNumbers, int n, int k) {
        stop = false;
        int lastIndex = k - 1;
        helpNumbers[lastIndex]++;
        if (helpNumbers[lastIndex] > ((n - (k - lastIndex)) + 1)) {
            while (helpNumbers[lastIndex] > ((n - (k - lastIndex)))) {
                lastIndex--;
                if (lastIndex < 0) {
                    break;
                }
            }
            if (lastIndex < 0) {
                stop = true;
                return null;
            }
            helpNumbers[lastIndex]++;
            for (int i = lastIndex + 1; i < helpNumbers.length; i++) {
                helpNumbers[i] = helpNumbers[i - 1] + 1;
            }
        }
        stop = false;
        return helpNumbers;
    }
    /**
     * Finds the determinant columns combination
     * @param columns columns of the current table
     * @param helpNumbers the set to combine
     * @param k the total number of the elements in the subset
     */
    public void combinations(final AbstractList columns, int[] helpNumbers, final int k,String table_name) {
        // if (tableName.equals("achievements")) {
        int[] result = new int[k];
        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }
        boolean done = false;
        while (!done) {
            determinantColumnsArray.clear();
            //System.out.println("Meghatarozo:" + Arrays.toString(result));
            for (int j = 0; j < result.length; j++) {
                System.out.print(columns.get(result[j] - 1) + " ");
                determinantColumnsArray.add(columns.get(result[j] - 1).toString());
            }
            System.out.println();

            for (int i = 1; i < columnsArray.size(); i++) {
                combinations(columnsArray, i, result);
            }

            result = getNext(result, columns.size(), k);
            done = stop;
        }
         // }else System.out.println("not correct table");
    }
    
    
    
}
