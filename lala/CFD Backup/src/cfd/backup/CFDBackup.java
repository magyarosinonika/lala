/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd.backup;

import UI.forms.ConnectToDatabase;
import engine.DAL;
import engine.MySql;
import java.util.ArrayList;
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
        ConnectToDatabase connDb = new ConnectToDatabase();
        ArrayList<String> determinant = new ArrayList<String>();
        ArrayList<String> dependent = new ArrayList<String>();
        determinant.add("added_by");
        determinant.add("added_date");
        dependent.add("id");
        dependent.add("value");
        //MySql m = new MySql();
        //m.isFd("bans", determinant, dependent);

        
        
        
        
    }
}
