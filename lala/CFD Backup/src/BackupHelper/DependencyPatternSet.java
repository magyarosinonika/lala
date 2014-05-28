/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackupHelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class DependencyPatternSet {
    
    private static int currentId = 0;
    private static ArrayList<DependencyPatternSet> dependencyPatternSetSet;
    private int id;
    private HashMap<Integer,Integer> keySet;    //<key,value> ahol value 1 = isdeterminant , 0 ha isdependent
    
}
