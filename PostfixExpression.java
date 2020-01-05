package cs2321;

public class PostfixExpression {
	
	/**
	 * 
	 * @author SiddheshDM
	 * Date last modified: Sept 24, 2019
	 * 
	 * Takes a postfix expression and outputs the actual numerical result
	 * 
	 */
	
	/**
	 * Evaluate a postfix expression. 
	 * Postfix expression notation has operands first, following by the operations.
	 * For example:
	 *    13 5 *           is same as 13 * 5 
	 *    4 20 5 + * 6 -   is same as 4 * (20 + 5) - 6  
	 *    
	 * In this homework, expression in the argument only contains
	 *     integer, +, -, *, / and a space between every number and operation. 
	 * You may assume the result will be integer as well. 
	 * 
	 * @param exp The postfix expression
	 * @return the result of the expression
	 */
	public static int evaluate(String exp) {
		DLLStack <Integer> operandStorage = new DLLStack<Integer>();
		
		String [] splitExpression = exp.split(" ");
		String divide = "/";
		String multiply = "*";
		String add = "+";
		String subtract = "-";
		
		for (int i = 0; i < splitExpression.length; i++){
			
			if (splitExpression[i].equals(divide)) {
				int int2 = operandStorage.pop();
				int int1 = operandStorage.pop();
				operandStorage.push(int1 / int2);
				continue;
			}
			
			else if (splitExpression[i].equals(multiply)) {
				int int2 = operandStorage.pop();
				int int1 = operandStorage.pop();
				operandStorage.push(int1 * int2);
				continue;
			}
			
			else if (splitExpression[i].equals(add)) {
				int int2 = operandStorage.pop();
				int int1 = operandStorage.pop();
				operandStorage.push(int1 + int2);
				continue;
			}
			
			else if (splitExpression[i].equals(subtract)) {
				int int2 = operandStorage.pop();
				int int1 = operandStorage.pop();
				int int3 = int1-int2;
				operandStorage.push(int3);
				
			}
			
			int a = Integer.parseInt(splitExpression[i]);
			operandStorage.push(a);			
		}
		
		return operandStorage.pop();
	}
	
	public static void main (String [] args) {
		System.out.print(evaluate("5 3 * 3 2 / +"));
	}
				
	
}
