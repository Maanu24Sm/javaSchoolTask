package com.javaSchool.task;

/**
 * 
 *
 */
public class App 
{
	public static void main(String[] args) {
        calculator c = new calculator();
        
        String expression1 = "7/3";
        String expression2 = "7*6/2+8";
        String expression3 = "-12)1//(";
        
        String result1 = c.evaluate(expression1);
        String result2 = c.evaluate(expression2);
        String result3 = c.evaluate(expression3);
        
        System.out.println("Resultado 1: " + result1);
        System.out.println("Resultado 2: " + result2);
        System.out.println("Resultado 3: " + result3);
    }
}
