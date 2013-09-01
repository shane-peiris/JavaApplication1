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
import sun.org.mozilla.javascript.internal.Evaluator;
import sun.org.mozilla.javascript.internal.regexp.SubString;

class ClassDefinition
{    
    public Vector interf_names = new Vector<Object>(); 
    public Vector method_names = new Vector<Object>(); 
    public String parent_class="";
    public String class_name="";
    public int class_line=0;
    public int max_class_nest_depth = 0;
    
    public String getParentClassName()
    {    
        return parent_class;
    }
    
    public Vector getInterfaceNames()
    {    
        return interf_names;
    }
    
    public Vector getMethodDefinitions()
    {
        return method_names;
    }
    
    public int getNumberOfLines()
    {
        return class_line;
    }
    
    public int getMaximumNesting()
    {
        return max_class_nest_depth;
    }
}

class MethodDefinition
{
    public Vector para_Defs = new Vector<Object>();
    public Vector loc_variables = new Vector<Object>();
    public String method_name="";
    public String return_type=""; 
    public int meth_line=0;
    public int max_meth_nest_depth=0;
    
    public String getMethodName()
    {    
        return method_name;
    }

    public String getReturnType()
    {
        return return_type;
    }
    
    public Vector getParameterDefinitions()
    {    
        return para_Defs;
    }
    
    public Vector getLocalVariables()
    {    
        return loc_variables;        
    }
    
    public int getNumberOfLines()
    {
        return meth_line;
    }
    public int getMaximumNesting()
    {
        return max_meth_nest_depth;
    }
    
}

class VariableDefinition
{
    public String var_name="";
    public String var_type="";
    public String var_par_or_prim;
    
    public String getVariableName()
    {
        return var_name;
    }
    
    public String getVariableType()
    {
        return var_type;
    }
    
    public boolean isParameter()
    {
        if(var_par_or_prim.equals("Parameter"))
        {
            return true;
        }
        return false;
    }
    
    public boolean isPrimitive()
    {
        if(var_par_or_prim.equals("Primitive"))
        {
            return true;
        }
        return false;
    }    
}
public class CriticalTextAnalyzer{

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
    
    int file_line_count=0;    
    
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
    
    public Vector file_class_Details = new Vector<Object>();
    public String class_def_id = "1";
    
    public int cd_count=0;
    
    public static ClassDefinition[] cd_temp;
        
    public int cls_meth_flag=0;
    public int meth=0;
    public String prv_line="";
    public Vector file_meth_Details = new Vector<Object>();
    public static MethodDefinition[] md;
    public int md_count=0;
    
    public String class_in="";
    
    public String cur_class="";
    public String prim_para="";
    
    public int class_line_count=0;
    public int meth_line_count=0;
    
    public int max_meth_nest=0;
    public int cur_meth_nest=0;
    
   //ClassDefinition[] cd;
    
    CriticalTextAnalyzer(String u_file_path) 
    {
        //Intializes file path sent while object creationg to the variable file_path
        file_path=u_file_path;        
    }  
    
