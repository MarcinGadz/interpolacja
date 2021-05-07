package num.formula;

public class Absolute implements Formula{
    @Override
    public String getDescription() {
        return "|x|";
    }

    @Override
    public double getValue(double x) {
        return Math.abs(x);
    }
}
