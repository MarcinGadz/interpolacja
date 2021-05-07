package num.formula;

public class Polynomial implements Formula {
    private double[] coeffs;
    private int degree;

    public Polynomial(double[] coeffs) {
        this.coeffs = coeffs;
        this.degree = coeffs.length;
    }

    public double[] getCoeffs() {
        return coeffs;
    }

    @Override
    public String getDescription() {
        StringBuilder b = new StringBuilder();
        if (coeffs[0] > 0) {
            b.append(coeffs[0] + "x^" + (degree - 1));
        } else if (coeffs[0] < 0) {
            b.append(coeffs[0] + "x^" + (degree - 1));
        }
        for (int i = 1; i < degree; i++) {
            if (coeffs[i] > 0) {
                b.append("+ " + coeffs[i] + "x^" + (degree - i - 1));
            } else if (coeffs[i] < 0) {
                b.append(coeffs[i] + "x^" + (degree - i - 1));
            }
        }
        return b.toString();
    }

    @Override
    public double getValue(double x) {
        return Calculator.horner(coeffs, degree, x);
    }
}
