package cs2321;

/**
 * 
 * @author SiddheshDM
 * Date last modified: Sept 24, 2019
 * 
 * The Infix to Postfix program which converts a given infix operation into postfix
 * 
 */

public class InfixToPostfix {
	/* Convert an infix expression and to a temp expression
	 * infix expression : operator is between operands. Ex. 3 + 5
	 * temp Expression: operator is after the operands. Ex. 3 5 +
	 * 
	 * The infixExp expression includes the following
	 *      operands:  integer or decimal numbers 
	 *      and operators: + , - , * , /
	 *      and parenthesis: ( , )
	 *      
	 *      For easy parsing the expression, there is a space between operands and operators, parenthesis. 
	 *  	Ex: "1 * ( 3 + 5 )"
	 *      Notice there is no space before the first operand and after the last operand/parentheses. 
	 *  
	 * The postExp includes the following 
	 *      operands:  integer or decimal numbers 
	 *      and operators: + , - , * , /
	 *      
	 *      For easy parsing the expression, there should have a space between operands and operators.
	 *      Ex: "1 3 5 + *"
	 *      Notice there is space before the first operand and last operator. 
	 *      Notice that postExp does not have parenthesis. 
	 */


	public static int precedence(String first, String cur) { 

		if(cur.equals("*") || cur.equals("/")) 
		{
			if(first.equals("*") || first.equals("/")) 
			{
				return 0;
			}
			return 1;
		}
		else 
		{
			if(first.equals("*") || first.equals("/")) {
				return -1;
			}
			else 
			{
				return 1;
			}
		}
	}


	public static String convert(String infixExp) {
		String [] chars = infixExp.split(" ");
		String temp1 = "";
		DLLStack<String> result = new DLLStack<String>();


		for(int i = 0; i < chars.length; i++) { 
			try {  								
				int temp = Integer.parseInt(chars[i]);
				if(temp1.length()<1)
					temp1 += temp;
				else {
					temp1+= " " + temp;
				}
			}
			catch(Exception e) {
				if(chars[i].equals("(")) 
				{
					result.push(")");
					continue;
				}
				if(chars[i].equals(")")) {
					while( result.top() != ")" ) 
					{
						temp1 += " " + result.pop();
					}
					result.pop();
					continue;
				}
				if(chars[i].equals("+") || chars[i].equals("-")  
						|| chars[i].equals("/") || chars[i].equals("*")) 
				{
					while( !result.isEmpty() && precedence(result.top(), chars[i])>0) 
					{
						temp1 += " " + result.pop();
					}
					result.push(chars[i]);

				}

			}

		}
		while(result.size() != 0) 
		{
			temp1 += " " + result.pop();

		}
		return temp1.trim();
	}




}