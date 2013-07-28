package javaapplication1;
/**
 *
 * @author Shane
 */

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;


public class CriticalTextAnalyzer {

    //Variable to hold the file path
    public static String file_path="";
    //Variable to hold the one line data
    public static String line="";
    //Variable to hold the total alphanumeric token count
    public static int tot_alpha_word_count=0;
    //Variable to hold the total literal string count
    public static int tot_literal_string_count=0;
    
    public String delims = "\t\n,;{}[]().-<>&^%$@!-+\\/*'~\"= ";
    
    //Comment Flag Variables
    int fw_flag_c=0;
    int st_flag_c=0;
    
    int flag_1=0;
    int flag_2=0;
    int flag_3=0;
    
   
    int alpha_string_count_for_line = 0; 
    
    public Vector literal_strings = new Vector<Object>();
    
    
    CriticalTextAnalyzer(String u_file_path) 
    {
        //Intializes file path sent while object creationg to the variable file_path
        file_path=u_file_path;        
    }  
    
    public int check_comment(String word)
    {
        int ret=0;
        
            if(fw_flag_c==2)
            {
                ret = 0;
                return ret;
            }            
            else if(!((word.charAt(0) == '*')|(word.charAt(0) == '/')))
            {              
                if((st_flag_c==2) |(st_flag_c==3))
                {
                    ret = 1;
                    return ret;
                }
            } 
             if ((word.charAt(0) == '/') & ((fw_flag_c==0)&(st_flag_c==0)))
             {
                    fw_flag_c += 1;
                    st_flag_c += 1;
                    ret = 1;
                    return ret;
             }
             else
             {
                 if((fw_flag_c==1)|(st_flag_c==1))
                 {
                     if(word.charAt(0) == '/')
                     {
                        fw_flag_c += 1;
                        ret = 1;
                        return ret;
                     }
                     else if(word.charAt(0) == '*')
                     {
                        st_flag_c += 1;
                        fw_flag_c = 0;
                        ret = 1;
                        return ret;
                     }  
                     else
                     {
                         st_flag_c = 0;
                         fw_flag_c = 0;
                     }
                 } 
                 else if((st_flag_c==2) &(word.charAt(0) == '*'))
                 {
                     st_flag_c+=1;
                     ret = 1;
                     return ret;
                 }
                 else if((st_flag_c==3) &(word.charAt(0) == '/'))
                 {
                     st_flag_c=0;
                     ret = 1;
                     return ret;
                 }
                 
             }
             return 2;
    }
    
    public int check_literal_strings(String word)
    {
        //int ret=0;
        
        if((flag_1==0) & (flag_2==0))
            {
                if ((word.charAt(0) == '\''))
                {
                    flag_1=1;
                }
                else if((word.charAt(0) == '\"'))
                {
                    flag_2=1;
                }
            }
            else if(flag_1 == 1)
            {
                if (!(word.charAt(0) == '\''))
                {                   
                    return 0;
                }
                else
                {
                    flag_1 = 0;
                    return 1;
                }
            }
            else if(flag_2 == 1)
            {
                if (!(word.charAt(0) == '\"'))
                {
                    if ((word.charAt(0) == '\\'))
                    {
                        flag_3=1;
                    }
                    else
                    {
                        flag_3=0;
                    }
                    return 0;
                }
                else
                {
                    if ((word.charAt(0) == '\"') & (flag_3==1))
                    {
                        flag_3=0;
                        
                        return 0;
                    }                    
                    flag_2 = 0;
                    return 1;
                }
            }
        
        return 2;
    }
    
