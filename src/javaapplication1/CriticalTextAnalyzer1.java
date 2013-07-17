package javaapplication1;

/**
 *
 * @author Shane
 */
import java.io.*;

public class CriticalTextAnalyzer1 {

    //Variable to hold the file path
    public static String file_path="";
    //Variable to hold the one line data
    public static String line="";
    //Variable to hold the total alphanumeric token count
    public static int tot_alpha_word_count=0;
    
    CriticalTextAnalyzer1(String u_file_path) 
    {
        file_path=u_file_path;        
    }
    
    
    
    public int getNumTokens()
    {    
        //Variable to hold the total alphanumeric token count in the line read
        int alph_word_count_for_line = 0;
        //Variable to hold the flag value whether the token is an alphanumeric token
        int flag=0;
        //Variable to hold the flag value whether the token has atleast one letter
        int alpha=0;
        
        
        String[] words=line.split("\\s+");
        int no_of_words = words.length;
        
       
        for(int i=0;i<no_of_words;i++)
        {   
            flag=0;
            alpha=0;
            if(Character.isLetter(words[i].charAt(0)) || words[i].charAt(0) == '_')
            {
                if(!((words[i].charAt(0) == '_') & (words[i].length()==1)))
                {                       
                    for(int x=0;x<words[i].length();x++)
                    {
                        if(Character.isLetter(words[i].charAt(0)))
                        {
                                alpha=1;
                        }
                        
                        if(!String.valueOf(words[i].charAt(x)).matches("[0-9A-Za-z_]"))
                        {
                                flag=1;
                        }
                    }
                }
                else
                {
                    flag=1;                    
                }
            }            
            else
            {
                flag=1;
            } 
            if (flag==0 && alpha==1)
            {
                alph_word_count_for_line +=1;
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
        CriticalTextAnalyzer1 c = new CriticalTextAnalyzer1("Shane.txt");
        System.out.println(c.read_file_contents());
           
    }
}
