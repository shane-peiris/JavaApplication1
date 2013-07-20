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
        //Intializes file path sent while object creationg to the variable file_path
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
             //Read next token in current line
             word = splitter.nextToken();
             
             //Checks whether the first character is either a letter or an underscore
             if (Character.isLetter(word.charAt(0)) || word.charAt(0) == '_')
             {     
                 //Checks whether the token is NOT an underscore and only with a length of 1
                 if(!((word.charAt(0) == '_') & (word.length()==1)))
                 {
                     //Increament token count found in current line
                      alph_word_count_for_line += 1;
                 }                         
             }          
             
        }       
        //return alphanumeric token count of current line
        return alph_word_count_for_line;
    }
    
    public void read_file_contents()
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
        
        //Display final total count of Alphanumeric Tokens
        System.out.println("\nTotal count of Alphanumeric Tokens : " + tot_alpha_word_count);
        System.out.println("\n***************************************************************\n");
        }
        catch(Exception e)
        {
                System.out.println("Error..!! Unable to read line..!!" 
                + e.getMessage()+"\n");                      
        }
        
    }
    
    public static void main(String args[])
    {
        System.out.println("\n******************Critical Text Analyzer***********************\n");
        String path="";
        do{
            System.out.print("Please enter file name in the current directory to anaylse : ");
            //Get file path directory from user
            path = System.console().readLine();

            if (path.equals(""))
            {
                System.out.println("\nNo input found\n");                
            }
            else
            {
                //Create new object of class along with passing the file path
                CriticalTextAnalyzer c = new CriticalTextAnalyzer(path);  
                c.read_file_contents();                
            }
        }while(path.equals(""));
          
    }
}
