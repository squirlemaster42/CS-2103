import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AdditiveExpression implements CompoundExpression {

    private final List<Expression> children;
    private CompoundExpression parent;

    /**
     * Creates an instance of Additive Expression
     * @param children children of the additive expression
     * @param parent parent of the additive expression
     */
    AdditiveExpression(final List<Expression> children, final CompoundExpression parent){
        this.children = children;
        this.parent = parent;
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
     * Creates a deep copy of the expression
     * @return the deep copy that was created
     */
    @Override
    public Expression deepCopy() {
        //TODO Make sure we want an ArrayList
        //TODO Make sure this is correct
        List<Expression> newArr = new ArrayList<>();
        children.forEach(e -> newArr.add(e.deepCopy()));
        return new MultiplicationExpression(newArr, (CompoundExpression) parent.deepCopy());
    }

    /**
     * Combines common symbols to simplify the tree
     */
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
            if(child instanceof AdditiveExpression){
                iter.remove();
                ((AdditiveExpression) child).children.forEach(e -> e.setParent(this));
                ((AdditiveExpression) child).children.forEach(iter::add);
            }
        }
    }

    /**
     * Builds a Stringbuilder of the expressions
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        stringBuilder.append("\t".repeat(indentLevel)).append("+").append("\n");
        children.forEach(e -> stringBuilder.append(e.convertToString(indentLevel + 1)));
    }

    /**
     * Adds child expression
     * @param subexpression the child expression to add
     */
    @Override
    public void addSubexpression(Expression subexpression) {
        //TODO Make sure that this is correct
        children.add(subexpression);
    }
}
