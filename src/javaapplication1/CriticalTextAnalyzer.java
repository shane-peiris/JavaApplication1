package javaapplication1;

/**
 *
 * @author Shane
 */
import java.io.*;
import java.util.StringTokenizer;


public class CriticalTextAnalyzer {

    //Variable to hold the file path
    public static String file_path="";
    //Variable to hold the one line data
    public static String line="";
    //Variable to hold the total alphanumeric token count
    public static int tot_alpha_word_count=0;
    
    CriticalTextAnalyzer(String u_file_path) 
    {
        file_path=u_file_path;        
    }    
    public int getNumTokens()
    {
        String delims = "\t\n,;{}[]().-<>&^%$@!-+/*~= ";
        StringTokenizer splitter = new StringTokenizer(line, delims, true);
        int numTokens = splitter.countTokens();        
        
        String word="";
        //Variable to hold the total alphanumeric token count in the line read
        int alph_word_count_for_line = 0; 
        
        for(int i=0;i<numTokens;i++)
        {    
             word = splitter.nextToken();
             
             if (Character.isLetter(word.charAt(0)) || word.charAt(0) == '_')
             {     
                 if(!((word.charAt(0) == '_') & (word.length()==1)))
                 {
                      alph_word_count_for_line += 1;
                 }                         
             }          
             
        }       
        return alph_word_count_for_line;
    }
    
    public int read_file_contents()
    {
         try{

        FileReader fileToRead = new FileReader(file_path);
        //Instantiate the BufferedReader Class to read the file
        BufferedReader bufferReader = new BufferedReader(fileToRead);        
        // Read file line by line and print on the console
        
        
       
        while ((line = bufferReader.readLine()) != null)   
        {
                 tot_alpha_word_count+= getNumTokens();
        }
        
        //Close the buffer reader
        bufferReader.close();
        }
        catch(Exception e)
        {
                System.out.println("Error..!! Unable to read line..!!" 
                + e.getMessage());                      
        }
        return tot_alpha_word_count;
    }
    
    public static void main(String args[])
    {
        System.out.print("Please enter file name in the current directory to anaylse : ");
        
        CriticalTextAnalyzer c = new CriticalTextAnalyzer(args[0]);
        System.out.println(c.read_file_contents());
           
    }
}
