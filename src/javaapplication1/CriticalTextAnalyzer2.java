/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mali
 */
public class CriticalTextAnalyzer2 {

    /**
     * @param args the command line arguments
     */
    public static String line="";
    public static int tot_alpha_word_count=0;
    
    public static void main(String[] args) {
        // TODO code application logic here
         String path="";
            CriticalTextAnalyzer2 cta=new CriticalTextAnalyzer2();
            FileReader fileToRead = null;
            try {
                System.out.print("Please enter file name in the current directory to anaylse : ");
                //Get file path directory from user
                path = System.console().readLine();
                fileToRead = new FileReader(path);
                //Instantiate the BufferedReader Class to read the file
                BufferedReader bufferReader = new BufferedReader(fileToRead);        
                // Read file line by line and print on the console
                while ((line = bufferReader.readLine()) != null)   
                    {
                         tot_alpha_word_count+=cta.getNumTokens();
                    }
                //Close the buffer reader
                bufferReader.close();
            } catch (Exception ex) {   
                System.out.println("Error..!! Unable to read line..!!" 
                + ex.getMessage()+"\n");          
            } 
            
            System.out.println("Total Alphanumeric Tokens :"+cta.getNumTokens());
            
       }
                
    public int getNumTokens()
    {
         int x=0;
        int tot=0;
        String delims="\t\n,;{}[]().-<>&^%$@!-+/*~=";
        StringTokenizer splitter = new StringTokenizer(line,delims,true);
        int numTokens=splitter.countTokens();
        
       
        int alphaNumWordCount=0;
       
                       
     	while (splitter.hasMoreTokens())
        {
            
            int tot_Charac=0;
             String word=splitter.nextToken();
           // System.out.println(" "+s);
            
            if((word.charAt(0)=='_') || (Character.isLetter(word.charAt(0))))
            {      
                //Checks whether the token is NOT an underscore and only with a length of 1
                 if(!((word.charAt(0) == '_') & (word.length()==1)))
                 {
                     //Increament token count found in current line
                      alphaNumWordCount += 1;
                 }   
            }
        }
        return alphaNumWordCount;
        
    }
}
    
    
     

