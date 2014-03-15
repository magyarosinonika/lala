/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskhandler;

import UI.forms.DependenciesForm;
import engine.init.DBMSManager;
import engine.init.Settings;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ReadDependencies extends BaseThread{
    
    protected int status;
    protected DefaultTableModel model;

    public ReadDependencies(int status, DefaultTableModel model) {
        this.status = status;
        this.model = model;
    }
    
    
    
    @Override
    public void run() {
        DependenciesForm.readCorrelations(status, model);
    }
    
}
