import java.util.List;

public abstract class AbstractCompoundExpression extends AbstractExpression implements CompoundExpression {

    private final List<Expression> children;
    public CompoundExpression parent;

    /**
     * An abstract implementation of CompoundExpression
     * @param children The children of the expression
     * @param parent The parent of the expression
     * @param symbol The symbol that represents the expression
     */
    AbstractCompoundExpression(List<Expression> children, CompoundExpression parent, final String symbol){
        super(parent, symbol);
        this.children = children;
    }

    /**
     * Returns the children of the expression
     * @return The children of the expression
     */
    List<Expression> getChildren(){
        return children;
    }

    /**
     * Builds a StringBuilder of the expressions
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        super.convertToString(stringBuilder, indentLevel);
        children.forEach(e -> stringBuilder.append(e.convertToString(indentLevel + 1)));
    }

    /**
     * Adds child expression
     * @param subexpression the child expression to add
     */
    @Override
    public void addSubexpression(Expression subexpression) {
        children.add(subexpression);
    }
}
