package javaapplication1;
/**
 *
 * @author Shane
 */

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    
    public int line_count=0;
    //Comment Flag Variables
    int fw_flag_c=0;
    int st_flag_c=0;
    
    int flag_1=0;
    int flag_2=0;
    int flag_3=0;
    
    
    public int int_var_count=0;
    public int char_var_count=0;
    public int byte_var_count=0;
    public int short_var_count=0;
    public int long_var_count=0;
    public int double_var_count=0;
    public int float_var_count=0;
    public int boolean_var_count=0;
    
    public int var_flag=0;
    
    public String last_var_type="";
    
   
    int alpha_string_count_for_line = 0; 
    
    public Vector literal_strings = new Vector<Object>();
    
    
    CriticalTextAnalyzer(String u_file_path) 
    {
        //Intializes file path sent while object creationg to the variable file_path
        file_path=u_file_path;        
    }  
    
    public int check_comment_old(String word)
    {
        int ret=0;
        
            if(fw_flag_c==2)
            {
                ret = 0;
                return ret;
            }   
            else if((st_flag_c==3) & !(word.charAt(0) == '/'))
            {
                     st_flag_c=2;
                     ret = 1;
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
    
    public int check_comment_new()               
    {
        
         line = (line.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)",""));
        
        return 0;
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
         String new_line="";
        //New Comment Remover Start            
          
            new_line = line.replace("\\\"", "");
                
            new_line = (new_line.replaceAll("\"([^\"]*)\"", ""));
             
            new_line = (new_line.replaceAll("\'([^\"]*)\'", ""));
        //New Comment Remover End
        
        StringTokenizer splitter = new StringTokenizer(new_line, delims, true);
        int numTokens = splitter.countTokens();        
        
        String word="";
        //Variable to hold the total alphanumeric token count in the line read
        int alph_word_count_for_line = 0;        
        
        for(int i=0;i<numTokens;i++)
        {    
            //Read next token in current line
            word = splitter.nextToken();
    
                       
                    //New Complex Comment Handler END            
            
                     if ((word.charAt(0)=='/') &(fw_flag_c==0))
                    {
                        fw_flag_c=1;
                        continue;
                    }
                    if ((word.charAt(0)=='*') &(fw_flag_c==1))
                    {
                        fw_flag_c=2;
                        continue;
                    }
                    if ((word.charAt(0)=='*') &(fw_flag_c==2))
                    {
                        fw_flag_c=3;
                        continue;
                    }
                    if ((word.charAt(0)=='/') &(fw_flag_c==3))
                    {
                        fw_flag_c=0;
                        continue;
                    }
                     
                    if ((fw_flag_c==2)|(fw_flag_c==3))
                        continue;
                    
                    //New Complex Comment Handler END
            
            
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
        //fw_flag_c=0;
        return alph_word_count_for_line;
        
    }
    
    
    public int getNumStrings()
    {        
        StringTokenizer splitter = new StringTokenizer(line, delims, true);
        
        //Variable to hold the total alphanumeric token count in the line read    
        String word="";
        
        int literal_string_count_for_line = 0;         
        
        while(splitter.hasMoreTokens())
        {
            word = splitter.nextToken();
                        
            int ret2 = check_literal_strings(word);
            
            if (ret2 == 0)
            {
               
                if(Character.isLetter(word.charAt(0)))                    
                alpha_string_count_for_line+= 1;
            }
            else if (ret2 ==1)
            {
                
                literal_string_count_for_line+= 1;
            }
            
        }
        
        return literal_string_count_for_line;
    }
    
    public Vector getStrings()
    {
        StringTokenizer splitter = new StringTokenizer(line, delims, true);          
        
        //Variable to hold the total alphanumeric token count in the line read    
        String word="";
        String cur_lit_string="";
        
        Vector curr_literal_strings = new Vector<Object>();
        
        while(splitter.hasMoreTokens())
        {
            word = splitter.nextToken();
            
            int ret2 = check_literal_strings(word);
            
            if (ret2 == 0)
            {
                cur_lit_string+=  word;
                System.out.println("***************"+word+"******************");
            }
            else if (ret2 ==1)
            {
                curr_literal_strings.add(cur_lit_string);
                
                cur_lit_string = "";                
            }
        }
        return curr_literal_strings;
    }
    
    public void add_variable_type(String proc_line)
    {
        String var_type="";
        int comma_count=1;
        int multi;
        
        if(var_flag==1 & line_count==0)
            multi=1;
        else if(var_flag==1)
            multi=0;
        else
            multi=1;
        
        //Vector comma_seps = new Vector<Object>();
        
        String pat="(.*?),";                
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(proc_line);
        while (m.find()) {
            //comma_seps.add(m.group(1));
            multi++;
        }
        
        if (multi>1)
            comma_count = comma_count * (multi);        
         
        if(var_flag==1 & line_count!=0)
        {
//            if(proc_line.matches("(.*?)"+ last_var_type +" (.*?)"))
//            {
                switch(last_var_type) {
                    case "int":
                        int_var_count = int_var_count + comma_count;
                        break;
                    case "char":
                        char_var_count = char_var_count + comma_count;
                        break;
                    case "byte":
                        short_var_count = short_var_count + comma_count;
                        break;
                    case "short":
                        char_var_count = char_var_count + comma_count;
                        break;
                    case "long":
                        long_var_count = long_var_count + comma_count;
                        break;
                    case "double":
                        double_var_count = double_var_count + comma_count;
                        break;
                    case "float":
                        float_var_count = float_var_count + comma_count;
                        break;
                    case "boolean":
                        boolean_var_count = boolean_var_count + comma_count;
                        break;
                }
            //}
        }
        else
        {
            if(proc_line.matches("(.*?)int (.*?)")){
                    int_var_count = int_var_count + comma_count;
                    last_var_type="int";}
            else if(proc_line.matches("(.*?)char (.*?)")){
                    char_var_count = char_var_count + comma_count;
                    last_var_type="char";}
            else if(proc_line.matches("(.*?)byte (.*?)")){
                    byte_var_count = byte_var_count + comma_count;
                    last_var_type="byte";}
            else if(proc_line.matches("(.*?)short (.*?)")){
                    short_var_count = short_var_count + comma_count;
                    last_var_type="short";}
            else if(proc_line.matches("(.*?)long (.*?)")){
                    long_var_count = long_var_count + comma_count;
                    last_var_type="long";}
            else if(proc_line.matches("(.*?)double (.*?)")){
                    double_var_count = double_var_count + comma_count;
                    last_var_type="double";}
            else if(proc_line.matches("(.*?)float (.*?)")){
                    float_var_count = float_var_count + comma_count;
                    last_var_type="float";}
            else if(proc_line.matches("(.*?)boolean (.*?)")){
                    boolean_var_count = boolean_var_count + comma_count;
                    last_var_type="boolean";}
        }
    }
    
    public void identify_variable()
    {
        String cur_line=line;
        cur_line = cur_line.replace("public", "");
        cur_line = cur_line.replace("protected", "");
        cur_line = cur_line.replace("private", "");
        cur_line = cur_line.trim().replace(",\\s+", ",");
        
//        System.out.println("*******Line Without public/private/protected************");
//        System.out.println(cur_line);
//        System.out.println("********************************************************\n");
        try
        {
            if(cur_line.matches("(.*?)\\((.*?)\\)(.*?)") & (!(cur_line.substring(0, 3).equals("for"))))
            {               
                cur_line = cur_line.replaceAll("\\)", ",\\)");
                System.out.println(cur_line);
                String pat="(.*?),(.*?)";                
                Pattern p = Pattern.compile(pat);
                Matcher m = p.matcher(cur_line.substring((cur_line.indexOf("("))+1));
                while (m.find()) {
                    //semi_seps.add(m.group(1));
        // System.out.println(m.group(1));
                   // var_flag=0;
                   System.out.println(m.group(1));
                   add_variable_type(m.group(1));
                    
                }
               
            }
        }
        catch(Exception ex){}
        
        Vector semi_seps = new Vector<Object>();
        try
        {
        if((cur_line.substring((cur_line.length())-1)).equals(","))
                {
                    //System.out.println(cur_line);
                    //System.out.println("comma2");
                    var_flag = 1;
                    
                }
        }
        catch(Exception ex){}
//        System.out.println("*******Line Semi Break************");  
        if (var_flag!=1)
        {
            String pat="(.*?);";                
            Pattern p = Pattern.compile(pat);
            Matcher m = p.matcher(cur_line);
            while (m.find()) {
                semi_seps.add(m.group(1));
    //            System.out.println(m.group(1));
                var_flag=0;
            }
    //        System.out.println("**********************************\n");
        }
        else
        {
            int count=0;
            
            String pat="(.*?);";                
            Pattern p = Pattern.compile(pat);
            Matcher m = p.matcher(cur_line);
            while (m.find()) {
                if (count!=0)
                {
                    semi_seps.add(m.group(1));
     //             System.out.println(m.group(1));
                    var_flag=0;
                }
                else
                {
                    add_variable_type(m.group(1));
                }
                
                count++;
            }
            
                    
        }
            
        if( semi_seps.size()<=0)
        {
            //System.out.println("comma");
            try
            {
                
                if((cur_line.substring((cur_line.length())-1)).equals(","))
                {
                    //System.out.println(cur_line);
                    //System.out.println("comma2");
                    var_flag = 1;
                    add_variable_type(cur_line);
                }
            }
            catch(Exception ex)
            {}
                
        }
        else
        {        
            for (int i=0;i<semi_seps.size();i++)
            {
              //System.out.println(cur_line); 
                //System.out.println("semi");
               add_variable_type(semi_seps.elementAt(i).toString());
            }
        }
    }
    
    public int getNumIntVariables()
    {return int_var_count;}
    public int getNumBooleanVariables()
    {return boolean_var_count;}
    public int getNumCharVariables()
    {return char_var_count;}
    public int getNumDoubleVariables()
    {return double_var_count;}
    public int getNumFloatVariables()
    {return float_var_count;}
    public int getNumByteVariables()
    {return byte_var_count;}
    public int getNumShortVariables()
    {return short_var_count;}
    public int getNumLongVariables()
    {return long_var_count;}
    
    public void read_file_contents()
    {
         try{

        FileReader fileToRead = new FileReader(file_path);
        //Instantiate the BufferedReader Class to read the file
        BufferedReader bufferReader = new BufferedReader(fileToRead);        
        // Read file line by line and print on the console
        
        while ((line = bufferReader.readLine()) != null)   
        {   
            
            check_comment_new();
                        
            tot_alpha_word_count+= getNumTokens();
          
            tot_literal_string_count+= getNumStrings();
            
            literal_strings.add(getStrings());
            
            identify_variable();
            line_count++;
        } 
        
        //Close the buffer reader
        bufferReader.close();
        
        //Display final total count of Alphanumeric Tokens
        System.out.println("\nTotal count of Alphanumeric Tokens (without comments and literal strings) : " + (tot_alpha_word_count));
        System.out.println("\nTotal count of Literal Strings : " + tot_literal_string_count);
        
        
        int count=1;
        
        if(tot_literal_string_count>0)
        {
            System.out.println("\nThe list of Literal Strings : ");
        }
        
        for (int l=0;l<literal_strings.size();l++)
        {
                if(!(literal_strings.elementAt(l).toString().equals("[]")))
                { 
                    System.out.println( String.valueOf(count) + ". \"" + literal_strings.elementAt(l)+"\"");
                    count++;
                }
        }
        
        System.out.println("\nTotal int variable count " + getNumIntVariables());
        System.out.println("\nTotal boolean variable count " + getNumBooleanVariables());
        System.out.println("\nTotal char variable count " + getNumCharVariables());
        System.out.println("\nTotal double variable count " + getNumDoubleVariables());
        System.out.println("\nTotal float variable count " + getNumFloatVariables());
        System.out.println("\nTotal byte variable count " + getNumByteVariables());
        System.out.println("\nTotal short variable count " + getNumShortVariables());
        System.out.println("\nTotal long variable count " + getNumLongVariables());
        
        
        
        
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
