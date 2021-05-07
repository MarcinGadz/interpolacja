package num.formula;

public class Trigonometric implements Formula {

    @Override
    public String getDescription() {
        return "sin(x)";
    }

    @Override
    public double getValue(double x) {
        return Math.sin(x);
    }
}
