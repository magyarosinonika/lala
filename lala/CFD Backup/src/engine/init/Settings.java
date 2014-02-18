/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.init;

import engine.DAL;
import engine.FileHandler;
import exceptionhandler.ExceptionHandlerCaller;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
    private static AbstractList<String> tabels = null;
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

    public static void initializeSettings() {
        
        String key = "";
        String value = "";
        String[] values = null;
        try {
            AbstractList<String> settingsContent = FileHandler.readFromFile("settings.ini");
            String line = "";
            for(int lineIndex = 0; lineIndex < settingsContent.size(); lineIndex++) {
                line = settingsContent.get(lineIndex);
                values = line.split(":");
                value = ( ( line != null ) && ( line.length() > 0 ) && ( line.charAt(line.length() - 1) != ':' ) ) ? values[1] : "" ;
                key = values[0];
                if (key.equalsIgnoreCase(keyUserName)) {
                    setUserName(value);
                } else if (key.equalsIgnoreCase(keyPassword)) {
                    setPassword(value);
                } else if (key.equalsIgnoreCase(keyHost)) {
                    setHost(value);
                } else if (key.equalsIgnoreCase(keyPort)) {
                    setPort(Integer.parseInt(value));
                } else if (key.equalsIgnoreCase(keyDbName)) {
                    setDbName(value);
                } else if (key.equalsIgnoreCase(keyRdbms)) {
                    setRdbms(value);
                } else if (key.equalsIgnoreCase(keyOccurrenceNumber)) {
                    setOccurrenceNumber(Integer.parseInt(value));
                } else if (key.equalsIgnoreCase(keyMaxThreadNumber)) {
                    setMaxThreadNumber(Integer.parseInt(value));
                } else if (key.equalsIgnoreCase(keyTables)) {
                    tabels = new ArrayList<String>();
                    String[] valuesTable = value.split(",");
                    List<String> list = Arrays.<String>asList(valuesTable);
                    ArrayList<String> arrayList = new ArrayList<String>(list);
                    setTabels(arrayList);
                }

            }
        } catch(FileNotFoundException fileNotFoundException) {
            //JOptionPane.showMessageDialog(null, "settings.ini was not found!" , "ERROR", 0);
            // fileNotFoundException2 = (FileNotFoundException)ExceptionHandlerCaller.createJOptionPane(FileNotFoundExceptionHandler.class, "settings.ini", null);
            ExceptionHandlerCaller.handleFileNotFoundException(fileNotFoundException, null,"settings.ini");
        
        
        } catch(IOException ioException) {
            JOptionPane.showMessageDialog(null, "There was an error while trying to read the content of settings.ini!" , "ERROR", 0);
        }

    }
    
    private static String tabelsAsString() {
        if ( tabels == null ){
            return "";
        }
        String returnValue = "";
        for ( int tableIndex = 0; tableIndex < tabels.size(); tableIndex++ ){
            returnValue += ( ( returnValue.length() > 0 ) ? ( "," ) : ( "" ) ) + tabels.get(tableIndex);
        }
        return returnValue;
    }
    
    
    public static void save() {
        try {
            AbstractList<String> settingsToSave = new ArrayList<String>();
            settingsToSave.add(Settings.keyUserName + ":" + Settings.userName);
            settingsToSave.add(Settings.keyPassword + ":" + Settings.password);
            settingsToSave.add(Settings.keyHost + ":" + Settings.host);
            settingsToSave.add(Settings.keyPort + ":" + Settings.port);
            settingsToSave.add(Settings.keyDbName + ":" + Settings.dbName);
            settingsToSave.add(Settings.keyRdbms + ":" + Settings.rdbms);
            settingsToSave.add(Settings.keyOccurrenceNumber + ":" + Settings.occurrenceNumber);
            settingsToSave.add(Settings.keyMaxThreadNumber + ":" + Settings.maxThreadNumber);
            settingsToSave.add(Settings.keyTables + ":" + tabelsAsString());
            FileHandler.writeToFile("settings.ini", settingsToSave);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error while trying to write the content of settings.ini!" , "ERROR", 0);
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
