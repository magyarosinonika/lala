/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

import engine.init.Settings;
import java.lang.String;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class DependencyLearner extends BaseRunnable {

    protected String tableName;
    protected static AbstractList<DependencyLearner> dependenciesObjects = new ArrayList<DependencyLearner>();
    

    public static void learnAllDependencies() throws InterruptedException {
        AbstractList<String> tableNames = Settings.getTabels();
        int maxIndex = tableNames.size() - 1;
        for (int i = 0; i < maxIndex ; ++i) {
            try {
                taskhandler.DependencyLearner.dependencyLearnerFactory(tableNames.get(i));
            } catch (InterruptedException ex) {
                //TODO handle exception
            }
        }
        if( maxIndex > -1 ) {
            //taskhandler.DependencyLearner.dependencyLearnerFactory(tableNames.get(maxIndex + 1) , true);
            taskhandler.DependencyLearner.dependencyLearnerFactory(tableNames.get(maxIndex) , true);
            if(maxIndex == tableNames.size() - 1){
                synchronize();
                dependenciesObjects.clear();
            }
            
            
        }
    }

    public static void dependencyLearnerFactory(String tableName) throws InterruptedException {
        dependencyLearnerFactory(tableName, false);
    }

    public static void dependencyLearnerFactory(String tableName, boolean synchronizeAnyway) throws InterruptedException {
        if (dependenciesObjects == null) {
            dependenciesObjects = new ArrayList<DependencyLearner>();
        } else if ( synchronizeAnyway || dependenciesObjects.size() >= Settings.getMaxThreadNumber()  ) {
            synchronize();
            dependenciesObjects.clear();
        }
        dependenciesObjects.add(new DependencyLearner(tableName));

    }

    @Override
    public void run() {
        engine.Operator.learnDependencies(tableName);
        //JOptionPane.showMessageDialog(null, );
    }

    public DependencyLearner(String tableName) {
        super('a');
        this.tableName = tableName;
        this.runBaseRunnable();

    }

    public static void synchronize() throws InterruptedException {
        for (int i = 0; i < dependenciesObjects.size(); ++i) {
            dependenciesObjects.get(i).join();
            JOptionPane.showMessageDialog(null, dependenciesObjects.get(i).tableName);
        }
        
    }
}
