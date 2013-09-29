/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.init;

import engine.DAL;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author noni
 */
public final class Settings {

    private static String rdbms = null;
    private static String host = null;
    private static String userName = null;
    private static String password = null;
    private static int port = 0;
    private static String dbName = null;
    

    public static void initializeSettings() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("settings.ini"));
        try {
            String line = br.readLine();
            int i=1;
            while (line != null) {
                //System.out.println(line + "size:" + line.length());
                if(i==1){
                    setUserName(line);
                }
                if (i==2) {
                    setPassword(line);
                }
                if (i==3) {
                    setHost(line);
                }
                if (i==4) {
                    setPort(Integer.parseInt(line));
                }
                if (i==5) {
                    setDbName(line);
                }
                if (i==6) {
                    setRdbms(line);
                }
                line = br.readLine();
                ++i;
                
            }
        } finally {
            br.close();
        }
    }

    public static String getDbName() {
        return dbName;
    }

    public static String getHost() {
        return host;
    }
    

    public static String getPassword() {
        return password;
    }

    public static int getPort() {
        return port;
    }

    public static String getRdbms() {
        return rdbms;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setDbName(String dbName) {
        Settings.dbName = dbName;
    }

    public static void setPassword(String password) {
        Settings.password = password;
    }

    public static void setPort(int port) {
        Settings.port = port;
    }

    public static void setRdbms(String rdbms) {
        Settings.rdbms = rdbms;
    }

    public static void setUserName(String userName) {
        Settings.userName = userName;
    }

    public static void setHost(String host) {
        Settings.host = host;
    }
    
}