    public Vector getMethodDefinitions()
    {
        Vector chk_method_details = new Vector<Object>();
        
        String cur_line = line;
        
        String method_name="";
        String meth_ret_type="";
                
        cur_line = cur_line.replace("public", "");
        cur_line = cur_line.replace("protected", "");
        cur_line = cur_line.replace("private", "");
        cur_line = cur_line.replace("static", "");
        cur_line = cur_line.replace("final", "");
        cur_line = cur_line.trim().replace(",\\s+", ",");
        
        
        //System.out.println(cur_line);
        
        
        
        try
        {
            
          
            
            
                //Identify Class
            if((cur_line.contains("{"))&(prv_line.matches("class (.*?)"))&(cls_meth_flag==0))
            {        
                cls_meth_flag=1;  
                meth=1;
                //System.out.println("Inside class********************************************************");

                try
                {
                    class_in= prv_line.substring(6,prv_line.indexOf(" ", 6));
                }
                catch(Exception ex)
                {
                    class_in= prv_line.substring(6);
                }

                class_line_count=0;

                class_in = class_in.replace("{", "");
                class_in = class_in.replace(" ", "");
                //System.out.println(class_in);

            }
            //Identify Class
            else if((cur_line.contains("{"))&(cur_line.matches("class (.*?)"))&(cls_meth_flag==0) )
            {        
                cls_meth_flag=1;
                meth=1;
                //System.out.println("Inside class*************************************************");

                try
                {
                    class_in= cur_line.substring(6,prv_line.indexOf(" ", 6));
                }
                catch(Exception ex)
                {
                    class_in= cur_line.substring(6);
                }   


                //System.out.println(String.valueOf(cur_meth_nest));

                //cur_meth_nest=0;

                class_line_count=0;

                class_in = class_in.replace("{", "");
                class_in = class_in.replace(" ", "");
                //System.out.println(class_in);

            }  
            //Identify Constructor
             else if(((cur_line.matches(class_in+" \\((.*?)\\)\\s+\\{"))|(cur_line.matches(class_in+"\\((.*?)\\)\\s+\\{"))|(cur_line.matches(class_in+"\\((.*?)\\)\\{"))|(cur_line.matches(class_in+"\\((.*?)\\)\\{")))&(meth==1)&((cur_line.contains("{"))))
            {
                cls_meth_flag++;
                meth=2;         
    //            System.out.println((cur_line.indexOf(" ", 0)+1));
    //            System.out.println((cur_line.indexOf("(")));
    //            System.out.println(((cur_line.indexOf("\\("))-(cur_line.indexOf(" ", 0))));
                method_name=cur_line.substring(0,((cur_line.indexOf("("))));
                method_name = method_name.trim().replace(" ", "");
                //System.out.println(method_name);  
                chk_method_details.add(method_name);

    //            meth_ret_type=cur_line.substring(0,(cur_line.indexOf(" ")));
    //            System.out.println(meth_ret_type);  
    //            chk_method_details.add(meth_ret_type);

                //System.out.println("Inside method***********************************************************");

                cd_temp[cd_count-1].method_names.add(method_name.toString());


                md[md_count].method_name = method_name.toString();
                md[md_count].return_type = "NULL";
                try
                {
                md[md_count-1].meth_line = meth_line_count+1;
                }
                catch(Exception ex)
                {

                }
                md_count++;

                //System.out.println(String.valueOf(cur_meth_nest));

                //cur_meth_nest=0;
                meth_line_count=0;

                //identify_variable("count");
            } 
              //Identify Constructor
              else if(((prv_line.matches(class_in+" \\((.*?)\\)"))|(prv_line.matches(class_in+"\\((.*?)\\)")))&(meth==1)&((cur_line.contains("{"))))
            {
                cls_meth_flag++;
                meth=2;         
    //            System.out.println((cur_line.indexOf(" ", 0)+1));
    //            System.out.println((cur_line.indexOf("(")));
    //            System.out.println(((cur_line.indexOf("\\("))-(cur_line.indexOf(" ", 0))));
                method_name=prv_line.substring(0,((prv_line.indexOf("("))));
                method_name = method_name.trim().replace(" ", "");
                //System.out.println(method_name);  
                chk_method_details.add(method_name);

    //            meth_ret_type=cur_line.substring(0,(cur_line.indexOf(" ")));
    //            System.out.println(meth_ret_type);  
    //            chk_method_details.add(meth_ret_type);

                //System.out.println("Inside method*********************************************************************");

                cd_temp[cd_count-1].method_names.add(method_name.toString());


                md[md_count].method_name = method_name.toString();
                md[md_count].return_type = "NULL";
                try
                {
                md[md_count-1].meth_line = meth_line_count+1;
                }
                catch(Exception ex)
                {

                }
                md_count++;

                //System.out.println(String.valueOf(cur_meth_nest));

                //cur_meth_nest=0;

                meth_line_count=0;
                //identify_variable("count");
            } 

             //Identify Method
            else if(((cur_line.matches("(.*?)\\((.*?)\\)\\s+\\{"))|(cur_line.matches("(.*?)\\((.*?)\\)\\{")))&(meth==1)&((cur_line.contains("{"))))
            {
                cls_meth_flag++;
                meth=2;         
    //            System.out.println((cur_line.indexOf(" ", 0)+1));
    //            System.out.println((cur_line.indexOf("(")));
    //            System.out.println(((cur_line.indexOf("\\("))-(cur_line.indexOf(" ", 0))));
                method_name=cur_line.substring((cur_line.indexOf(" ", 0)+1),((cur_line.indexOf("("))));
                method_name = method_name.trim().replace(" ", "");
                //System.out.println(method_name);  
                chk_method_details.add(method_name);

                meth_ret_type=cur_line.substring(0,(cur_line.indexOf(" ")));
                //System.out.println(meth_ret_type);  
                chk_method_details.add(meth_ret_type);  
                //System.out.println("Inside method******************************************************************");

                cd_temp[cd_count-1].method_names.add(method_name.toString());


                md[md_count].method_name = method_name.toString();
                md[md_count].return_type = meth_ret_type.toString();
                try
                {
                md[md_count-1].meth_line = meth_line_count+1;
                }
                catch(Exception ex)
                {

                }

                //System.out.println(String.valueOf(cur_meth_nest));

                //cur_meth_nest=0;


                md_count++;
                meth_line_count=0;
                //identify_variable("count");
            }  
             //Identify Method
            else if((prv_line.matches("(.*?)\\((.*?)\\)"))&(meth==1)&((cur_line.contains("{"))))
            {
                cls_meth_flag++; 
                meth=2;

                method_name=prv_line.substring((prv_line.indexOf(" ", 0)+1),((prv_line.indexOf("("))));
                method_name = method_name.trim().replace(" ", "");
                //System.out.println(method_name);   
                chk_method_details.add(method_name);

                meth_ret_type=prv_line.substring(0,(prv_line.indexOf(" ")));
                //System.out.println(meth_ret_type); 
                chk_method_details.add(meth_ret_type); 

                //System.out.println("Inside method***********************************************************************");
                cd_temp[cd_count-1].method_names.add(method_name.toString());


                md[md_count].method_name = method_name.toString();
                md[md_count].return_type = meth_ret_type.toString();
                try
                {
                md[md_count-1].meth_line = meth_line_count+1;
                }
                catch(Exception ex)
                {

                }

                //System.out.println(String.valueOf(cur_meth_nest));

                //cur_meth_nest=0;


                md_count++;
                meth_line_count=0;
                //identify_variable("count");

            }  
            else if(((cur_line.length()==1)&(cur_line.contains("{")))|(cur_line.substring((cur_line.length())-1).equals("{")))
            {            
                cls_meth_flag++; 
                meth++;
                //cur_meth_nest++;
                //System.out.println("jjjj"+cur_line.substring((cur_line.length()-1)));
                if((cur_line.substring((cur_line.length())-1).equals("}")))
                {
                    cls_meth_flag--;
                    meth--;
                    if(cls_meth_flag==0)
                    {
                        cd_temp[cd_count-1].class_line=class_line_count+1;
                        cd_temp[cd_count-1].max_class_nest_depth=max_meth_nest+1;
                        max_meth_nest = 0;
                        //System.out.println(cur_line);
                        meth=0;
                    }
                    
                    if(meth==1)
                    {
                        try
                        {
                            md[md_count-1].max_meth_nest_depth = cur_meth_nest;
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Error in Meth Nest");
                        }
                        if (max_meth_nest<cur_meth_nest)
                        {
                            max_meth_nest = cur_meth_nest;
                        }
                        //System.out.println(String.valueOf(cur_meth_nest));
                        cur_meth_nest=0;
                    }
                }

            }
            else if(((cur_line.length()==1)&(cur_line.contains("}")))|(cur_line.substring((cur_line.length())-1).equals("}")))
            {
                cls_meth_flag--;
                meth--;
                if(meth==1)
                    {
                        try
                        {
                            md[md_count-1].max_meth_nest_depth = cur_meth_nest;
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Error in Meth Nest");
                        }
                        if (max_meth_nest<cur_meth_nest)
                        {
                            max_meth_nest = cur_meth_nest;
                        }   
                        //System.out.println(String.valueOf(cur_meth_nest));
                        cur_meth_nest=0;
                    }
                if(cls_meth_flag==0)
                {
                    cd_temp[cd_count-1].class_line=class_line_count+1;
                    cd_temp[cd_count-1].max_class_nest_depth=max_meth_nest+1;
                    max_meth_nest = 0;
                    //System.out.println(cur_line);
                    meth=0;
                }
                
            }
            if(cls_meth_flag>0 & meth>=2)
            {

                //System.out.println(cur_line);
                  //meth_line_count++;

                  try
                  {
                      md[md_count] = new MethodDefinition();
                  }
                  catch(Exception ex)
                  {
                      //System.out.println(ex.toString());
                  }

                  identify_variable("Count");  
            }
            else if(cls_meth_flag>0 & meth>=1 & (cur_line.matches("(.*?)\\((.*?)\\)(.*?)")))
            {
                  //System.out.println(cur_line);

                  try
                  {
                      md[md_count] = new MethodDefinition();
                  }
                  catch(Exception ex)
                  {
                      //System.out.println(ex.toString());
                  }

                  identify_variable("Count");  

            }
        
            if(cls_meth_flag>=1 & meth>=1)
            {
                class_line_count++;
            }

            if(cls_meth_flag>=1 & meth>=2)
            {
                meth_line_count++;
            }  
            if((((cur_line.length()==1)&(cur_line.contains("{")))|(cur_line.substring((cur_line.length())-1).equals("{"))|(cur_line.contains("{")))& (cls_meth_flag>=1 & meth>=2))
            {
                cur_meth_nest++;
            }
        }
        catch(Exception ex)
        {
            //System.out.println(ex.toString());
        }
        prv_line = cur_line;
        
        return chk_method_details;
    }
    
