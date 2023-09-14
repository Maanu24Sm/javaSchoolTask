package com.javaSchool.task;

import java.util.Stack;

public class calculator {

    public String evaluate(String statement) {
    	String result = evaluateExpression(statement);
        if (result != null) {
            return result.toString();
        } else {
            return null;
        }
    }

    private String evaluateExpression(String expression) {
        try {
            // Check if the expression contains a comma
            if (expression.contains(",")) {
                return null;
            }
            
            Stack<Double> numbers = new Stack<>();
            Stack<Character> operators = new Stack<>();
            int i = 0;
            while (i < expression.length()) {
                char c = expression.charAt(i);
                if (Character.isDigit(c) || c == '.') {
                    StringBuilder numBuilder = new StringBuilder();
                    while (i < expression.length() &&
                            (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        numBuilder.append(expression.charAt(i));
                        i++;
                    }
                    double number = Double.parseDouble(numBuilder.toString());
                    numbers.push(number);
                } else if (c == '(') {
                    operators.push(c);
                    i++;
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                    }
                    if (!operators.isEmpty() && operators.peek() == '(') {
                        operators.pop(); // Pop the '('
                    } else {
                        return null; // Error: Mismatched parentheses
                    }
                    i++;
                } else if (isOperator(c)) {
                    while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                        numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                    }
                    operators.push(c);
                    i++;
                } else {
                    i++; // Ignore other characters
                }
            }
            while (!operators.isEmpty()) {
                if (operators.peek() == '(') {
                    return null; // Error: Mismatched parentheses
                }
                numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
            }
            if (!numbers.isEmpty()) {
                double result = numbers.pop();
                if (Math.floor(result) == result) {
                    // If the result is an integer, return it as an integer
                    return Integer.toString((int) result);
                } else {
                    // If the result has decimals, format it and remove trailing zeros
                    return formatAndRemoveTrailingZeros(result);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }



    private Double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b != 0) {
                    return a / b;
                } else {
                    return null; // Error: Division by zero
                }
            default:
                return null; // Error: Invalid operator
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    private String formatAndRemoveTrailingZeros(double value) {
        // Round to a maximum of 4 decimal places
        double roundedValue = Math.round(value * 10000.0) / 10000.0;
        // Format the result as a string with 4 decimals and remove trailing zeros
        String formattedValue = String.format("%.4f", roundedValue).replace(",", ".");
        
        // Remove trailing zeros after the last decimal place if any
        while (formattedValue.endsWith("0")) {
            formattedValue = formattedValue.substring(0, formattedValue.length() - 1);
        }
        
        // If the last character is a period, remove it as well
        if (formattedValue.endsWith(".")) {
            formattedValue = formattedValue.substring(0, formattedValue.length() - 1);
        }
        
        return formattedValue;
    }
}