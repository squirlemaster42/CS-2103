import java.util.ArrayList;

/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {
	/**
	 * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
	 * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * @param str the string to parse into an expression tree
	 * @param withJavaFXControls you can just ignore this variable for R1
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}

	/**
	 * Converts a String into a Tree representation of the expression
	 * @param str The String to parse
	 * @return A tree that represents the expression
	 */
	protected Expression parseExpression (String str) {
		//Start parsing the Expression. The parent of the head of the Tree is null
		return parseExpression(str, null);
	}

	/**
	 * Converts a String into a Tree representation of the expression
	 * @param str The String to parse
	 * @param parentExp The parent of the subExpression currently being parsed
	 * @return A tree that represents the expression
	 */
	private Expression parseExpression(String str, CompoundExpression parentExp) {
		//Check that the String is not length 0
		if(str.length() == 0){
			return null;
		} else if (firstSymbolNotInParens(str, '+') != -1) {
			//Parse "+"
			//E → A | X
			//A → A+M | M
			final int index = firstSymbolNotInParens(str, '+');
			final CompoundExpression returnExp = new AdditiveExpression(new ArrayList<>(), parentExp);
			return genExpression(str, index, returnExp);
		} else if (firstSymbolNotInParens(str, '*') != -1) {
			//Parse "*"
			//M := M*M | X
			final int index = firstSymbolNotInParens(str, '*');
			final CompoundExpression returnExp = new MultiplicationExpression(new ArrayList<>(), parentExp);
			return genExpression(str, index, returnExp);
		} else if (str.contains("(") && str.contains(")") && correctParenOrder(str)) {
			//Parse parenthesis
			//X → (E) | L
			final CompoundExpression returnExp = new ParentheticalExpression(new ArrayList<>(), parentExp);
			final Expression expression = parseExpression(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")")), returnExp);
			if(expression == null){
				return null;
			}
			returnExp.addSubexpression(expression);
			return returnExp;
		} else if (str.matches("^[0-9A-Za-z]*$")) { //Make sure that we only have numbers and letters
			//Parse number and letters
			//L := [0-9]+ | [a-z]
			return new LiteralExpression(str, parentExp);
		}
		//We did not parse the Expression because it was invalid
		return null;
	}

	/**
	 * Generates an expression that will have two children (Addition or Multiplication)
	 * @param str The String to parse
	 * @param index The index of the symbol to parse
	 * @param returnExp An instance of the expression to return
	 * @return A CompoundExpression with two children
	 */
	private CompoundExpression genExpression(final String str, final int index, final CompoundExpression returnExp){
		//The Expression that represents the right half of the String
		Expression rightExp = parseExpression(str.substring(0, index), returnExp);
		//The Expression that represents the left half of the String
		Expression leftExp = parseExpression(str.substring(index + 1), returnExp);
		//If either sub expression is null return null
		if(leftExp == null || rightExp == null){
			return null;
		}
		//Add the sub expressions
		returnExp.addSubexpression(rightExp);
		returnExp.addSubexpression(leftExp);
		return returnExp;
	}

	/**
	 * Finds the first instance of symbol that is not contained in parenthesis
	 * @param str The String to search
	 * @param symbol The symbol to look for
	 * @return The position of the symbol (-1 if not found or all symbols are in parenthesis)
	 */
	private int firstSymbolNotInParens(final String str, final char symbol){
		for(int i = 0; i < str.length(); i++){
			//We found the symbol and it is not in parenthesis
			if(str.charAt(i) == symbol && !inParens(str, i)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Determines if the first and last parenthesis in a String are in the correct order
	 * @param str The String to check
	 * @return True if the parenthesis are in the correct order, False otherwise
	 */
	private boolean correctParenOrder(final String str){
		return str.lastIndexOf(")") > str.indexOf("(");
	}

	/**
	 * Checks if symbolIndex in contained in parenthesis
	 * @param str The String to check
	 * @param symbolIndex The index of the symbol to check
	 * @return True if symbolIndex is in parenthesis, False otherwise
	 */
	private boolean inParens(final String str, final int symbolIndex){
		boolean inParens;
		//Check that the String contains parens
		if(str.contains("(") || str.contains(")")){
			for(int i = 0; i < str.length(); i++){
				//We found an opening paren
				if(str.charAt(i) == '('){
					//Find the index of the matching parenthesis
					final int matchingParen = indexOfMatchingParen(str, i);
					//Check that symbolIndex is contained withing the parenthesis
					inParens = matchingParen > symbolIndex && symbolIndex > i;
					//Only return true if we find it contained in the parenthesis
					//Otherwise, we need to check the rest of the String
					if(inParens){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Find the index of the parenthesis matching parenIndex
	 * @param str The String to check
	 * @param parenIndex the index of the ( to check
	 * @return The position of the closing parenthesis that matches the parenthesis at parenIndex
	 */
	private int indexOfMatchingParen(final String str, final int parenIndex){
		int closeParensToFind = 1;
		for(int i = parenIndex + 1; i < str.length(); i++){
					if(str.charAt(i) == ')'){
						//We found a close paren
						closeParensToFind--;
					}else if(str.charAt(i) == '('){
						//We found an open paren
						closeParensToFind++;
					}

					//All parens have been closed
					if(closeParensToFind == 0){
						return i;
			}
		}
		//We did not find a closing paren that matches the paren at parenIndex
		return -1;
	}
}
