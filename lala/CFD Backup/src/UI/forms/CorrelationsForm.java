/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import UI.TableColumnAdjuster;
import UI.TabelModel;
import engine.FDScenario;
import engine.init.DBMSManager;
import engine.init.Settings;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author noni
 */
public class CorrelationsForm extends JFrame {

    private DefaultTableModel modelAccept;
    private JTable tableAccept;
    private DefaultTableModel modelReject;
    private JTable tableReject;

    public CorrelationsForm(String name) {
        super(name);
        initComponents();
    }

    public void initComponents() {
        createTabs();
    }

    public void createTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Learned correlations", createLearnedCorrelationsPanel());
        tabbedPane.addTab("Accepted correlations", createAcceptCorrelationsPanel());
        tabbedPane.addTab("Rejected correlations", createRejectCorrelationsPanel());
        this.add(tabbedPane);
    }

    public JPanel createLearnedCorrelationsPanel() {
        JPanel correlationsPanel = new JPanel();
        correlationsPanel.setLayout(new BorderLayout());
        AbstractList<String> determinantColumnsArray = new ArrayList<String>();
        AbstractList<String> dependentColumnsArray = new ArrayList<String>();
        final AbstractList<FDScenario> list = new ArrayList<FDScenario>();
        determinantColumnsArray.add("aaaaa");
        determinantColumnsArray.add("bbbbbbbbbb");
        determinantColumnsArray.add("ccccccc");
        dependentColumnsArray.add("dddddddddddddddd");
        dependentColumnsArray.add("eeeeeeeeeeee");
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        list.add(new FDScenario(determinantColumnsArray, dependentColumnsArray));
        final DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Id", "Condition", "Determinant", "Dependent"});
        for (int i = 0; i < list.size(); ++i) {
            model.addRow(new String[]{Integer.toString(i), "iffff", list.get(i).determinantColumns.toString(), list.get(i).dependentColumns.toString()});
        }
        final JTable table = new JTable(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JTableHeader header = table.getTableHeader();
        tablePanel.add(header, BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);
        correlationsPanel.add(tablePanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane);
        scrollPane.setViewportView(table);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3));
        JButton acceptButton = new JButton();
        acceptButton.setText("Accept");
        buttonsPanel.add(acceptButton);
        acceptButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    ArrayList<Object> row = new ArrayList<Object>();
                    for (int j = 0; j < table.getColumnCount(); ++j) {
                        row.add(model.getValueAt(rows[i] - i, j));
                    }
                    String[] temp = new String[table.getColumnCount()];
                    for (int k = 0; k < model.getColumnCount(); ++k) {
                        temp[k] = (String) row.get(k);
                    }
                    modelAccept.addRow(temp);
                    model.removeRow(rows[i] - i);
                }
                tableAccept.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                TableColumnAdjuster tca = new TableColumnAdjuster(tableAccept);
                tca.adjustColumns();
            }
        });
        JButton rejectButton = new JButton("Reject");
        rejectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    ArrayList<Object> row = new ArrayList<Object>();
                    for (int j = 0; j < table.getColumnCount(); ++j) {
                        row.add(model.getValueAt(rows[i] - i, j));
                    }
                    String[] temp = new String[table.getColumnCount()];
                    for (int k = 0; k < model.getColumnCount(); ++k) {
                        temp[k] = (String) row.get(k);
                    }
                    modelReject.addRow(temp);
                    model.removeRow(rows[i] - i);
                }
                tableReject.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                TableColumnAdjuster tca = new TableColumnAdjuster(tableReject);
                tca.adjustColumns();
            }
        });
        buttonsPanel.add(rejectButton);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < list.size(); ++i) {
                    model.addRow(new String[]{Integer.toString(i), "iffff", list.get(i).determinantColumns.toString(), list.get(i).dependentColumns.toString()});
                }
            }
        });
        buttonsPanel.add(refreshButton);
        correlationsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return correlationsPanel;
    }

    public JPanel createAcceptCorrelationsPanel() {
        JPanel acceptCorrelationsPanel = new JPanel();
        acceptCorrelationsPanel.setLayout(new BorderLayout());
        modelAccept = new DefaultTableModel();
        modelAccept.setColumnIdentifiers(new String[]{"Id", "Condition", "Determinant", "Dependent"});
        tableAccept = new JTable(modelAccept) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableAccept.setModel(modelAccept);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JTableHeader header = tableAccept.getTableHeader();
        tablePanel.add(header, BorderLayout.NORTH);
        tablePanel.add(tableAccept, BorderLayout.CENTER);
        acceptCorrelationsPanel.add(tablePanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane);
        scrollPane.setViewportView(tableAccept);
        JPanel buttonsPanel = new JPanel();
        JButton deleteButton = new JButton();
        deleteButton.setPreferredSize(new Dimension(200, 25));
        deleteButton.setText("Delete");
        buttonsPanel.add(deleteButton);
        acceptCorrelationsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return acceptCorrelationsPanel;
    }

    public JPanel createRejectCorrelationsPanel() {
        JPanel rejectCorrelationsPanel = new JPanel();
        rejectCorrelationsPanel.setLayout(new BorderLayout());
        modelReject = new DefaultTableModel();
        modelReject.setColumnIdentifiers(new String[]{"Id", "Condition", "Determinant", "Dependent"});
        tableReject = new JTable(modelReject) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableReject.setModel(modelReject);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JTableHeader header = tableReject.getTableHeader();
        tablePanel.add(header, BorderLayout.NORTH);
        tablePanel.add(tableReject, BorderLayout.CENTER);
        rejectCorrelationsPanel.add(tablePanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane);
        scrollPane.setViewportView(tableReject);
        JPanel buttonsPanel = new JPanel();
        JButton deleteButton = new JButton();
        deleteButton.setText("Delete");
        deleteButton.setPreferredSize(new Dimension(200, 25));
        buttonsPanel.add(deleteButton);
        rejectCorrelationsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return rejectCorrelationsPanel;
    }
}
