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
		//TODO Fix issue where left or right is null
		System.out.println("Parsing: " + str);
		if(str.length() == 0){
			return null;
		} else if (str.contains("+") && !inParens(str, "+")) { //TODO Check if in ()
			//E → A | X
			//A → A+M | M
			Expression rightExp = parseExpression(str.substring(0, str.indexOf("+")), parentExp);
			Expression leftExp = parseExpression(str.substring(str.indexOf("+") + 1), parentExp);
			if(leftExp == null || rightExp == null){
				return null;
			}
			return new AdditiveExpression(List.of(rightExp, leftExp), parentExp);
		} else if (str.contains("*") && !inParens(str, "*")) { //TODO Check if in ()
			//M := M*M | X
			Expression rightExp = parseExpression(str.substring(0, str.indexOf("*")), parentExp);
			Expression leftExp = parseExpression(str.substring(str.indexOf("*") + 1), parentExp);
			if(leftExp == null || rightExp == null){
				return null;
			}
			return new MultiplicationExpression(List.of(rightExp, leftExp), parentExp);
		} else if (str.contains("(") && str.contains(")") && correctParenOrder(str)) { //TODO Need to deal with when when ) is before (
			//X → (E) | L
			return parseExpression(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")")), parentExp);
		} else if (str.matches("^[0-9A-Za-z]*$")) {
			//L := [0-9]+ | [a-z]
			return new LiteralExpression(str, parentExp);
		}
		return null;
	}

	private boolean correctParenOrder(String str){
		return str.lastIndexOf(")") > str.indexOf("(");
	}

	private boolean inParens(String str, String symbol){
		if(str.contains("(") || str.contains(")")){
			int symbIndex = str.indexOf(symbol);
			return symbIndex > str.indexOf("(") && symbIndex < indexOfMatchingParen(str, str.indexOf("("));
		}else{
			return false;
		}
	}

	private int indexOfMatchingParen(String str, int parenIndex){
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
