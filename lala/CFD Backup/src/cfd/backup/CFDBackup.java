/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd.backup;

import UI.forms.ConnectToDatabase;
import engine.DAL;
import engine.MySql;
import javax.swing.JOptionPane;

/**
 *
 * @author noni
 */
public class CFDBackup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DAL mySql = new MySql();//bealiitando a feluletrol
        
        try {
            /* AbstractList<String> determinantColumns = new ArrayList<String>();
            determinantColumns.add("eletkor");
            determinantColumns.add("nev");
            AbstractList<String> dependentColumns = new ArrayList<String>();
            dependentColumns.add("nem");
            dependentColumns.add("ihate");
            mySql.isFd("tabla", determinantColumns, dependentColumns);*/
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String hibasUzenet = null;
            int uzenetHossza = hibasUzenet.length();
        } catch (Exception ex) {
            //kulon kezelni a potencialis kiveteleket hibak szerint
            JOptionPane.showMessageDialog(null, "Upps", "ERROR", 0);
        }
        mySql.connect("jdbc:mysql://localhost:", "root", "", 3306, "mysql");//ini allomanybol a  beallitasiokat beolvassa a felulet
        ConnectToDatabase connDb = new ConnectToDatabase();
        
    }
}