    public int getNumTokens()
    {        
        StringTokenizer splitter = new StringTokenizer(line, delims, true);
        int numTokens = splitter.countTokens();        
        
        String word="";
        //Variable to hold the total alphanumeric token count in the line read
        int alph_word_count_for_line = 0; 
        
        
        
        
        for(int i=0;i<numTokens;i++)
        {    
             //Read next token in current line
             word = splitter.nextToken();
                   
            
            int ret = check_comment(word);
            
            if (ret==0)   
            {
                break;   
            }
            else if(ret==1)
            {
                continue;
            }
            
             //Checks whether the first character is either a letter or an underscore
             if (Character.isLetter(word.charAt(0)) || word.charAt(0) == '_')
             {     
                 //Checks whether the token is NOT an underscore and only with a length of 1
                 if(!((word.charAt(0) == '_') & (word.length()==1)))
                 {
                     //Increament token count found in current line
                      alph_word_count_for_line += 1;
                      System.out.println(word);
                 }                         
             }          
             
        }       
        //return alphanumeric token count of current line
        fw_flag_c=0;
        return alph_word_count_for_line;
        
    }
    
    
    public int getNumStrings()
    {        
        StringTokenizer splitter = new StringTokenizer(line, delims, true);
        int numTokens = splitter.countTokens();   
        
        //Variable to hold the total alphanumeric token count in the line read    
        String word="";
        String cur_lit_string="";
        int literal_string_count_for_line = 0; 
        
        
        while(splitter.hasMoreTokens())
        {
            word = splitter.nextToken();
            
            int ret = check_comment(word);
            
            if (ret==0)   
            {
                break;   
            }
            else if(ret==1)
            {
                continue;
            }
            
            int ret2 = check_literal_strings(word);
            
            if (ret2 == 0)
            {
                cur_lit_string+=  word;
                if(Character.isLetter(word.charAt(0)))                    
                alpha_string_count_for_line+= 1;
            }
            else if (ret2 ==1)
            {
                literal_strings.add(cur_lit_string);
                cur_lit_string = "";
                literal_string_count_for_line+= 1;
            }
            
            
            
//            if((flag_1==0) & (flag_2==0))
//            {
//                if ((word.charAt(0) == '\''))
//                {
//                    flag_1=1;
//                }
//                else if((word.charAt(0) == '\"'))
//                {
//                    flag_2=1;
//                }
//            }
//            else if(flag_1 == 1)
//            { if((flag_1==0) & (flag_2==0))
//                if (!(word.charAt(0) == '\''))
//                {
//                    cur_lit_string+=  word;
//                    if(!(word.charAt(0) == ' '))                    
//                        alpha_string_count_for_line+= 1;
//                }
//                else
//                {
//                    literal_strings.add(cur_lit_string);
//                    cur_lit_string = "";
//                    flag_1 = 0;
//                    literal_string_count_for_line+= 1;
//                }
//            }
//            else if(flag_2 == 1)
//            {
//                if (!(word.charAt(0) == '\"'))
//                {
//                    cur_lit_string+=  word;
//                    if(!(word.charAt(0) == ' '))  
//                        alpha_string_count_for_line+= 1;
//                }
//                else
//                {
//                    literal_strings.add(cur_lit_string);
//                    cur_lit_string = "";
//                    flag_2 = 0;
//                    literal_string_count_for_line+= 1;
//                }
//            }
        }
        
        return literal_string_count_for_line;
    }
    
    public Vector getStrings()
    {
        
        return literal_strings;
    }
    
    public void read_file_contents()
    {
         try{

        FileReader fileToRead = new FileReader(file_path);
        //Instantiate the BufferedReader Class to read the file
        BufferedReader bufferReader = new BufferedReader(fileToRead);        
        // Read file line by line and print on the console
        
        int prv_flag=0;
        
        while ((line = bufferReader.readLine()) != null)   
        {   
                       
            fw_flag_c=0;
             if((st_flag_c!=2) & (st_flag_c<=2))
                st_flag_c=0;
//            if(st_flag_c!=2)
//                st_flag_c=0;    
            
            tot_alpha_word_count+= getNumTokens();
            System.out.println(tot_alpha_word_count);

//            fw_flag_c=0;
//            if((st_flag_c!=2) & (st_flag_c<=2))
//                st_flag_c=0;
//            else if((st_flag_c!=3)&(st_flag_c>2))
//                st_flag_c=0;

            prv_flag = st_flag_c;
            st_flag_c=0;
            
            tot_literal_string_count+= getNumStrings();
            System.out.println(tot_literal_string_count);
            
            st_flag_c = prv_flag;
                 
        } 
        
        //Close the buffer reader
        bufferReader.close();
        
        //Display final total count of Alphanumeric Tokens
        System.out.println("\nTotal count of Alphanumeric Tokens (without comments and literal strings) : " + (tot_alpha_word_count-alpha_string_count_for_line));
        System.out.println("\nTotal count of Literal Strings : " + tot_literal_string_count);
        System.out.println("\nThe list of Literal Strings : ");
        
        for (int l=0;l<literal_strings.size();l++)
        {
            System.out.println( String.valueOf(l+1) + ". \"" + literal_strings.elementAt(l)+"\"");
        }
        
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
        
         CriticalTextAnalyzer c = new CriticalTextAnalyzer("test.txt");  
         c.read_file_contents();       
        
        
//        System.out.println("\n******************Critical Text Analyzer***********************\n");
//        String path="";
//        do{
//            System.out.print("Please enter file name in the current directory to anaylse : ");
//            //Get file path directory from user
//            path = System.console().readLine();
//
//            if (path.equals(""))
//            {
//                System.out.println("\nNo input found\n");                
//            }
//            else
//            {
//                //Create new object of class along with passing the file path
//                CriticalTextAnalyzer c = new CriticalTextAnalyzer(path);  
//                c.read_file_contents();                
//            }
//        }while(path.equals(""));
//          
    }
}
