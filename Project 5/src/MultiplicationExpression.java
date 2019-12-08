import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MultiplicationExpression implements CompoundExpression {

    private final List<Expression> children;
    private CompoundExpression parent;

    MultiplicationExpression(final List<Expression> children, final CompoundExpression parent){
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
        //TODO Make sure this is correct
        List<Expression> newArr = new ArrayList<>();
        children.forEach(e -> newArr.add(e.deepCopy()));
        return new MultiplicationExpression(newArr, (CompoundExpression) parent.deepCopy());
    }

    @Override
    public void flatten() {
        if(parent == null){
            for(Expression child : children){
                child.flatten();
            }
        }
        ListIterator<Expression> iter = children.listIterator();
        while (iter.hasNext()){
            Expression child = iter.next();
            child.flatten();
            if(child instanceof MultiplicationExpression){
                iter.remove();
                ((MultiplicationExpression) child).children.forEach(e -> e.setParent(this));
                ((MultiplicationExpression) child).children.forEach(iter::add);
            }
        }
    }

    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        stringBuilder.append("\t".repeat(indentLevel)).append("*").append("\n");
        children.forEach(e -> stringBuilder.append(e.convertToString(indentLevel + 1)));
    }

    @Override
    public void addSubexpression(Expression subexpression) {
        children.add(subexpression);
    }

}
