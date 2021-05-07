package num.formula;

public class Composition implements Formula {
    private Formula outer;
    private Formula inner;

    public Composition(Formula outer, Formula inner) {
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    public String getDescription() {
        return outer.getDescription() + " od " + inner.getDescription();
    }

    @Override
    public double getValue(double x) {
        return outer.getValue(inner.getValue(x));
    }
}
