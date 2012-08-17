/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filechooser.resources;

import MemoryEntry.MemoryEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import parser.CFile;
import var.Typedef;
import var.Variable;



class MyCustomFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory();
        }
        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Code composer project directory";
        }
    } 
/**
 *
 * @author umdentot
 */
public class FileChooser extends javax.swing.JFrame {
    boolean fileLoaded = false;
    
    ArrayList<MemoryEntry> entries = new ArrayList<MemoryEntry>();
    /**
     * Creates new form FileChooser
     */
    public FileChooser() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Open = new javax.swing.JMenuItem();

        fileChooser.setCurrentDirectory(new java.io.File("C:\\"));
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            fileChooser.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    fileChooserActionPerformed(evt);
                }
            });

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("UMBUG .map file browser");

            table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String [] {
                    "Name", "Start Address", "End Address", "Length"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            });
            jScrollPane2.setViewportView(table);

            jTextField1.setEnabled(false);
            jTextField1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField1ActionPerformed(evt);
                }
            });
            jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    jTextField1KeyTyped(evt);
                }
            });

            jLabel1.setText("Filter Text:");

            jMenu1.setText("File");

            Open.setText("Open project directory");
            Open.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    OpenActionPerformed(evt);
                }
            });
            jMenu1.add(Open);

            jMenuBar1.add(jMenu1);

            setJMenuBar(jMenuBar1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                        .addComponent(jTextField1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(21, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        ArrayList<Variable> globalVars = new ArrayList<Variable>();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /*
             * We want to just read the base project directory and let the program sort out the rest.
             */
            File baseDir = fileChooser.getSelectedFile();
            System.out.println(baseDir);
                 
            try {
              // file contains the base directory
             
             File mapFile = new File(baseDir.getPath()+ File.separator +  "source" + File.separator + "Project-Cheesecake.map");//change this to accomodate different filenames!
             System.out.println(mapFile);
             BufferedReader fileIn = new BufferedReader(new FileReader(mapFile));
             String line = fileIn.readLine();
             boolean parseOn=false;
             
             //read in the map file
             while(line != null)
             {
                 
                 if (line.length() < 4)
                 {
                     parseOn=false;
                 }
                 if (parseOn==true)
                 {
                     
                     String tokens[] = line.split("\\s+");
                     entries.add(new MemoryEntry(tokens[1],Long.parseLong(tokens[0],16),0,0));//parseInt(tokens[0],16) is base sixteen ie hex
                     if (entries.size() > 1)
                     {
                           entries.get(entries.size()-2).addressEnd = Long.parseLong(tokens[0],16);
                           entries.get(entries.size()-2).length = entries.get(entries.size()-2).addressEnd - entries.get(entries.size()-2).addressStart + 1;
                         
                     }
                    // System.out.println(tokens[1]);
                     System.out.flush();
                     
                     
                 }
                 
                 if (line.contains("GLOBAL SYMBOLS: SORTED BY Symbol Address"))
                 {
                     parseOn=true;
                     //burn off three lines
                     line = fileIn.readLine();
                     line = fileIn.readLine();
                     line = fileIn.readLine();
                 }
                 line = fileIn.readLine();
                 //entries.add(new MemoryEntry(line, WIDTH, WIDTH, WIDTH));
                 
             }
             System.out.println("done loop?");
             /*
              * The hard part - read in .h files to get actual sizes of variables.
              * This is opposed to the previous approach of just taking deltas in the memory map, which only works if the memory is 100% contiguous
              */
             
             ArrayList<File> hfiles = new ArrayList<File>();
             recurseDirForFiles(baseDir,"^.*\\.h$",hfiles);
             
              
              
             ArrayList<File> cfiles = new ArrayList<File>();
             recurseDirForFiles(baseDir, "^.*\\.c$", cfiles);
             System.out.println("Num of h files: " + hfiles.size());
             System.out.println("Num of c files: " + cfiles.size());
             
             
             fileLoaded = true;
             jTextField1.setEnabled(true);
             
             /*
              * Look through every C File and try to extract global variables.
              * 
              */
             System.out.println("****Begin variable hunt....");
             try{
                for(int i = 0; i < cfiles.size();i++)
                {
                    globalVars.addAll(CFile.parseFile(cfiles.get(i)));
                }
             } catch(IOException ioe)
             {
                 ioe.printStackTrace();
             }
             System.out.println("****variable hunt complete");
             for(int i = 0; i < globalVars.size(); i++)
             {
                 System.out.println(globalVars.get(i));
             }
             /*
              * First look through every file h file for a typedef.
              * Right now this is able to sepeate out typedefs.  Do we need to modify structures tho?
              * TBD.
              */
             System.out.println("MAtch some typedefs");
             //int numLeftCurls = 0;
             boolean matchTypeDef = false;
             
             ArrayList<Typedef> typedefs = new ArrayList<Typedef>();
             String[] curFileLines;
             String curFileText = "";
             for(int i = 0; i < hfiles.size(); i++)
             {
                 /*
                  * First read in file and convert it to an array of strings.
                  */
                 try
                 {
                     BufferedReader hfile = new BufferedReader(new FileReader(hfiles.get(i)));
                     curFileText = hfile.readLine();
                     while(line != null)
                     {
                         if (!line.matches("^\\s+$"))//dont include blank lines
                         {curFileText += "\n" + line;}
                         line = hfile.readLine();
                     }
                     
                     
                 } catch(Exception e) {e.printStackTrace();}
                 curFileLines = curFileText.split("\\n");
                 removeComments(curFileLines);
                 
                 //array curFileLines no longer contains comments.
                 
                 /*
                  * now, parse through h file (sans comments) and pick out typedefs.
                  */
                 boolean typedefOn = false;
                 String typedefText = "";
                 int numLeftCurls = 0;
                 for(int j = 0; j < curFileLines.length; j++)
                 {
                     line = curFileLines[j];
                     if(line.matches("^\\s*typedef.*$"))
                     {
                         typedefOn = true;
                     }
                     if(line.contains("{"))
                     {
                         numLeftCurls++;
                     }
                     if(line.contains("}"))
                     {
                         numLeftCurls--;
                     }
                     if(typedefOn)
                     {
                         if(line.matches("^.*\\w+.*$"))
                         {
                             typedefText += line + "\n";
                         }
                     }
                     if(typedefOn && numLeftCurls==0 && line.matches("^.*;\\s*$"))
                     {
                         typedefOn=false;
                         //typedefText += "\nEND***********";
                         typedefs.add(new Typedef(typedefText));
                         typedefText = "";
                         //typedefText = "BEGIN*********\n";
                     }
                     
                 }
             }
             
            } catch (IOException ex) {
              System.out.println("problem accessing file"+baseDir.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            /*
             * Now we need to fill up the table with MemoryElements.  But first, we should remove everything already in there. (in case someone reloads afile)
             */
            DefaultTableModel dm = (DefaultTableModel)table.getModel();
            dm.getDataVector().removeAllElements();
            String ZEROS = "00000000";
            for (int i = 0; i < entries.size(); i++) {
                String addressStart = Long.toHexString(entries.get(i).addressStart);
                addressStart = addressStart.length() <= 8 ? ZEROS.substring(addressStart.length()) + addressStart : addressStart;
                
                String addressEnd = Long.toHexString(entries.get(i).addressEnd);
                addressEnd = addressEnd.length() <= 8 ? ZEROS.substring(addressEnd.length()) + addressEnd : addressEnd;
                ((DefaultTableModel)table.getModel()).addRow(new Object[]{entries.get(i).varName,addressStart,addressEnd,entries.get(i).length});
            }
            table.setCellSelectionEnabled(true);
        } else {
            System.out.println("File access cancelled by user.");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_OpenActionPerformed
    /*
     * filemask is a regexp string!!!!
     */
    
    private void removeComments(String t[])
    {
        String lines[] = t;
        boolean multiLineComm = false;
        
        //remove all single-line comments
        for(int i = 0; i < lines.length; i++)
        {
            if(lines[i].contains("//"))
            {
                //System.out.println("BEFORE:\t"+lines[i]);
                lines[i] = lines[i].replaceAll("//+.*", "");
                //System.out.println("AFTER: \t" + lines[i].replaceAll("//+.*", ""));
            }
        }
        //remove all multi-line comments - including those /*contained on a single line*/
        for(int i = 0; i < lines.length; i++)
        {
            char der[] = lines[i].toCharArray();
            for(int j = 0; j < (lines[i]).length()-1; j++)
            {
                
                if (der[j] == '/' && der[j+1] == '*')
                {
                    multiLineComm = true;
                    der[j] = ' ';
                    der[j+1] = ' ';
                   
                }
                if (der[j] == '*' && der[j+1] == '/')
                {
                    multiLineComm = false;
                    der[j] = ' ';
                    der[j+1] = ' ';
                }
                if (multiLineComm)
                {
                    if (der[j] != '\0' && der[j] != '\n')
                    {
                        der[j] = ' ';
                    }
                }
                /*
                 * Worst fix in the world
                 */
                if (multiLineComm && i >= 1 && j==0 && lines[i-1].length() > 0)
                {
                    char lastLineArr[] = lines[i-1].toCharArray();
                    lastLineArr[lastLineArr.length-1] = ' ';
                    lines[i-1] = String.valueOf(lastLineArr);
                }
            }
            lines[i] = String.valueOf(der);
        }
    
    }
    
    
    private void recurseDirForFiles(File dir, String filemask, ArrayList result)
    {
        //recursive case
        
        File[] ls = dir.listFiles();
        for(int i = 0; i < ls.length; i++)
        {
            if (ls[i].isDirectory())
            {
               //System.out.println("Entering directory " + ls[i]);
               recurseDirForFiles(ls[i],filemask,result);
            }   
            else
            {
                 if ((ls[i].getName()).matches(filemask))
                 {
                     //System.out.println(ls[i] + " " + ls[i].getName());
                     result.add(ls[i]);
                 }
            }
        }
        
        
        //just to make the compiler shut up for now.
       
    }
    
    private int countOccurrences(String haystack, char needle)
    {
        int count = 0;
        for (int i=0; i < haystack.length(); i++)
        {
            if (haystack.charAt(i) == needle)
            {
                 count++;
            }
        }
        return count;
    }

    
    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileChooserActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        /*
         * Select the most relevant variable
         */
        String typed = jTextField1.getText() + evt.getKeyChar();
        
        int i = 0;
        //boolean keeplooking = false;
        if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER)
        {
            //System.out.println(i);
            i = (i == table.getSelectedRow() ? table.getSelectedRow() : table.getSelectedRow() + 1);
            typed = jTextField1.getText();
            while(i < table.getRowCount()-1 && ((String)table.getValueAt(i, 0)).toUpperCase().contains(typed.toUpperCase()) == false)
                {               
                    i++;
                }
             //System.out.println(i);
                if (((String)table.getValueAt(i,0)).toUpperCase().contains(typed.toUpperCase()) == true)
                {
                    table.changeSelection(i, 0, false, false);
                    //table.requestFocus();
                }
        }
        if (evt.getKeyChar() != java.awt.event.KeyEvent.VK_ENTER){
            while(i < table.getRowCount()-1 && ((String)table.getValueAt(i, 0)).toUpperCase().contains(typed.toUpperCase()) == false)
                {               
                    i++;
                }
                if (((String)table.getValueAt(i,0)).toUpperCase().contains(typed.toUpperCase()) == true)
                {
                    table.changeSelection(i, 0, false, false);
                    //table.requestFocus();
                }
                
        } 
        
    }//GEN-LAST:event_jTextField1KeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FileChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FileChooser().setVisible(true);
            }
        });
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Open;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}