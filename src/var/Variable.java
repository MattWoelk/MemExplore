/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package var;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author trdenton
 */
public class Variable {
    String name;
    String type;
    int mult;
    int size;
    static int VAR_UI8 = 1;
    static int VAR_UI16 = 2;
    static int VAR_UI32 = 4;
    static int VAR_CHAR = 1;
    static int VAR_INT = 4;
    static int VAR_LONG = 8;//this is a guess
    
    public Variable(String in)
    {
        /*
         * valid variable defs
         * (type) (name);
         * (type) (name) = (init);
         * (type) (name)[(mult)];
         * 
         */
        Pattern p = Pattern.compile("^([\\*\\w]+)\\s*([\\*\\w]+)(;|\\s*=\\s*\\w+;|\\[\\d*\\];)");
        Matcher m = p.matcher(in);
        if(m.matches())
        {
            type = m.group(1);
            name = m.group(2);
            String end = m.group(3);
            Pattern multPat = Pattern.compile(".*\\[(\\d*)\\].*");
            Matcher multMat = multPat.matcher(in);
            if (multMat.matches())
            {
                mult = Integer.parseInt(multMat.group(1));
            } else
            {
                mult = 1;
            }
        }
        /*if (in.matches("^(\\w+)\\s*(\\w+)(;|\\s*=\\s*\\w+;|\\[\\d*\\];)"))
        {
            
        }
        String tokens[] = in.split("=");
        //take care of any initialization
        String tokensprime[] = tokens[0].split("\\s+");
        //the last two entries will always be the type and name (modifiers all go beforehand)
        String n = tokensprime[tokens.length-1];
        String t = tokens[tokens.length-2];
        name = n;
        type = t;
        */
    }
    
    public String toString(){
        return "Type: " + type + " Name: " + name + " Mult: " + mult;
    }
    
}
