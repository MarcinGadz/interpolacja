package num;


import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import num.formula.*;
import num.solver.LagrangeMethod;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.DoubleStream;

public class App {
    private static Polynomial createPolynomial() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj stopień (1 dla f. liniowej)");
        int deg = sc.nextInt();
        double[] coeffs = new double[deg+1];
        System.out.println("Podawaj współczynniki");
        for (int i = 0; i <= deg; i++) {
            coeffs[i] = sc.nextDouble();
        }
        return new Polynomial(coeffs);
    }

    private static Composition createComposition() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Wybierz f. zewnętrzną");
        System.out.println("1 - wielomian (liniowa)");
        System.out.println("2 - wart. bezwzgl.");
        System.out.println("3 - trygonometryczna (sinus)");
        Formula outer;
        byte test = sc.nextByte();
        switch (test) {
            case 1:
                outer = createPolynomial();
                break;
            case 2:
                outer = new Absolute();
                break;
            case 3:
                outer = new Trigonometric();
                break;
            default:
                outer = new Polynomial(new double[]{1});
        }
        System.out.println("Wybierz f. wewnętrzną");
        System.out.println("1 - wielomian (liniowa)");
        System.out.println("2 - wart. bezwzgl.");
        System.out.println("3 - trygonometryczna (sinus)");
        Formula inner;
        test = sc.nextByte();
        switch (test) {
            case 1:
                inner = createPolynomial();
                break;
            case 2:
                inner = new Absolute();
                break;
            case 3:
                inner = new Trigonometric();
                break;
            default:
                inner = new Polynomial(new double[]{1});
        }
        return new Composition(outer, inner);
    }

    public static void main(String[] args) {

        System.out.println("Marcin Gadziński 229877\nMichał Tosik 230027\n");
        System.out.println("Wybierz funkcję:");
        Scanner sc = new Scanner(System.in);
        Formula finalFormula;
        byte test;
        System.out.println("1 - wielomian (liniowa)");
        System.out.println("2 - wart. bezwzgl.");
        System.out.println("3 - trygonometryczna (sinus)");
        System.out.println("4 - złożenie");

        test = sc.nextByte();
        switch (test) {
            case 1:
                finalFormula = createPolynomial();
                break;
            case 2:
                finalFormula = new Absolute();
                break;
            case 3:
                finalFormula = new Trigonometric();
                break;
            case 4:
                finalFormula = createComposition();
                break;
            default:
                finalFormula = new Polynomial(new double[]{1});
        }

        System.out.println("Podaj przedział interpolacji: ");
        double begin = sc.nextDouble();
        double end = sc.nextDouble();
        System.out.println("Podaj ilość węzłów: ");
        int points = sc.nextInt();
        if (points <= 1) {
            return;
        }
        sc.close();

        //Odległość między węzłami
        double dist = (end - begin) / (points - 1);
        //obliczanie węzłów
        double[] pointsx = DoubleStream.iterate(begin, num -> num + dist).limit(points).toArray();
        double[] pointsy = Arrays.stream(pointsx).map(finalFormula::getValue).toArray();
        System.out.println(finalFormula.getDescription());

        //Wykres

        //Węzły
        String descriptionPoints = "Wezly";
        double[][] dataPoints = new double[points][2];
        for (int i = 0; i < points; i++) {
            dataPoints[i][0] = pointsx[i];
            dataPoints[i][1] = pointsy[i];
        }

        JavaPlot p = new JavaPlot();

        PlotStyle myPlotStyle = new PlotStyle();
        myPlotStyle.setStyle(Style.DOTS);
        DataSetPlot s = new DataSetPlot(dataPoints);
        myPlotStyle.setLineWidth(5);
        s.setPlotStyle(myPlotStyle);
        s.setTitle(descriptionPoints);

        //Funkcje
        //Narysuj funkcje na co najmniej 80 punktach
        int pointsForDrawing = points < 10 ? 80 : points * 8;
        double distCharts = (end - begin) / (pointsForDrawing - 1);
        double[] pointsxCharts = DoubleStream.iterate(begin, num -> num + distCharts).limit(pointsForDrawing).toArray();
        String descriptionCalculated = "F. interpolujaca";
        String descriptionBase = finalFormula.getDescription();
        double[][] dataExpected = new double[pointsForDrawing][2];
        double[][] dataCalculated = new double[pointsForDrawing][2];
        double[] tmpCalculated = LagrangeMethod.solveMorePoints(pointsx, pointsy, pointsxCharts, points);

        for (int i = 0; i < pointsForDrawing; i++) {
            dataExpected[i][0] = pointsxCharts[i];
            dataExpected[i][1] = finalFormula.getValue(pointsxCharts[i]);
            dataCalculated[i][0] = pointsxCharts[i];
            dataCalculated[i][1] = tmpCalculated[i];
        }
        //Oczekiwane
        PlotStyle myPlotStyleExpected = new PlotStyle();
        myPlotStyleExpected.setStyle(Style.LINES);
        DataSetPlot data = new DataSetPlot(dataExpected);
        myPlotStyleExpected.setLineWidth(1);
        data.setPlotStyle(myPlotStyleExpected);
        data.setTitle(descriptionBase);

        //Obliczone
        PlotStyle calculatedStyle = new PlotStyle();
        calculatedStyle.setStyle(Style.LINES);
        DataSetPlot dataCalc = new DataSetPlot(dataCalculated);
        calculatedStyle.setLineWidth(1);
        dataCalc.setPlotStyle(calculatedStyle);
        dataCalc.setTitle(descriptionCalculated);

        //Rysowanie
        p.addPlot(s);
        p.addPlot(data);
        p.addPlot(dataCalc);
        p.setKey(JavaPlot.Key.OUTSIDE);
        p.set("zeroaxis", "");
        p.set("xzeroaxis", "");
        p.plot();
    }
}