    public Vector getClassDefinitions()
    {
        Vector chk_class_details = new Vector<Object>();
        
        String cur_line = line;
        String cur_class_name="";
        
        cur_line = cur_line.replace("public", "");
        cur_line = cur_line.replace("protected", "");
        cur_line = cur_line.replace("private", "");
        cur_line = cur_line.replace("static", "");
        cur_line = cur_line.trim().replace(",\\s+", ",");
        cur_line = cur_line.replace("{", "");
        cur_line = cur_line.replace("}", "");        
        
        if(cur_line.matches("class (.*?)"))
        {
            try
            {
                cur_class_name= cur_line.substring(6,cur_line.indexOf(" ", 6));
            }
            catch(Exception ex)
            {
                cur_class_name= cur_line.substring(6);
            }
            chk_class_details.add("CN - " + cur_class_name);
            
            
            
        
            if(cur_line.matches("class (.*?) extends (.*?)"))
            {
                if(cur_line.matches("class (.*?) extends (.*?) implements (.*?)"))
                {
                    cur_line = cur_line.replace("class", "");
                    cur_line = cur_line.replace(cur_class_name, "");

                    chk_class_details.add("PC - " + cur_line.substring(cur_line.indexOf("extends")+8,cur_line.indexOf("implements")));

                    String[] interfaces=cur_line.substring(cur_line.indexOf("implements")+11).split(",");

                    for (int i=0; i<interfaces.length;i++)
                    {
                        chk_class_details.add("I - " + interfaces[i]);                    
                    }

                    chk_class_details.add("O - " + " ");
                    
                    
                }
                else
                {            
                    cur_class_name= cur_line.substring(cur_line.indexOf("extends")+8);

                    chk_class_details.add("PC - " + cur_class_name);
                    chk_class_details.add("I - " + " ");
                    chk_class_details.add("O - " + " ");
                }
            }
            else if(cur_line.matches("class (.*?) implements (.*?)"))
            {
                cur_class_name= cur_line.substring(6, cur_line.indexOf("implements"));

                cur_line = cur_line.replace("class", "");
                cur_line = cur_line.replace("implements", "");
                cur_line = cur_line.replace(cur_class_name, "");
                cur_line = cur_line.replace(" ", "");

                chk_class_details.add("PC - " + " ");

                String[] interfaces=cur_line.split(",");

                for (int i=0; i<interfaces.length;i++)
                {
                    chk_class_details.add("I - " + interfaces[i]);
                }

                chk_class_details.add("O - " + " ");

            }
            else if(cur_line.matches("class (.*?)"))
            {            
                cur_class_name= cur_line.substring(6);
                chk_class_details.add("PC - " + " ");
                chk_class_details.add("I - " + " ");
                chk_class_details.add("O - Object");
            }       
        }
        else
        {
            chk_class_details.add("CN - " + " ");
        }
        
        return chk_class_details;
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
    
    public void add_to_var_defs(String sep_line,String prim_para_type)
    {        
        Vector temp = new Vector<Object>();
                
        String var_type="";
        String var_name="";
        
        //System.out.println(sep_line + prim_para_type);
        //System.out.println(sep_line.substring(0,sep_line.indexOf(" ")));
        var_type = sep_line.substring(0,sep_line.indexOf(" "));
        sep_line = sep_line.trim().replace(sep_line.substring(0,sep_line.indexOf(" ")), "");
        sep_line = sep_line.trim().replace(" ","");
        
        
       
        
        
        if(sep_line.contains(","))
        {
            for(int i=0;i<sep_line.length();i++)
            {
                if(sep_line.contains(","))
                {       
                    try
                    {
                        //System.out.println("1");
                        //System.out.println((sep_line.substring(0,sep_line.indexOf(","))));
                        var_name = ((sep_line.substring(0,sep_line.indexOf(","))));
                        temp.add(var_name);
                        
                        //System.out.println((sep_line.substring(0,sep_line.indexOf(","))).substring(0,(sep_line.substring(0,sep_line.indexOf(","))).indexOf("=")));
                        sep_line = sep_line.trim().replace(sep_line.substring(0,sep_line.indexOf(",")+1),"");
                        sep_line = sep_line.trim().replace(" ","");
                        
                        
                        
                        //sep_line = sep_line.trim().replace("=(.*?)","");
                    }
                    catch(Exception ex)
                    {
                        //System.out.println("2");
                        //System.out.println(sep_line.substring(0,sep_line.indexOf("=")));
                        
                        var_name = (sep_line.substring(0,sep_line.indexOf("=")));
                        temp.add(var_name);
                        
                        sep_line = sep_line.trim().replace(sep_line.substring(0,sep_line.indexOf("=")),"");
                        sep_line = sep_line.trim().replace("=(.*?),","");
                    }
                }
                else
                {
                    //System.out.println(sep_line);
                    //System.out.println(sep_line.substring(0) + " h");
                    var_name = sep_line.substring(0);
                    temp.add(var_name);
                    break;
                }
            }
        }
        else
        {               
            try
            {
                //System.out.println(sep_line);
                //System.out.println("3");
                //System.out.println(sep_line.substring(0,sep_line.indexOf("=")));
                var_name = (sep_line.substring(0,sep_line.indexOf("=")));
                temp.add(var_name);
            }
            catch(Exception ex)
            {
                //System.out.println("4");
                //System.out.println(sep_line.substring(0));
                var_name = (sep_line.substring(0));
                temp.add(var_name);
            }
        }
        
        
        //System.out.println(var_type);
        for(int x=0;x<temp.size();x++)
        {
            VariableDefinition vd = new VariableDefinition();
        
            vd.var_type = var_type;
            vd.var_name = temp.elementAt(x).toString().trim().replaceAll("=.*", "");
            vd.var_par_or_prim = prim_para_type;
            
            
            
            if(vd.isParameter())
            {
                try
                {
                    md[md_count].para_Defs.add(vd);
                    //System.out.println("Done");
                }
                catch(Exception ex)
                {
                    md[md_count].para_Defs.add(vd);
                    //System.out.println("Done");
                }
            }
            else if(vd.isPrimitive())
            //else if(prim_para_type.equals("Primitive"))
            {
                try
                {
                    md[md_count-1].loc_variables.add(vd);
                    //System.out.println("Done");
                }
                catch(Exception ex)
                {
                    md[md_count].loc_variables.add(vd);
                    //System.out.println("Done");
                }
            }
            
            //System.out.println(temp.elementAt(x).toString().trim().replaceAll("=.*", "")+"\n");
        }
        
        
        
    }
    
    public void add_variable_type(String proc_line,String meth_type)
    {
        String var_type="";
        int comma_count=1;
        int multi;
        //String prim_or_para="";
        
        
        if(var_flag==1 & line_count==0)
            multi=1;
        else if(var_flag==1)
            multi=0;
        else
            multi=1;
        
        //Vector comma_seps = new Vector<Object>();
        
        //System.out.println(proc_line + prim_para);
        
        String pat="(.*?),";                
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(proc_line);
        while (m.find()) {
            //comma_seps.add(m.group(1));
            
            multi++;
        }
        
        if (multi>1)
            comma_count = comma_count * (multi);        
         
        proc_line = proc_line.trim().replace("\\s+", "");
        
        if(var_flag==1 & line_count!=0)
        {
//            if(proc_line.matches("(.*?)"+ last_var_type +" (.*?)"))
//            {
                switch(last_var_type) {
                    case "int":
                        //System.out.println(proc_line + prim_para);
                        int_var_count = int_var_count + comma_count;
                        if(meth_type.equals("Count"))
                            add_to_var_defs(proc_line,prim_para);
                        break;
                    case "char": 
                        //System.out.println(proc_line + prim_para);
                        char_var_count = char_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                        break;
                    case "byte":
                        //System.out.println(proc_line + prim_para);
                        short_var_count = short_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                        break;
                    case "short":
                        //System.out.println(proc_line + prim_para);
                        char_var_count = char_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                        break;
                    case "long":
                        //System.out.println(proc_line + prim_para);
                        long_var_count = long_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                        break;
                    case "double":
                        //System.out.println(proc_line + prim_para);
                        double_var_count = double_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                        break;
                    case "float":
                        //System.out.println(proc_line + prim_para);
                        float_var_count = float_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                        break;
                    case "boolean":
                        //System.out.println(proc_line + prim_para);
                        boolean_var_count = boolean_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                        break;
                }
            //}
        }
        else
        {
            if(proc_line.matches("(.*?)int (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    int_var_count = int_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="int";}
            else if(proc_line.matches("(.*?)char (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    char_var_count = char_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="char";}
            else if(proc_line.matches("(.*?)byte (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    byte_var_count = byte_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="byte";}
            else if(proc_line.matches("(.*?)short (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    short_var_count = short_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="short";}
            else if(proc_line.matches("(.*?)long (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    long_var_count = long_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="long";}
            else if(proc_line.matches("(.*?)double (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    double_var_count = double_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="double";}
            else if(proc_line.matches("(.*?)float (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    float_var_count = float_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="float";}
            else if(proc_line.matches("(.*?)boolean (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    boolean_var_count = boolean_var_count + comma_count;
                        if(meth_type.equals("Count"))
                        add_to_var_defs(proc_line,prim_para);
                    last_var_type="boolean";}
            else if(proc_line.matches("(.*?)String (.*?)")){
                    //System.out.println(proc_line + prim_para);
                    //boolean_var_count = boolean_var_count + comma_count;
                    if(meth_type.equals("Count"))
                    add_to_var_defs(proc_line,prim_para);
                    //last_var_type="boolean";
            }
        }
    }
    
    public void identify_variable(String meth_type)
    {
        String cur_line=line;
        cur_line = cur_line.replace("public", "");
        cur_line = cur_line.replace("protected", "");
        cur_line = cur_line.replace("private", "");
        cur_line = cur_line.trim().replace(",\\s+", ",");
        cur_line = cur_line.replace("{", "");
        cur_line = cur_line.replace("}", "");
        
        
//        System.out.println("*******Line Without public/private/protected************");
//        System.out.println(cur_line);
//        System.out.println("********************************************************\n");
        try
        {
            if(cur_line.matches("(.*?)\\((.*?)\\)(.*?)") & (!(cur_line.substring(0, 3).equals("for"))))
            {               
                cur_line = cur_line.replaceAll("\\)", ",\\)");
                
                String pat="(.*?),(.*?)";                
                Pattern p = Pattern.compile(pat);
                Matcher m = p.matcher(cur_line.substring((cur_line.indexOf("("))+1));
                if(cur_line.matches("(.*?)\\(,\\)(.*?)"))
                {
                    
                }
                else
                {
                    while (m.find()) {
                        //semi_seps.add(m.group(1));
            // System.out.println(m.group(1));
                       // var_flag=0;
                       //System.out.println(m.group(1));
                       //System.out.println("Parameter");
                       
                                               
                       prim_para = "Parameter";
                       add_variable_type(m.group(1),meth_type);

                    }
                }
               
            }
        }
        catch(Exception ex){
        
            //System.out.println(ex.toString());
        }
        
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
                    //System.out.println("Primitive");
                    prim_para = "Primitive";
                    add_variable_type(m.group(1),meth_type);
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
                    //System.out.println("Primitive");
                    prim_para = "Primitive";
                    add_variable_type(cur_line,meth_type);
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
               prim_para = "Primitive"; 
               add_variable_type(semi_seps.elementAt(i).toString(),meth_type);
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
    
    public void class_cat()
    {
        int ele_id=0;
            if(!(file_class_Details.elementAt(ele_id).toString().substring(5)).equals(" "))
            {
                cd_temp[cd_count] = new ClassDefinition();

                //Show Class Name

                cd_temp[cd_count].class_name = file_class_Details.elementAt(ele_id).toString().substring(5);
                //System.out.println(file_class_Details.elementAt(ele_id).toString().substring(5) );

                ele_id++;

                //Show Parent Class

                cd_temp[cd_count].parent_class = file_class_Details.elementAt(ele_id).toString().substring(5);
                //System.out.println(file_class_Details.elementAt(ele_id+1).toString().substring(5));

                ele_id++;

                while((file_class_Details.elementAt(ele_id).toString().contains("I - ")))
                {
                    cd_temp[cd_count].interf_names.add(file_class_Details.elementAt(ele_id).toString().substring(4));

                    ele_id++;
                }

                //Show Elements in interfaces

                //System.out.println(cd_temp[cd_count].interf_names.size());



                if((cd_temp[cd_count].parent_class).equals(""))
                {
                    cd_temp[cd_count].parent_class = file_class_Details.elementAt(ele_id).toString().substring(4);
                    //System.out.println(cd_temp[cd_count].parent_class );
                }


                cd_count++;
            }
    
    }
    
     public void meth_cat()
     {
         if(!(file_meth_Details.elementAt(0).toString().equals(" ")))
         {
                md[md_count] = new MethodDefinition();                
                md[md_count].method_name  = file_meth_Details.elementAt(0).toString();
                md[md_count].return_type  = file_meth_Details.elementAt(1).toString();
         }
     }
    
    public void read_file_contents()
    {
         try{

       
        FileReader fileToRead_c = new FileReader(file_path);       
        // Read file line by line and print on the console
        BufferedReader bufferReader_c = new BufferedReader(fileToRead_c);    
        
        while (bufferReader_c.readLine() != null) file_line_count++;
        
        bufferReader_c.close();
        
        
        
        FileReader fileToRead = new FileReader(file_path);
         //Instantiate the BufferedReader Class to read the file
        BufferedReader bufferReader = new BufferedReader(fileToRead);
        
        cd_temp = new ClassDefinition[file_line_count];
        md = new MethodDefinition[file_line_count];       
                
        while ((line = bufferReader.readLine()) != null)   
        {   
            
            check_comment_new();
                        
            tot_alpha_word_count+= getNumTokens();
          
            tot_literal_string_count+= getNumStrings();
            
            literal_strings.add(getStrings());
            
            //identify_variable("normal");
//            
              file_class_Details = getClassDefinitions();
              class_cat();
              
              //System.out.println(line);
              file_meth_Details= getMethodDefinitions();
//              try
//              {
//                meth_cat();
//              }
//              catch(Exception ex){}
            
              file_meth_Details.removeAllElements();
              file_class_Details.removeAllElements();
              line_count++;
        } 
        
//        cd = new ClassDefinition[cd_count];
        
//        for(int a=0;a<cd_count;a++)
//        {
//            cd[a]=cd_temp[a];
//        
//        }
        //Close the buffer reader
        bufferReader.close();   
        
        //Special Section START
        
        //Last Class Details
        cd_temp[cd_count-1].class_line=class_line_count+1;
        cd_temp[cd_count-1].max_class_nest_depth=max_meth_nest+1;
        //Last Method Details
        md[md_count-1].meth_line = meth_line_count+1;
        
        //Special Section END
        
        
        //Display final total count of Alphanumeric Tokens
        System.out.println("\nTotal count of Alphanumeric Tokens (without comments and literal strings) : " + (tot_alpha_word_count));
        //System.out.println("\nTotal count of Literal Strings : " + tot_literal_string_count);
        
        int count=0;
         for (int l=0;l<literal_strings.size();l++)
         {
                if(!(literal_strings.elementAt(l).toString().equals("[]")))
                {                     
                    count++;
                }
         }
        System.out.println("\nTotal count of Literal Strings : " + count);
        
       
        
        if(count>0)
        {
            System.out.println("\nThe list of Literal Strings : ");
        }
        else
        {
            System.out.println("\nNo Literal Strings to Display");
        }
        
        count=1;
        
        for (int l=0;l<literal_strings.size();l++)
        {
                if(!(literal_strings.elementAt(l).toString().equals("[]")))
                { 
                    System.out.println( String.valueOf(count) + ". \"" + literal_strings.elementAt(l)+"\"");
                    count++;
                }
        }
        
        System.out.println("\n*********************Variable Details**************************\n");
        
        System.out.println("Total int variable count " + getNumIntVariables());
        System.out.println("\nTotal boolean variable count " + getNumBooleanVariables());
        System.out.println("\nTotal char variable count " + getNumCharVariables());
        System.out.println("\nTotal double variable count " + getNumDoubleVariables());
        System.out.println("\nTotal float variable count " + getNumFloatVariables());
        System.out.println("\nTotal byte variable count " + getNumByteVariables());
        System.out.println("\nTotal short variable count " + getNumShortVariables());
        System.out.println("\nTotal long variable count " + getNumLongVariables());
        
        System.out.println("\n*********************Class Details*****************************\n");
        
        System.out.println("Total Class count " + cd_count);
        
        md_count=0;
        
        for(int a=0;a<cd_count;a++)
        {
            
            
            System.out.println("\n------------------------Class " + (a+1) + " Details------------------------");
            
            System.out.println("\nClass Name : " + cd_temp[a].class_name);
            
            System.out.println("Class Line Count : " + cd_temp[a].getNumberOfLines());
            System.out.println("Max Class Nest Depth : " + cd_temp[a].getMaximumNesting());
            
            if(cd_temp[a].getParentClassName().equals(" "))
                System.out.println("Parent Class : NULL");
            else
                System.out.println("Parent Class :" + cd_temp[a].getParentClassName());
            
            if((cd_temp[a].getInterfaceNames().size()==1) & (cd_temp[a].getInterfaceNames().elementAt(0).equals(" ")))
                System.out.println("Interface : NULL");
            else
            {
                System.out.println("Interface List");
                
                for(int z=0;z<cd_temp[a].getInterfaceNames().size();z++)
                {
                    System.out.println(" * " + cd_temp[a].getInterfaceNames().elementAt(z));
                }
            
            }
            
            try
            {
            if((cd_temp[a].getMethodDefinitions().size()==1) & (cd_temp[a].getMethodDefinitions().elementAt(0).equals(" ")))
                System.out.println("Methods : NULL");
            else
            {
                System.out.println("Total Method count : " + cd_temp[a].getMethodDefinitions().size());
                System.out.println("Method Details");    
                
                VariableDefinition vd_temp;
                
                
                
                
                for(int z=0;z<cd_temp[a].getMethodDefinitions().size();z++)
                {
                    
                    System.out.println(" * Method Name : " + cd_temp[a].getMethodDefinitions().elementAt(z) + " / Return Type : " + md[md_count].getReturnType());
                    try
                    {
                        System.out.println(" * Line Count  : " + md[md_count].getNumberOfLines());
                    }
                    catch(Exception ex)
                    {}
                    
                    try
                    {
                        System.out.println(" * Max Method Nest Depth : " + md[md_count].getMaximumNesting()+"\n");
                    }
                    catch(Exception ex)
                    {}
                    
                    if(md[md_count].getParameterDefinitions().size()==0)
                    {
                    System.out.print("      No Parametereised Variable Definitions\n");
                    }
                    
                    if(md[md_count].getLocalVariables().size()==0)
                    {
                    System.out.print("      No Primitive Variable Definitions\n");
                    }
                    
                    if((md[md_count].getParameterDefinitions().size()>0)|(md[md_count].getLocalVariables().size()>0))
                    {
                                        
                        
                        for(int p=0;p<md[md_count].getParameterDefinitions().size();p++)
                        {
                            if(p==0)
                            {
                                System.out.println("      Parameterized Variable List\n"); 
                            }
                            vd_temp = (VariableDefinition) md[md_count].getParameterDefinitions().elementAt(p);
                            System.out.print("      * Variable Type : " + vd_temp.var_type + " / Variable Name : " + vd_temp.var_name + "\n");
                        }
                        
                        
                        
                        for(int p1=0;p1<md[md_count].getLocalVariables().size();p1++)
                        {
                            if(p1==0)
                            {
                                System.out.println("\n      Primitive Variable List\n");
                            }
                            vd_temp = (VariableDefinition) md[md_count].getLocalVariables().elementAt(p1);
                            System.out.print("      * Variable Type : " + vd_temp.var_type + " / Variable Name : " + vd_temp.var_name+ "\n");
                        
                        }
                        
                        
                        
                        System.out.print("\n");
                    }
                    
                    
                    md_count++;
                    
                }
            }
            }
            catch(Exception ex)
            {
                System.out.println("Methods : NULL");
            }
        
        }
        
//        System.out.println("\n*********************Method Details*****************************\n");
//        
//        System.out.println("Total Method count " + md_count);
//        
//        //System.out.println("\nTotal Class count " + cd_count);
//        System.out.println("\n Method List \n");
//        
//        for(int p=0;p<md_count;p++)
//        {
//            System.out.println(" Method Name  :  " + md[p].getMethodName());
//            System.out.println(" Return Type  :  " + md[p].getReturnType() + "\n");
//            
//        }
        
        
        //System.out.println("\n***************************************************************\n");
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

