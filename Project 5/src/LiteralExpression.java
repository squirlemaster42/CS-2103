public class LiteralExpression extends AbstractExpression implements Expression{

    private final String value;

    /**
     * Creates an Literal of Additive Expression
     * @param parent parent of the additive expression
     */
    LiteralExpression (final String value, final CompoundExpression parent){
        super(parent, value);
        this.value = value;
    }

    /**
     * Creates a deep copy of the expression
     * @return the deep copy that was created
     */
    @Override
    public Expression deepCopy() {
        return new LiteralExpression(value, null);
    }

    /**
     * Combines common symbols to simplify the tree
     */
    @Override
    public void flatten() {
        //Literal expressions are not flattened
    }
}
