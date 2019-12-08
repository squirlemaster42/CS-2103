import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ParentheticalExpression extends AbstractCompoundExpression {

    /**
     * Creates an instance of Parenthetical Expression
     * @param children children of the additive expression
     * @param parent parent of the additive expression
     */
    ParentheticalExpression(final List<Expression> children, final CompoundExpression parent){
        super(children, parent, "()");
    }

    /**
     * Creates a deep copy of the expression
     * @return the deep copy that was created
     */
    @Override
    public Expression deepCopy() {
        List<Expression> newArr = new ArrayList<>();
        super.getChildren().forEach(e -> {
            Expression newE = e.deepCopy();
            newE.setParent(this);
            newArr.add(newE);
        });
        return new ParentheticalExpression(newArr, null);
    }

    /**
     * Combines common symbols to simplify the tree
     */
    @Override
    public void flatten() {
        if(parent == null){
            for(Expression child : super.getChildren()){
                child.flatten();
            }
        }
        ListIterator<Expression> iter = super.getChildren().listIterator();
        while (iter.hasNext()){
            Expression child = iter.next();
            child.flatten();
            if(child instanceof ParentheticalExpression){
                iter.remove();
                ((ParentheticalExpression) child).getChildren().forEach(e -> e.setParent(this));
                ((ParentheticalExpression) child).getChildren().forEach(iter::add);
            }
        }
    }
}
