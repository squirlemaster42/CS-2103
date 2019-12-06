public class LiteralExpression implements Expression{

    //TODO Figure out if this should be final
    //TODO Check deepCopy for all
    //TODO Check single literal
    private final String value;
    private CompoundExpression parent;

    LiteralExpression (final String value, final CompoundExpression parent){
        this.value = value;
        this.parent = parent;
    }

    @Override
    public CompoundExpression getParent() {
        return parent;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        this.parent = parent;
    }

    @Override
    public Expression deepCopy() {
        return new LiteralExpression(value, (CompoundExpression) parent.deepCopy());
    }

    @Override
    public void flatten() {}

    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        stringBuilder.append("\t".repeat(indentLevel)).append(value).append("\n");
    }

    public String getValue() {
        return value;
    }
}
