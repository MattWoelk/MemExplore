/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import var.Variable;

/**
 *  this is a class of methods to parse a c file 
 * this application is specifically for parsing filescope variables at this time.
 * @author trdenton
 * 
 */
public class CFile {
    
    public static void parseFileOld(File f) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader(f));
        String line = in.readLine();
        int numLeftCurls = 0;
        
        boolean multiLineComment = false;
        int commentBegin = 0;
        int commentEnd = 0;
        int commentBeginSingleLine = 0;
        while(line != null)
        {
            
            //need to strip comments
            if (line.contains("/*"))
            {
                multiLineComment = true;
                commentBegin = line.indexOf("/*");
            }
            if (line.contains("*/"))
            {
                multiLineComment = false;
                commentEnd = line.indexOf("*/");
            }
            
            if (line.contains("//"))
            {
               
                commentBeginSingleLine = line.indexOf("//");
                
                line = line.substring(0,commentBeginSingleLine);
            }
            
            if (line.contains("{") && !line.contains("}"))
            {
                numLeftCurls++;
                
            }
            if (line.contains("}") && !line.contains("{"))
            {
                numLeftCurls--;
            }
            
            if (numLeftCurls == 0 && 
                multiLineComment == false &&
                !line.startsWith("#") &&                        //no defines
                !(line.contains("}") || line.contains("{")) &&  //no lines with only braces
                !(line.contains("void")) &&                     //no functions
                !(line.matches("^\\s+\\*/")) &&                 //no lines with only "*/"
                !(line.matches("^\\s*$")) &&                    //no lines with only whitespace
                !(line.matches(".*\\(.*\\).*"))                       //no function calls
                )//if we are filescope
            {
                if (!line.equals(""))
                    System.out.println(line);
            }
            line = in.readLine();
        }
    }
    
    public static ArrayList<Variable> parseFile(File f) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader(f));
        String line = in.readLine();
        ArrayList<Variable> found = new ArrayList<Variable>();
        while(line != null)
        {
            /*if (line.contains("Time_ms systemTimer;"))
            {
                line = "Time_ms *systemTimer;";
            }*/
            if (line.matches("^([\\*\\w]+)\\s{1}([\\*\\w]+)(\\[\\d*\\]|\\s*=\\s*.*)?\\s*;\\s*$"))
            {
                found.add(new Variable(line));
               // System.out.println(a);
            }
            /*if (line.matches(".*\\[\\d*\\].*"))
            {
                System.out.println("arr\t" + line);
            }*/
            line = in.readLine();
            
        }
        
        return found;
        
    }
    
}
