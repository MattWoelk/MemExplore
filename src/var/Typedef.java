/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package var;

import java.util.ArrayList;

/**
 *
 * @author trdenton
 */
public class Typedef {
    String name;//this is the name of the typedef
    String text;//this is the code that defines the typedef
    ArrayList<Variable> vars;
    /*
     * This constructor parses the .h file code
     */
    public Typedef(String t)
    {
        text = t;
        parse2(text);
        /*
         * 
         * Need to strip all comments from file...
         */
        
        
    }
    private void parse2(String t){
        String lines[] = t.split("\n");
        /*System.out.println("*******BEFORE*******");
        for(int i = 0; i < lines.length; i++)
        {
            System.out.println(lines[i]);
        }
        //removeComments(lines);
        System.out.println("*******AFTER********");*/
        for(int i= 0; i < lines.length; i++)
        {
            System.out.println(lines[i]);
        }
    }
    
    
    
    private void parse(String t)
    {
        String lines[] = t.split("\n");
        boolean commentOn = false; //for multiline comments
        int commentStart = 0;
        int commentEnd = 0;
        for(int i = 0; i < lines.length; i++)
        {
            if(lines[i].contains("//"))
            {
                /*
                 * get rid of single line comment
                 */
                int singleCommentPos = lines[i].indexOf("//");
                lines[i] = lines[i].substring(singleCommentPos,lines[i].length());
            } 
            if (lines[i].contains("/*"))
            {
                commentOn = true;
                commentStart = lines[i].indexOf("/*");
                if (lines[i].contains("*/"))
                {
                    commentEnd = lines[i].indexOf("*/");
                    lines[i] = lines[i].substring(0, commentStart) + lines[i].substring(commentEnd+3,lines[i].length());
                } else {
                    lines[i] = lines[i].substring(0, commentStart);
                }
            }else           
            if(lines[i].contains("*/"))
            {
                commentOn = false;
                commentEnd = lines[i].indexOf("*/");
                lines[i] = lines[i].substring(commentEnd+3,lines[1].length());
            } else 
            if (commentOn == true)
            {
                lines[i] = "";
            }
            
            System.out.println(lines[i]);
        }
        
        if (lines[0].contains("struct") || lines[0].contains("Struct") || lines[0].contains("STRUCT"))
        {
            /*
             * need to parse what follows in the parentheses;
             */
            
        } 
        else {
            /*
             * this is just a simple redefinition.
             */
            String tokens[] = lines[0].split("\\s+");
            String alias = tokens[1];
            String type = tokens[2];
            if (type.contains("*"))
            {
                /*
                 * this is a pointer type
                 * 
                 */
                
            } else
            {
                /*
                 * This is a 'regular' type
                 */
            }
        }
    }
    
    /*
     * Need a function to compare equality?
     */
}
