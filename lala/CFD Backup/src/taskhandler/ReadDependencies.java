/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

import UI.forms.DependenciesForm;
import engine.init.DBMSManager;
import engine.init.Settings;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ReadDependencies extends BaseRunnable{
    
    protected int status;
    protected DefaultTableModel model;

    public ReadDependencies(int status, DefaultTableModel model) {
        this.status = status;
        this.model = model;
    }
    
    @Override
    public void run() {
        try {
            DependenciesForm.readDependencies(status, model);
        } catch (SQLException ex) {
            Logger.getLogger(ReadDependencies.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
