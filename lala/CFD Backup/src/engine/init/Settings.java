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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static AbstractList<String> tabels = new ArrayList<String>();
    private static int occurrenceNumber = 0;
    private static int maxThreadNumber = 0;
    private static String keyRdbms = "Rdbms";
    private static String keyHost = "Host";
    private static String keyUserName = "User name";
    private static String keyPassword = "Password";
    private static String keyPort = "Port";
    private static String keyDbName = "Database name";
    private static String keyOccurrenceNumber = "Occurrence number";
    private static String keyMaxThreadNumber = "Max Thread number";
    private static String keyTables = "Tabels";
    

    public static void initializeSettings() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("settings.ini"));
        String key = "";
        String value = "";
        try {
            String line = br.readLine();
            String[] values = line.split(":");
            char a_char = line.charAt(line.length() - 1);
            if (a_char == ':') {
                return;
            }
            key = values[0];
            value = values[1];
            int i = 1;
            while (line != null) {
                values = line.split(":");
                a_char = line.charAt(line.length() - 1);
                if (a_char == ':') {
                    return;
                }
                key = values[0]; // 004
                value = values[1]; // 034556
                if (i == 1 && key.equalsIgnoreCase(keyUserName)) {
                    setUserName(value);
                }
                if (i == 2 && key.equalsIgnoreCase(keyPassword)) {
                    setPassword(value);
                }
                if (i == 3 && key.equalsIgnoreCase(keyHost)) {
                    setHost(value);
                }
                if (i == 4 && key.equalsIgnoreCase(keyPort)) {
                    setPort(Integer.parseInt(value));
                }
                if (i == 5 && key.equalsIgnoreCase(keyDbName)) {
                    setDbName(value);
                }
                if (i == 6 && key.equalsIgnoreCase(keyRdbms)) {
                    setRdbms(value);
                }
                if (i == 7 && key.equalsIgnoreCase(keyOccurrenceNumber)) {
                    setOccurrenceNumber(Integer.parseInt(value));
                }
                if (i == 8 && key.equalsIgnoreCase(keyMaxThreadNumber)) {
                    setMaxThreadNumber(Integer.parseInt(value));
                }
                if(i == 9 && key.equalsIgnoreCase("Tabels")){
                    String[] valuesTable = value.split(",");
                    List<String> list = Arrays.<String>asList(valuesTable);
                    ArrayList<String> arrayList = new ArrayList<String>(list);
                    setTabels(arrayList);
                }
                line = br.readLine();
                ++i;

            }
        } finally {
            br.close();
        }
        
    }

    public static AbstractList<String> getTabels() {
        return tabels;
    }

    public static void setTabels(AbstractList<String> tabels) {
        Settings.tabels = tabels;
    }
    
    

    public static int getMaxThreadNumber() {
        return maxThreadNumber;
    }

    public static void setMaxThreadNumber(int maxThreadNumber) {
        Settings.maxThreadNumber = maxThreadNumber;
    }

    public static int getOccurrenceNumber() {
        return occurrenceNumber;
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

    public static void setOccurrenceNumber(int occurrenceNumber) {
        Settings.occurrenceNumber = occurrenceNumber;
    }
}
