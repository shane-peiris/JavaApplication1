package javaapplication1;
/**
 *
 * @author Mali
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;



public class CriticalTextAnalyzer_m
{
    public static String line="";
    public static String filePath="";
    public static int totalAlphaWordCount=0;
    public static int tot_lit_strings=0;
    public static Vector literal_strings = new Vector<Object>(); 
    //public static int x=0;
    
     int flag_1=0;
    int flag_2=0;
    int flag_3=0;
    
    private CriticalTextAnalyzer_m(String f_path) 
    {
        filePath=f_path;
    }
      
//      public int getNumTokens()
//    {  
//        int c_comment_flag=0;
//        int s_comment_flag=0;
//        String delims="\t\n,;{}[]().-<>&^%$@!-+/*~= ";
//        StringTokenizer splitter = new StringTokenizer(line,delims,true);
//        int numTokens=splitter.countTokens();
//        
//        int alphaNumWordCount=0;
//                     
//     	for(int i=0;i<numTokens;i++)
//        {
//            String text=splitter.nextToken();
//            
//            if(Character.isLetter(text.charAt(0))|| text.charAt(0)=='_')
//            {      
//                 if(!((text.charAt(0) == '_') & (text.length()==1)))
//                 {               
//                    alphaNumWordCount++;
//                 }   
//            }
//        }
//        return alphaNumWordCount;       
//    }
       
      public int ignoreComments()
      {
          String line2="";
        line2 = line.replace("\\\"", "");
        line2 = (line2.replaceAll("\"([^\"]*)\"", ""));
        line2 = (line2.replaceAll("\'([^\"]*)\'", ""));
        
        String delims="\t\n,;{}[]().-<>&^%$@!-+/*~= ";
        StringTokenizer splitter = new StringTokenizer(line2,delims,true);
        int numTokens=splitter.countTokens();
        
        int alphaNumWordCount=0;
        int c_comment_flag=0;
        int s_comment_flag=0;         
     	for(int i=0;i<numTokens;i++)
        {
             String text=splitter.nextToken();
            
            if(s_comment_flag==2)
            { 
                break;
            }
            if((c_comment_flag==2)|(c_comment_flag==3))
            {
                if(text.charAt(0)=='*')
                {
                    c_comment_flag++;
                    continue;
                }
                if(text.charAt(0)=='/')
                {     
                    c_comment_flag=0;
                    s_comment_flag=0;
                    continue;
                }
                continue;         
            }
            if((text.charAt(0)=='/')&((c_comment_flag==0)|(s_comment_flag==0)))
            {
                c_comment_flag++;
                s_comment_flag++;
                continue;
            }
            
           if((text.charAt(0)=='/'))
           {
                s_comment_flag++;
                continue;
           }
           else if(text.charAt(0)=='*')
           {
               c_comment_flag++;
               continue;
           }
        
            if(Character.isLetter(text.charAt(0))|| text.charAt(0)=='_')
            {      
                 if(!((text.charAt(0) == '_') & (text.length()==1)))
                 {               
                    alphaNumWordCount++;
                    System.out.println(""+text);
                 }   
            }
        }
        return alphaNumWordCount;
      
      }
      public int getNumStrings()
      {
            String line2 = line;
            String delims="\t\n,;{}[]().-<>&^%$@!-+\\/*'~\"= ";
            StringTokenizer splitter = new StringTokenizer(line2,delims,true);
            int numTokens=splitter.countTokens();
            int literal_string_count_for_line = 0;
            
            for(int i=0;i<numTokens;i++)
            {
             String text=splitter.nextToken();
            
            if((flag_1==0) & (flag_2==0))
            {
                if ((text.charAt(0) == '\''))
                {
                    flag_1=1;
                }
                else if((text.charAt(0) == '\"'))
                {
                    flag_2=1;
                }
            }
            else if(flag_1 == 1)
            {
                if (!(text.charAt(0) == '\''))
                {                   
                    //System.out.println(text);
                }
                else
                {
                    flag_1 = 0;
                    literal_string_count_for_line+=1;
                    //System.out.println(text);
                }
            }
            else if(flag_2 == 1)
            {
                if (!(text.charAt(0) == '\"'))
                {
                    if ((text.charAt(0) == '\\'))
                    {
                        flag_3=1;
                    }
                    else
                    {
                        flag_3=0;
                    }
                    //System.out.println(text);
                }
                else
                {
                    if ((text.charAt(0) == '\"') & (flag_3==1))
                    {
                        flag_3=0;
                        
                        
                    }                    
                    flag_2 = 0;
                    literal_string_count_for_line+=1;
                    //System.out.println(text);
                }
            }
                        
        }
            
            return literal_string_count_for_line;
      }
      
      public Vector getStrings()
      {
        
          
          
            String line2 = line;
            String delims="\t\n,;{}[]().-<>&^%$@!-+\\/*'~\"= ";
            StringTokenizer splitter = new StringTokenizer(line2,delims,true);
            int numTokens=splitter.countTokens();
            
            String cur_lit_string="";
            Vector curr_literal_strings = new Vector<Object>(); 
            //int literal_string_count_for_line = 0;
            
            for(int i=0;i<numTokens;i++)
            {
             String text=splitter.nextToken();
            
            if((flag_1==0) & (flag_2==0))
            {
                if ((text.charAt(0) == '\''))
                {
                    flag_1=1;
                }
                else if((text.charAt(0) == '\"'))
                {
                    flag_2=1;
                }
            }
            else if(flag_1 == 1)
            {
                if (!(text.charAt(0) == '\''))
                {                   
                    //System.out.println(text);
                    cur_lit_string+=  text;
                }
                else
                {
                    flag_1 = 0;
                    curr_literal_strings.add(cur_lit_string);
                    cur_lit_string = ""; 
                    //System.out.println(text);
                }
            }
            else if(flag_2 == 1)
            {
                if (!(text.charAt(0) == '\"'))
                {
                    if ((text.charAt(0) == '\\'))
                    {
                        flag_3=1;
                    }
                    else
                    {
                        flag_3=0;
                    }
                    //System.out.println(text);
                    cur_lit_string+=  text;
                }
                else
                {
                    if ((text.charAt(0) == '\"') & (flag_3==1))
                    {
                        flag_3=0;
                        cur_lit_string+=  text;
                        
                    }                    
                    flag_2 = 0;
                    curr_literal_strings.add(cur_lit_string);
                    cur_lit_string = ""; 
                    //System.out.println(text);
                }
            }
                        
        }
          
         return curr_literal_strings;
        }
      
    public static void main(String[] args)
    {      
        String path="test.txt";
        FileReader fRead = null;
        try {
                //System.out.print("Please enter file name in the current directory : ");
               
               // path = System.console().readLine();
                CriticalTextAnalyzer_m cta=new CriticalTextAnalyzer_m(path);
                //criticalTextAnalyzer ca=new criticalTextAnalyzer();
                fRead = new FileReader(filePath);
                BufferedReader bufRead = new BufferedReader(fRead);        
                while ((line = bufRead.readLine()) != null)   
                    {
                         //=cta.getNumTokens();
                           totalAlphaWordCount+= cta.ignoreComments();
                           tot_lit_strings+= cta.getNumStrings();
                           literal_strings= cta.getStrings();
                           
                    }
                bufRead.close();
                System.out.println("Total Alphanumeric Tokens :"+totalAlphaWordCount);
                System.out.println("Total Literal String :"+tot_lit_strings);
                System.out.println("Total Literal String :"+literal_strings);
            }catch (Exception ex) 
            {   
                System.out.println("Unable to read line..!!" + ex.getMessage()+"\n");          
            }      
       }  
}
    
    
     

