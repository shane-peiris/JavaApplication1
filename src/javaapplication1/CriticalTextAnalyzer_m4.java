package javaapplication1;

/**
 *
 * @author Mali
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class ClassDefinition_1
{    
    public Vector intNames = new Vector<Object>(); 
    public String parentClaz="";
    public String className="";
    
    public String getParentClassName()
    {    
        return parentClaz;
    }
    
    public Vector getInterfaceNames()
    {    
        return intNames;
    }
}

public class CriticalTextAnalyzer_m4
{
    
    public static String line="";
    public static String filePath="";
    public static int fileLineCount=0;
    public static int totalAlphaWordCount=0;
    public static int tot_lit_strings=0;
    public int flag1=0;
    public int flag2=0;
    public int flag3=0;
    int intPrivTypes,booleanPrivTypes,charPrivTypes,bytePrivTypes,longPrivTypes,shortPrivTypes,doublePrivTypes,floatPrivTypes;
    public int countNum=1;
    public int itotVariable,btotVariable,bytotVariable,ctotVariable,dtotVariable,ftotVariable,ltotVariable,stotVariable;
    public static String[] dataTypes=new String[]{"int","boolean","char","double","float","byte","short","long"};
    public static int[] dataTypesCount=new int[]{0,0,0,0,0,0,0,0};
    public String prv;
    public String curr="";
    public String nxt="";
    public String temp="";
    public static int i=0;
    public static int classCount=0;
    public static Vector<String> vData=new Vector<String>();
    public static ClassDefinition_1[] cd;
    
    private CriticalTextAnalyzer_m4(String f_path) 
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
      
      //       public int getNumStrings()
//        {
//            String line1 = line;
//            String delims="\t\n,;{}[]().-<>&^%$@!-+\\/*'~\"= ";
//            StringTokenizer splitter = new StringTokenizer(line1,delims,true);
//            int numTokens=splitter.countTokens();
//            int literalStringCount = 0;
//            
//            
//            
//            for(int i=0;i<numTokens;i++)
//            {
//             String text=splitter.nextToken();
//            
//            if((flag1==0) & (flag2==0))
//            {
//                if ((text.charAt(0) == '\''))
//                {
//                    flag1=1;
//                }
//                else if((text.charAt(0) == '\"'))
//                {
//                    flag2=1;
//                }
//            }
//            else if(flag1 == 1)
//            {
//                if (!(text.charAt(0) == '\''))
//                {                   
//                    
//                }
//                else
//                {
//                    flag1 = 0;
//                    literalStringCount+=1;                    
//                   
//                }
//            }
//            else if(flag2 == 1)
//            {
//                if (!(text.charAt(0) == '\"'))
//                {
//                    if ((text.charAt(0) == '\\'))
//                    {
//                        flag3=1;
//                    }
//                    else
//                    {
//                        flag3=0;
//                    }
//                   
//                }
//                else
//                {
//                    if ((text.charAt(0) == '\"') & (flag3==1))
//                    {
//                        flag3=0;
//                        
//                        
//                    }                    
//                    flag2 = 0;
//                    literalStringCount+=1;                     
//                }
//            }                      
//        }           
//            return literalStringCount;     
//     }
      public void primitiveDataTYpes(String[] t)
      {
          for(int i=0;i<t.length;i++)
          { 
              for(int j=0;j<dataTypes.length;j++)
              {
                  if(t[i].equals(dataTypes[j]))
                  {
                      dataTypesCount[j] =  dataTypesCount[j] + 1;                    
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
        
        
         
         
         if(line.matches("(.*?)\\((.*?)\\)(.*?)"))
         {
                String newLine=line;
                newLine = newLine.replaceAll("\\(", "\\( ");
                
                if(line.matches("(.*?)for\\((.*?)\\)(.*?)"))
                {
                    
                    m1 = p1.matcher(newLine);
//                    System.out.println(newLine);  
                    while(m1.find())
                    {
                        
                        String[] tokens=m1.group(1).split("\\s+");   
                        primitiveDataTYpes(tokens);
                    }
//                    System.out.println(line);  
                }
                else
                {
                   newLine = newLine.replaceAll("\\)", ",\\)");
                    m = p.matcher(newLine);
                    
                    while(m.find())
                    {
                        //System.out.println(newLine); 
                        String[] tokens=m.group(1).split("\\s+");   
                        primitiveDataTYpes(tokens);
                    }
                
                }
                
                
                
         }
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
                           // System.out.println(m.group(0));
                            //System.out.println(""+countNum);       
                              } 
                            
                            dataTypesCount[j] =  dataTypesCount[j] + countNum; 
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
            
           itotVariable = dataTypesCount[0];
            return itotVariable;
        }

    
        public int getNumBooleanVariables() {
            btotVariable = dataTypesCount[1];
           return btotVariable;
    }

  
        public int getNumCharVariables() {
           ctotVariable = dataTypesCount[2];
           return ctotVariable;
    }

   
        public int getNumDoubleVariables() {
           dtotVariable = dataTypesCount[3];
           return dtotVariable;
    }

   
        public int getNumFloatVariables() {
           ftotVariable = dataTypesCount[4];
           return ftotVariable;
    }

   
        public int getNumByteVariables() {
           bytotVariable = dataTypesCount[5];
           return bytotVariable;
    }

   
        public int getNumShortVariables() {
           stotVariable = dataTypesCount[6];
           return stotVariable;
    }

   
        public int getNumLongVariables() {
           ltotVariable = dataTypesCount[7];
           return ltotVariable;
    }

      public Vector getClassDefinition()
      {
          nxt="";curr="";
          Vector<String> v=new Vector<String>();

        
          if((line.matches("(.*?)class(.*?)extends(.*?)implements(.*?)")))
          {
             classCount++;
              Pattern p = Pattern.compile(line);
              Matcher m = p.matcher(line);
              
              while(m.find())
              {
                   System.out.println(m.group(0));
                  StringTokenizer s = new StringTokenizer(line);
                  int numTokens=s.countTokens();
//                
//                    for(int i=0;i<numTokens;i++)
//                    {
                        while(!(curr.equals("extends")))
                        {
                        prv=curr;
                        //System.out.println(numTokens);
                        curr=s.nextToken();
                        //System.out.println("b4 "+nxt);
                        //System.out.println("prv val"+prv);
                     
                        }
                       // curr=splitter.nextToken();
                        if(curr.equals("extends"))
                        {   
                            temp=prv;
//                            System.out.println("prv val"+temp);
                            curr=s.nextToken();
//                        System.out.println("after extend  class :"+curr);
                        }
                        
                         while(!(curr.equals("implements")))
                        {
                            prv=curr;
                        //System.out.println(numTokens);
                            curr=s.nextToken();
                        //System.out.println("b4 "+nxt);
                        //System.out.println("prv val"+prv);
                     
                        }
                           if(curr.equals("implements"))
                        {   
                           
                            curr=s.nextToken();
//                        System.out.println("after extend  class :"+curr);
                        }
              
              }
             
//             System.out.println("after extend "+temp);
//             System.out.println("after extend "+prv);
//             System.out.println("after extend "+curr);
//               
              v.add(temp);
              v.add(prv);
              v.add(curr);
             // curr=null;
             // v.add(nxt);                
                
              
          }
          else if((line.matches("(.*?)class(.*?)extends(.*?)")))
          {
              classCount++;
              Pattern p = Pattern.compile(line);
              Matcher m = p.matcher(line);
              
              while(m.find())
              {
                   System.out.println(m.group(0));
                  StringTokenizer splitter = new StringTokenizer(line);
                  int numTokens=splitter.countTokens();
//                
//                    for(int i=0;i<numTokens;i++)
//                    {
                        while(!(curr.equals("extends")))
                        {
                        prv=curr;
                        //System.out.println(numTokens);
                        curr=splitter.nextToken();
                       // System.out.println("b4 "+curr);
                      //  System.out.println("prv val"+prv);
                     
                        }
                       // curr=splitter.nextToken();
                        if(curr.equals("extends"))
                        {
                            curr=splitter.nextToken();
                       // System.out.println("after extend "+curr);
                        }
              
              }
              nxt=null;
//              System.out.println("after extend "+prv);
//               System.out.println("after extend "+curr);
//              System.out.println("after extend "+nxt);
              v.add(prv);
              v.add(curr);
              v.add(nxt);                
                
               
          }  
          else if((line.matches("(.*?)class(.*?)implements(.*?)")))
          {
             classCount++;
              Pattern p = Pattern.compile(line);
              Matcher m = p.matcher(line);
              
              while(m.find())
              {
                   System.out.println(m.group(0));
                  StringTokenizer s = new StringTokenizer(line);
                  int numTokens=s.countTokens();
//                
//                    for(int i=0;i<numTokens;i++)
//                    {
                        while(!(nxt.equals("implements")))
                        {
                        prv=nxt;
                        //System.out.println(numTokens);
                        nxt=s.nextToken();
                        //System.out.println("b4 "+nxt);
                        //System.out.println("prv val"+prv);
                     
                        }
                       // curr=splitter.nextToken();
                        if(nxt.equals("implements"))
                        {
                            nxt=s.nextToken();
                       // System.out.println("after extend "+curr);
                        }
              
              }
             curr=null;
             // System.out.println("after extend "+prv);
             // System.out.println("after extend "+curr);
             // System.out.println("after extend "+nxt);
              v.add(prv);
              v.add(curr);
              v.add(nxt);
             // v.add(nxt);                
                
             
          }
          else if((line.matches("(.*?)class(.*?)")))
          {
              classCount++;
              curr="";
              Pattern p = Pattern.compile(line);
              Matcher m = p.matcher(line);
              
              while(m.find())
              {
                   System.out.println(m.group(0));
                  StringTokenizer s = new StringTokenizer(line);
                  int numTokens=s.countTokens();
//                
//                    for(int i=0;i<numTokens;i++)
//                    {
                        while(!(curr.equals("class")))
                        {
                        prv=curr;
                        //System.out.println(numTokens);
                        curr=s.nextToken();
                        //System.out.println("b4 "+nxt);
                        //System.out.println("prv val"+prv);
                     
                        }
                       // curr=splitter.nextToken();
                        if(curr.equals("class"))
                        {
                            curr=s.nextToken();
                       // System.out.println("after extend "+curr);
                        }
              
              }
             prv=null;nxt=null;
             // System.out.println("after extend "+prv);
             // System.out.println("after extend "+curr);
             // System.out.println("after extend "+nxt);
              
              v.add(curr);
              v.add(prv);
              v.add(nxt);     
             
          }

          return v;
          
      }
    
    public static void main(String[] args)
    {
        String path="test.txt";
        FileReader fRead = null;
        FileReader fileRead=null;
        try {
              // System.out.print("Please enter file name in the current directory : ");
               
               // path = System.console().readLine();
               // ClassDefinition[] cd=new ClassDefinition[30];
                CriticalTextAnalyzer_m4 cta=new CriticalTextAnalyzer_m4(path);
                //criticalTextAnalyzer ca=new criticalTextAnalyzer();
                fileRead = new FileReader(filePath);
                BufferedReader bufferRead = new BufferedReader(fileRead);        
                while ((bufferRead.readLine()) != null)   
                    {
                        fileLineCount++;
                    }
                System.out.println("Number of lines :"+fileLineCount);
                bufferRead.close();
               
                
                fRead = new FileReader(filePath);
                BufferedReader bufRead = new BufferedReader(fRead);
                cd = new ClassDefinition_1[fileLineCount];
                 
                while ((line = bufRead.readLine()) != null)   
                    {
                     vData=cta.getClassDefinition();
                          //totalAlphaWordCount+= cta.ignoreComments();
//                         tot_lit_strings+= cta.getNumStrings();
                         //cta.countVariables();
                         // System.out.println("size :"+vData.size());
                          while (i<vData.size()-1)
                          {
                              try
                              {
                              //System.out.println("Class :"+vData.elementAt(i));
                              cd[classCount-1]=new ClassDefinition_1();
                              cd[classCount-1].className = vData.elementAt(i);
//                              System.out.println("size :"+i);
                              i++;
                              //System.out.println("parent Class :"+vData.elementAt(i));
                              cd[classCount-1].parentClaz = vData.elementAt(i);
//                              System.out.println("size :"+i);
                              i++;
                              //System.out.println("parent Class :"+vData.elementAt(i));
                              cd[classCount-1].intNames.add(vData.elementAt(i));
//                              System.out.println("size :"+i);
                              }
                              catch(Exception ex)
                              {}
                                    
                          }
                
                        i=0;     
                    }
                
                
                System.out.println("\nTotal Number Of classes :"+classCount + "\n");
                
                for(int c=0;c<classCount;c++)
                {
                    System.out.println("Class :"+cd[c].className);
                    System.out.println("parent Class :"+cd[c].getParentClassName());
                    System.out.println("Interface Name :"+cd[c].getInterfaceNames().elementAt(0)+"\n");
                    
                }
                
                
                bufRead.close();
                 //System.out.println("Total Alphanumeric Tokens :"+totalAlphaWordCount);
//                System.out.println("Total number of int variables: " + cta.getNumIntVariables());
//                System.out.println("Total number of char variables: " + cta.getNumCharVariables());
//                System.out.println("Total number of byte variables: " + cta.getNumByteVariables());
//                System.out.println("Total number of short variables: " + cta.getNumShortVariables());
//                System.out.println("Total number of long variables: " + cta.getNumLongVariables());
//                System.out.println("Total number of double variables: " +cta.getNumDoubleVariables());
//                System.out.println("Total number of float variables: " + cta.getNumFloatVariables());
//                System.out.println("Total number of boolean variables: " + cta.getNumBooleanVariables());
                
               
                
            }catch (Exception ex) 
            {   
                System.out.println("Unable to read line..!!" + ex.getMessage()+"\n");          
            }      
       }  
}

