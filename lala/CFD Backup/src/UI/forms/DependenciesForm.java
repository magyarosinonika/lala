/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import UI.TableColumnAdjuster;
import UI.TabelModel;
import com.mysql.jdbc.Statement;
import engine.FDScenario;
import engine.MySql;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
public class DependenciesForm extends JFrame {

    private DefaultTableModel modelAccept;
    private JTable tableAccept;
    private DefaultTableModel modelReject;
    private JTable tableReject;
    private DefaultTableModel model;
    private JTable table;

    public DependenciesForm(String name) {
        super(name);
        initComponents();
    }

    public void initComponents() {

        createTabs();
    }

    public void createTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Learned dependencies", createLearnedDependenciesPanel());
        tabbedPane.addTab("Accepted dependencies", createAcceptDependenciesPanel());
        tabbedPane.addTab("Rejected dependencies", createRejectDependenciesPanel());
        this.add(tabbedPane);
    }

    public JPanel createLearnedDependenciesPanel() {
        JPanel dependenciesPanel = new JPanel();
        dependenciesPanel.setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Id", "Condition", "Determinant", "Dependent"});
//        readCorrelations(0, model);
        try {
            new taskhandler.ReadDependencies(0, model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        table = new JTable(model) {

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
        dependenciesPanel.add(tablePanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane);
        scrollPane.setViewportView(table);

//        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        TableColumnAdjuster tca = new TableColumnAdjuster(table);
//        tca.adjustColumns();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3));
        JButton acceptButton = new JButton();
        acceptButton.setText("Accept");
        buttonsPanel.add(acceptButton);
        acceptButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refresh(0, model, table);
                refresh(1, modelAccept, tableAccept);
            }
        });
        JButton rejectButton = new JButton("Reject");
        rejectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refresh(0, model, table);
                refresh(2, modelReject, tableReject);
            }
        });
        buttonsPanel.add(rejectButton);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refresh(0, model, table);
            }
        });
        buttonsPanel.add(refreshButton);
        dependenciesPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return dependenciesPanel;
    }

    public JPanel createAcceptDependenciesPanel() {
        JPanel acceptDependenciesPanel = new JPanel();
        acceptDependenciesPanel.setLayout(new BorderLayout());
        modelAccept = new DefaultTableModel();
        modelAccept.setColumnIdentifiers(new String[]{"Id", "Condition", "Determinant", "Dependent"});
//        readCorrelations(1, modelAccept);
        try {
            new taskhandler.ReadDependencies(1, modelAccept);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        acceptDependenciesPanel.add(tablePanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane);
        scrollPane.setViewportView(tableAccept);
        JPanel buttonsPanel = new JPanel();
        JButton discardButton = new JButton();
        discardButton.setPreferredSize(new Dimension(200, 25));
        discardButton.setText("Discard");
        buttonsPanel.add(discardButton);
        acceptDependenciesPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return acceptDependenciesPanel;
    }

    public JPanel createRejectDependenciesPanel() {
        JPanel rejectDependenciesPanel = new JPanel();
        rejectDependenciesPanel.setLayout(new BorderLayout());
        modelReject = new DefaultTableModel();
        modelReject.setColumnIdentifiers(new String[]{"Id", "Condition", "Determinant", "Dependent"});
//        readCorrelations(2, modelReject);
        try {
            new taskhandler.ReadDependencies(2, modelReject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        rejectDependenciesPanel.add(tablePanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane);
        scrollPane.setViewportView(tableReject);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));

        JButton forgetButton = new JButton();
        forgetButton.setText("Forget");
        buttonsPanel.add(forgetButton);

        JButton cleanupButton = new JButton();
        cleanupButton.setText("Cleanup");
        buttonsPanel.add(cleanupButton);


        rejectDependenciesPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return rejectDependenciesPanel;
    }

    public static void readCorrelations(int status, DefaultTableModel model) {
        Statement stmt_fd = null;
        ResultSet fd = DBMSManager.DALFactory(Settings.getRdbms()).readDependencies(status);
        if (fd == null) {
            //TODO: show error message
            return;
        }
        try {
            while (fd.next()) {
                String determinant = "";
                String dependent = "";
                String[] temp = fd.getString(3).split(",");
                for (int index = 1; index < temp.length; ++index) {
                    String[] temp2 = temp[index].split("!");
                    if (temp2[2].equals("1")) {
                        determinant += temp2[0] + ",";
                    } else if (temp2[2].equals("0")) {
                        dependent += temp2[0] + ",";
                    }
                }
                if (determinant.length() > 0) {
                    determinant = determinant.substring(0, determinant.length() - 1);
                }
                dependent = dependent.substring(0, dependent.length() - 1);
                model.addRow(new String[]{fd.getString(1), fd.getString(2), determinant, dependent});

                determinant = "";
                dependent = "";

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //}
    }

    public static void refresh(int status, DefaultTableModel model, JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
//        readCorrelations(status, model);
        try {
            new taskhandler.ReadDependencies(status, model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
