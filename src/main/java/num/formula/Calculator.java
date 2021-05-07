package num.formula;

public class Calculator {
    static double horner(double[] coeffs, int len, double x) {
        double res = coeffs[0];
        for (int i = 1; i < len; i++) {
            res = res * x + coeffs[i];
        }
        return res;
    }
}
