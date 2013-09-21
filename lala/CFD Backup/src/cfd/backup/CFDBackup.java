/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd.backup;

import engine.MySql;

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
        MySql mySql = new MySql();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        mySql.connect("jdbc:mysql://localhost:", "nonika", "seepbaba", 3306, "proba2");
        
    }
}
