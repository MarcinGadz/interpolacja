package num.solver;

public class LagrangeMethod {
    public static double solve(double[] x, double[] y, double arg, int noOfPoints) {
        double res = 0;
        for (int i = 0; i < noOfPoints; i++) {
            double currRes = y[i];
            for (int j = 0; j < noOfPoints; j++) {
                if (j != i) {
                    currRes = currRes * (arg - x[j]) / (x[i] - x[j]);
                }
            }
            res += currRes;
        }
        return res;
    }

    public static double[] solveMorePoints(double[] x, double[] y, double[] args, int noOfPoints) {
        double[] tmp = new double[noOfPoints];
        for (int i = 0; i < noOfPoints; i++) {
            tmp[i] = 1;
            for (int j = 0; j < noOfPoints; j++) {
                if (j != i) {
                    tmp[i] *= (x[i] - x[j]);
                }
            }
        }
        double[] res = new double[args.length];
        for (int k = 0; k < args.length; k++) {
            for (int i = 0; i < noOfPoints; i++) {
                double currRes = y[i];
                for (int j = 0; j < noOfPoints; j++) {
                    if (j != i) {
                        currRes *= args[k] - x[j];
                    }
                }
                res[k] += currRes /tmp[i];
            }
        }
        return res;
    }
}
