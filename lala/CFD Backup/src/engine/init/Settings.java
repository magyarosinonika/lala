/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.init;

/**
 *
 * @author noni
 */
public class Settings {

    private String rdbms;
    private String userName;
    private String password;
    private int port;
    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getRdbms() {
        return rdbms;
    }

    public String getUserName() {
        return userName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRdbms(String rdbms) {
        this.rdbms = rdbms;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
