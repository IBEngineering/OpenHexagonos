/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihaita
 */
public class TextReader {
    
    protected static Charset ENCODING = Charset.defaultCharset();
    
    public static List<String> readFile(String pathToFile) throws IOException {
        
        ArrayList<String> list = new ArrayList<>();
        
        //Treat file as if it was large
        Path path = Paths.get(pathToFile);
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
            
            String line;
            while ((line = reader.readLine()) != null) {
                //process each line in some way
                list.add(line);
            }
            
        }
        
        return list;
    }
    
    public static void writeFileLine(String pathToFile, String line) throws IOException {
        
        Path path = Paths.get(pathToFile);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)) {
            writer.write(line);
            writer.newLine();
        }
    }
}
