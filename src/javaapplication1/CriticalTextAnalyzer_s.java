package javaapplication1;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriticalTextAnalyzer_s
{
    int numToken;
    BufferedReader br;
    
    int c_flag=0;

    public CriticalTextAnalyzer_s(String file)
    {
        try
        {
            FileReader fr = new FileReader (file);
            br = new BufferedReader (fr);
        }
        catch (FileNotFoundException e)
        {
            System.out.println ("File Not Found!!!");
        }
        readFile();
    }
       
    public void readFile()
    {
        final String delims = ( " \t\n,;{}[]().-<>&^%$@!-+/*~=\"");
        String s, token;
        StringTokenizer splitter;
        try
        {
            while((s=br.readLine())!=null)      
            {
                s = (s.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)",""));
                
                System.out.println(s);
                
                s = s.replace("\\\"", "");
                
                s = (s.replaceAll("\"([^\"]*)\"", ""));
                
                s = (s.replaceAll("\'([^\"]*)\'", ""));
                
//                String pat="\"([^\"]*)\"";                
//                Pattern p = Pattern.compile(pat);
//                Matcher m = p.matcher(s);
//                while (m.find()) {
//                  System.out.println(m.group(1));
//                }
//                
//                pat="\'([^\"]*)\'";
//                p = Pattern.compile(pat);
//                m = p.matcher(s);
//                while (m.find()) {
//                  System.out.println(m.group(1));
//                }
                                
                splitter = new StringTokenizer(s, delims,true);
                while(splitter.hasMoreTokens())
                {
                    token = splitter.nextToken();
                    
                    if ((token.charAt(0)=='/') &(c_flag==0))
                    {
                        c_flag=1;
                        continue;
                    }
                    if ((token.charAt(0)=='*') &(c_flag==1))
                    {
                        c_flag=2;
                        continue;
                    }
                    if ((token.charAt(0)=='*') &(c_flag==2))
                    {
                        c_flag=3;
                        continue;
                    }
                    if ((token.charAt(0)=='/') &(c_flag==3))
                    {
                        c_flag=0;
                        continue;
                    }
                    
                    if (c_flag==2)
                        continue;
                    
                    if(!token.equals("_"))
                    {
                        char first = token.charAt(0);
                        if(Character.isLetter(first) || first == '_')
                        {
                            System.out.println(token);
                            this.numToken++;
                        }
                    }
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error Reading File!!!");
        }
    }

   public int getNumTokens()
    {
        return this.numToken;
    } 
    public static void main ( String[]args)
    {
        CriticalTextAnalyzer_s a = new CriticalTextAnalyzer_s("test.txt");
        int numToken = a.getNumTokens();
        System.out.println("The total number of alphanumeric tokens: "+numToken);
    }

}