import java.util.ArrayList;
import java.util.List;

public class ParentheticalExpression implements CompoundExpression {

    private final List<Expression> children;
    private CompoundExpression parent;

    ParentheticalExpression(final List<Expression> children, final CompoundExpression parent){
        this.children = children;
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
        //TODO Make sure we want an ArrayList
        //TODO Make sure this is correct
        List<Expression> newArr = new ArrayList<>();
        children.forEach(e -> newArr.add(e.deepCopy()));
        return new MultiplicationExpression(newArr, (CompoundExpression) parent.deepCopy());
    }

    @Override
    public void flatten() {
        //TODO Implement
    }

    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        stringBuilder.append("\t".repeat(indentLevel)).append("()").append("\n");
        children.forEach(e -> stringBuilder.append(e.convertToString(indentLevel + 1)));
    }

    @Override
    public void addSubexpression(Expression subexpression) {
        //TODO Make sure that this is correct
        children.add(subexpression);
    }
}
