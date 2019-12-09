public abstract class AbstractExpression implements Expression {

    private CompoundExpression parent;
    private final String symbol;

    /**
     * An abstract implementation of Expression
     * @param parent The parent of the expression
     * @param symbol The symbol that represents the expression
     */
    AbstractExpression(final CompoundExpression parent, final String symbol){
        this.parent = parent;
        this.symbol = symbol;
    }

    /**
     * Gets the parent of the Additive expression
     * @return the parent
     */
    @Override
    public CompoundExpression getParent() {
        return parent;
    }

    /**
     * Sets the parent to the target object
     * @param parent the CompoundExpression that should be the parent of the target object
     */
    @Override
    public void setParent(CompoundExpression parent) {
        this.parent = parent;
    }

    /**
     * Builds a StringBuilder of the expressions
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        //Add ourselves to the StringBuilder
        stringBuilder.append("\t".repeat(indentLevel)).append(symbol).append("\n");
    }
}
