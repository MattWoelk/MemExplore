/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MemoryEntry;

/**
 *
 * @author umdentot
 */
public class MemoryEntry  {
    /*
     * The name of the variable in memory
     */
    public String varName;
    /*
     * The starting address of the variable in memory
     */
    public long addressStart;
    /*
     * The ending address of the variable in memory
     */
    public long addressEnd;
    /*
     * The length of the variable
     */
    public long length;
    
    //Constructor
    public MemoryEntry(String n, long aS, long aE, long l)
    {
       varName = n;
       addressStart = aS;
       addressEnd = aE;
       length = l;
    }
}
