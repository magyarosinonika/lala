/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 *
 * @author noni
 */
public class FileHandler {
    
    public static AbstractList<String> readFromFile(String filename) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String currentLine;
        AbstractList<String> lines = new ArrayList<String>();
        while ((currentLine = bufferedReader.readLine()) != null ) {
            lines.add(currentLine);
        }
        bufferedReader.close();
        return lines;
    }
    
    public static void writeToFile(String filename, AbstractList<String> content) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        int contentSize = content.size();
        for (int contentIndex = 0; contentIndex < contentSize; contentIndex++ ){
            bufferedWriter.write(content.get(contentIndex));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
    
}