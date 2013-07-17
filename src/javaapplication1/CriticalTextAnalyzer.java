package javaapplication1;

/**
 *
 * @author Shane
 */
import java.io.*;
import java.util.StringTokenizer;


public class CriticalTextAnalyzer {

    public static String file_path="";
    public static String line="";
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
        System.out.println(String.valueOf(numTokens));
        
        String word_1="";
        String word_2="";
        int flag=0;
        word_1 = splitter.nextToken();
        word_2 = splitter.nextToken();
        int alph_word_count_for_line = 0;
        for(int i=0;i<numTokens;i++)
        {                    
             flag=0;             
             if (Character.isLetter(word_1.charAt(0)) || word_1.charAt(0) == '_')
             {     
                 if(!((word_1.charAt(0) == '_') & (word_1.length()==1)))
                 {
                    for (int x=0;x<word_1.length();x++)
                    {
                        if (!String.valueOf(word_1.charAt(x)).matches("[0-9A-Za-z_]"))
                        {
                            flag=1;
                        }                 
                    }

                    for (int x=0;x<word_2.length();x++)
                    {
                        int check=0;
                        while((splitter.hasMoreTokens()))
                        {
                           if (!String.valueOf(word_2.charAt(x)).matches("[0-9A-Za-z_ ]"))
                           {
                               flag=1;   
                               word_2=splitter.nextToken();
                               
                               check=1;
                               i++;                               
                           }  
                           else
                           {                                     
                               break;                               
                           }                        
                        } 
                        break;       
                    }  
                    if (flag==0)
                    {
                        System.out.println(word_1);
                        alph_word_count_for_line +=1;
                    }
                    else
                    {
                       
                    }   
                 }
                 else
                 {
                     flag=1;
                 }
                  
                              
             }          
             
             word_1 = word_2;
             
             if (splitter.hasMoreTokens())
             {
                word_2 = splitter.nextToken();
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
        
        //Variable to hold the one line data
       
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
        CriticalTextAnalyzer c = new CriticalTextAnalyzer("Shane.txt");
        System.out.println(c.read_file_contents());
           
    }
}
