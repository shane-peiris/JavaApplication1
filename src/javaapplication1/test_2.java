/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author Shane
 */

class student
{
    public String name="";
    public int age=0;
    
    public String returnName()
    {
    
        return name;
    }           
     
}

public class test_2 extends student{
    public static void main(String[] args)
    {
        student s1 = new student();
        
        s1.name = "shane";        
        
        System.out.println(s1.returnName());
        
        student s2 = new student();
        
        s2.name = "peiris";
        
        System.out.println(s2.returnName());
        
    }
    
}
