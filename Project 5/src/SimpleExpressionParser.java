import java.util.ArrayList;
import java.util.List;

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
	
	protected Expression parseExpression (String str) {
		return parseExpression(str, null);
	}

	private Expression parseExpression(String str, CompoundExpression parentExp) {
		//TODO Parent Expressions are not being set correctly
		if(str.length() == 0){
			return null;
		} else if (firstSymbolNotInParens(str, '+') != -1) {
			//E → A | X
			//A → A+M | M
			CompoundExpression returnExp = new AdditiveExpression(new ArrayList<>(), parentExp);
			System.out.println(str.substring(0, str.indexOf("+")) + " and " + str.substring(str.indexOf("+") + 1));
			Expression rightExp = parseExpression(str.substring(0, str.indexOf("+")), returnExp);
			Expression leftExp = parseExpression(str.substring(str.indexOf("+") + 1), returnExp);
			if(leftExp == null || rightExp == null){
				return null;
			}
			returnExp.addSubexpression(rightExp);
			returnExp.addSubexpression(leftExp);
			return returnExp;
		} else if (firstSymbolNotInParens(str, '*') != -1) {
			//M := M*M | X
			System.out.println(str.substring(0, str.indexOf("*")) + " and " + str.substring(str.indexOf("*") + 1));
			CompoundExpression returnExp = new MultiplicationExpression(new ArrayList<>(), parentExp);
			Expression rightExp = parseExpression(str.substring(0, str.indexOf("*")), returnExp);
			Expression leftExp = parseExpression(str.substring(str.indexOf("*") + 1), returnExp);
			if(leftExp == null || rightExp == null){
				return null;
			}
			returnExp.addSubexpression(rightExp);
			returnExp.addSubexpression(leftExp);
			return returnExp;
		} else if (str.contains("(") && str.contains(")") && correctParenOrder(str)) { //TODO Need to deal with when when ) is before (
			//X → (E) | L
			System.out.println(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")")));
			CompoundExpression returnExp = new ParentheticalExpression(new ArrayList<>(), parentExp);
			Expression expression = parseExpression(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")")), returnExp);
			if(expression == null){
				return null;
			}
			returnExp.addSubexpression(expression);
			return returnExp;
		} else if (str.matches("^[0-9A-Za-z]*$")) {
			//L := [0-9]+ | [a-z]
			//System.out.println(str);
			return new LiteralExpression(str, parentExp);
		}
		return null;
	}

	private int firstSymbolNotInParens(final String str, final char symbol){
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == symbol && !inParens(str, i)){
				return i;
			}
		}
		return -1;
	}

	private boolean correctParenOrder(final String str){
		return str.lastIndexOf(")") > str.indexOf("(");
	}

	private boolean inParens(final String str, final int symbolIndex){
		final char symbol = str.charAt(symbolIndex);
		if(str.contains("(") || str.contains(")")){
			return symbolIndex > str.indexOf("(") && symbolIndex < indexOfMatchingParen(str, str.indexOf("("));
		}else{
			return false;
		}
	}

	private int indexOfMatchingParen(final String str, final int parenIndex){
		int closeParensToFind = 1;
		for(int i = parenIndex + 1; i < str.length(); i++){
			if(str.charAt(i) == ')'){
				closeParensToFind--;
			}else if(str.charAt(i) == '('){
				closeParensToFind++;
			}

			if(closeParensToFind == 0){
				return i;
			}
		}
		return -1;
	}
}
