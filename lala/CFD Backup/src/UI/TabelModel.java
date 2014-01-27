/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import engine.FDScenario;
import java.util.AbstractList;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author noni
 */
public class TabelModel extends AbstractTableModel{
    
    private AbstractList<FDScenario> clicks = new ArrayList<FDScenario>();

    public TabelModel(AbstractList<FDScenario> list) {
        this.clicks=list;
    }

    @Override
    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
