/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Admin
 */
public class SimpleTableDemo implements TableModelListener{

    JTable table;
    
    
    public SimpleTableDemo() {
        table.getModel().addTableModelListener(this);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
    }
    
}
