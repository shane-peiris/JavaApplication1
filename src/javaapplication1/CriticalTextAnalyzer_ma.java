package javaapplication1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class CriticalTextAnalyzer_ma
{
    public static String line="";
    public static String filePath="";
    public static int totalAlphaWordCount=0;
    public static int tot_lit_strings=0;
    public int flag1=0;
    public int flag2=0;
    public int flag3=0;
    int intPrivTypes,booleanPrivTypes,charPrivTypes,bytePrivTypes,longPrivTypes,shortPrivTypes,doublePrivTypes,floatPrivTypes;
    public int countNum=1;
    public int itotVariable,btotVariable,bytotVariable,ctotVariable,dtotVariable,ftotVariable,ltotVariable,stotVariable;
    public static String[] dataTypes=new String[]{"int","char","byte","short","long","double","float","boolean"};
    public static int[] dataTypes_count=new int[]{0,0,0,0,0,0,0,0};
    
    private CriticalTextAnalyzer_ma(String f_path) 
    {
        filePath=f_path;
    }
      
      public int ignoreComments()
      {
        String line2="";
        line2 = line.replace("\\\"", "");
        line2 = (line2.replaceAll("\"([^\"]*)\"", ""));
        line2 = (line2.replaceAll("\'([^\"]*)\'", ""));
        String delims="\t\n,;{}[]().-<>&^%$@!-+/*~= ";
        StringTokenizer splitter = new StringTokenizer(line,delims,true);
        int numTokens=splitter.countTokens();
        
        int alphaNumWordCount=0;
        int c_comment_flag=0;
        int s_comment_flag=0;         
     	for(int i=0;i<numTokens;i++)
        {
           
             String text=splitter.nextToken();
           //prvToken=curToken;
             
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
                 }   
            }       
        }
       
       return alphaNumWordCount;  
      }
      
      public void primitiveDataTYpes(String[] t)
      {
          for(int i=0;i<t.length;i++)
          { 
              for(int j=0;j<dataTypes.length;j++)
              {
                  if(t[i].equals(dataTypes[j]))
                  {
                      dataTypes_count[j] =  dataTypes_count[j] + 1;                    
                  }              
              }
          }    
      }
      
     public  void countVariables()
     {
         String pat="(.*?),"; 
         String pat1="(.*?);";  
         Pattern p = Pattern.compile(pat);
         Pattern p1 = Pattern.compile(pat1);
         Matcher m = p.matcher(line);
         Matcher m1 = p1.matcher(line);
            
         if((line.contains(",")))
         {
              while(m1.find())
              {
                 String[] tokens=m1.group(1).split("\\s+"); 
                 
                 for(int i=0;i<tokens.length;i++)
                 { 
                    for(int j=0;j<dataTypes.length;j++)
                    {
                        if(tokens[i].equals(dataTypes[j]))
                        {
                            while (m.find()) {
                                countNum++;      
                            } 
                            dataTypes_count[j] =  dataTypes_count[j] + countNum; 
                            break;
                        }              
                    }
                 }  
                 countNum=1; 
               }
         }
         else
         {
            while(m1.find())
            {
                String[] tokens=m1.group(1).split("\\s+");
                primitiveDataTYpes(tokens);

            }
         }
              
         countNum=1;   
         
     }
     
       public int getNumIntVariables(){
                                      
            return itotVariable;
        }

    
        public int getNumBooleanVariables() {
 
           return btotVariable;
    }

  
        public int getNumCharVariables() {
     
           return ctotVariable;
    }

   
        public int getNumDoubleVariables() {
     
           return dtotVariable;
    }

   
        public int getNumFloatVariables() {
    
           return ftotVariable;
    }

   
        public int getNumByteVariables() {
       
           return bytotVariable;
    }

   
        public int getNumShortVariables() {
      
           return stotVariable;
    }

   
        public int getNumLongVariables() {
  
           return ltotVariable;
    }

        public int getNumStrings()
        {
            String line1 = line;
            String delims="\t\n,;{}[]().-<>&^%$@!-+\\/*'~\"= ";
            StringTokenizer splitter = new StringTokenizer(line1,delims,true);
            int numTokens=splitter.countTokens();
            int literalStringCount = 0;
            
            
            
            for(int i=0;i<numTokens;i++)
            {
             String text=splitter.nextToken();
            
            if((flag1==0) & (flag2==0))
            {
                if ((text.charAt(0) == '\''))
                {
                    flag1=1;
                }
                else if((text.charAt(0) == '\"'))
                {
                    flag2=1;
                }
            }
            else if(flag1 == 1)
            {
                if (!(text.charAt(0) == '\''))
                {                   
                    
                }
                else
                {
                    flag1 = 0;
                    literalStringCount+=1;                    
                   
                }
            }
            else if(flag2 == 1)
            {
                if (!(text.charAt(0) == '\"'))
                {
                    if ((text.charAt(0) == '\\'))
                    {
                        flag3=1;
                    }
                    else
                    {
                        flag3=0;
                    }
                   
                }
                else
                {
                    if ((text.charAt(0) == '\"') & (flag3==1))
                    {
                        flag3=0;
                        
                        
                    }                    
                    flag2 = 0;
                    literalStringCount+=1;                     
                }
            }                      
        }           
            return literalStringCount;     
     }


        
    public static void main(String[] args)
    {      
        String path="sample.txt";
        FileReader fRead = null;
        try {
                //System.out.print("Please enter file name in the current directory : ");
               
                //path = System.console().readLine();
                CriticalTextAnalyzer_ma cta=new CriticalTextAnalyzer_ma(path);
                //criticalTextAnalyzer ca=new criticalTextAnalyzer();
                fRead = new FileReader(filePath);
                BufferedReader bufRead = new BufferedReader(fRead);        
                while ((line = bufRead.readLine()) != null)   
                    {
                         
                         totalAlphaWordCount+= cta.ignoreComments();
                         tot_lit_strings+= cta.getNumStrings();
                         cta.countVariables();
                             
                    }
                bufRead.close();
                System.out.println("Total Alphanumeric Tokens :"+totalAlphaWordCount);
                System.out.println("Total Literal Strings :"+tot_lit_strings);
                System.out.println("Total number of int variables: " + dataTypes_count[0]);
                System.out.println("Total number of char variables: " + dataTypes_count[1]);
                System.out.println("Total number of byte variables: " + dataTypes_count[2]);
                System.out.println("Total number of short variables: " + dataTypes_count[3]);
                System.out.println("Total number of long variables: " + dataTypes_count[4]);
                System.out.println("Total number of double variables: " + dataTypes_count[5]);
                System.out.println("Total number of float variables: " + dataTypes_count[6]);
                System.out.println("Total number of boolean variables: " + dataTypes_count[7]);

                
                
            }catch (Exception ex) 
            {   
                System.out.println("Unable to read line..!!" + ex.getMessage()+"\n");          
            }      
       }  
}