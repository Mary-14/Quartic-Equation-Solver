package org.example;
import java.util.*;

public class EquationSolver {
    static final double EPSILON_FUNC = 0.000000001;
    static final double EPSILON_VALS = 0.0001;

    public static void main(String [] args) {
        System.out.println("Enter the coefficients for the 4th degree equation a*x^4 + b*x^3 + c*x^2 + d*x + e = 0");
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter coefficient a: ");
            double a = scanner.nextDouble();
            System.out.print("Enter coefficient b: ");
            double b = scanner.nextDouble();
            System.out.print("Enter coefficient c: ");
            double c = scanner.nextDouble();
            System.out.print("Enter coefficient d: ");
            double d = scanner.nextDouble();
            System.out.print("Enter coefficient e: ");
            double e = scanner.nextDouble();
            List<Pair> roots = new ArrayList<>();
            if(a!=0){
                System.out.println("Enter the initial approximation: ");
                double n = scanner.nextDouble();
                solveEquation(a, b, c, d, e, n, roots);
            }else{
                solveEquation(a, b, c, d, e, 0, roots);
            }
            scanner.close();
            if (!roots.isEmpty()) {
                System.out.println("Roots of the equation: ");
                for (Pair root : roots) {
                    if (root.im == 0) {
                        System.out.printf("%.4f%n", root.re);
                    } else {
                        System.out.printf("%.4f-%.4fi%n", root.re, root.im);
                        System.out.printf("%.4f+%.4fi%n", root.re, root.im);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: invalid input.");
            scanner.close();
        }
    }

    static boolean isZero(double x ) {
        return Math.abs(x) <= EPSILON_FUNC;
    }

    static boolean isZero(double x, double eps) {
        return Math.abs(x) <= eps;
    }

    static double zeroOrValue(double x) {
        return isZero(x) ? 0 : x;
    }

    static double zeroOrValue(double x, double eps) {
        return isZero(x, eps) ? 0 : x;
    }

    public static void solveEquation(double a, double b, double c, double d, double e, double nn, List<Pair> roots) {
        if (a != 0) {

            double xn1 =nn;
            double xn;
            int n=0;
            do {
                xn = xn1;
                double derivative = 4 * a * Math.pow(xn, 3) + 3 * b * Math.pow(xn, 2) + 2 * c * xn + d;
                double funcVal = a * Math.pow(xn, 4) + b * Math.pow(xn, 3) + c * xn * xn + d * xn + e;
                if (isZero(derivative)) {
                    if (isZero(funcVal)) {
                        break;
                    } else {
                        xn1 += EPSILON_VALS;
                        continue;
                    }
                }
                xn1 = xn - funcVal / derivative;
                n++;
                if (n>100000) {
                    System.out.println("Root not found");
                    return;
                }
            } while (!isZero(xn1 - xn));
            insert(xn1, 0, roots);
            double b2 = b + xn1 * a;
            double b1 = c + xn1 * b2;
            double b0 = d + xn1 * b1;
            solveEquation(0, a, b2, b1, b0, 0, roots);
        } else if (b != 0) {
            double p = zeroOrValue((3 * b * d - c * c) / (3 * b * b));
            double q = zeroOrValue((2 * Math.pow(c, 3) - 9 * b * c * d + 27 * b * b * e) / (27 * Math.pow(b, 3)));
            double Q = zeroOrValue(Math.pow(p, 3) / 27 + q * q / 4);
            if (Q > 0) {
                double A = Math.cbrt(-q / 2 + Math.sqrt(Q));
                double B = Math.cbrt(-q / 2 - Math.sqrt(Q));
                double x = A + B - c / (3 * b);
                insert(x, 0, roots);
                double imaginary = (A - B) / 2 * Math.sqrt(3);
                if (!isZero(imaginary)) {
                    x = -(A + B) / 2 - c / (3 * b);
                    insert(x, imaginary, roots);
                }

            } else if (Q == 0) {
                double x;
                if(q == 0){
                    x = -c/3*b;
                    insert(x, 0, roots);
                }else{
                    x = 2 * Math.cbrt(-q / 2) - c / (3 * b);
                    insert(x, 0, roots);
                    x = -Math.cbrt(-q / 2) - c / (3 * b);
                    insert(x, 0, roots);
                }
            } else {
                double phi=Math.acos(3*q/(2*p)*Math.sqrt(-3/p));
                for (double k = 0; k < 3; k++) {
                    double x = 2 * Math.sqrt(-p / 3) * Math.cos(phi / 3 + 2 * Math.PI * k / 3) - c / (3 * b);
                    insert(x, 0, roots);
                }
            }

        } else if (c != 0) {
            double D = zeroOrValue(d * d - 4 * c * e);
            if (D < 0) {
                insert(-d / (2 * c), Math.sqrt(-D) / (2 * c), roots);
            } else if (D == 0) {
                double x = -d / (2 * c);
                insert(x, 0, roots);
            } else {
                double x = (-d + Math.sqrt(D)) / (2 * c);
                insert(x, 0, roots);
                x = (-d - Math.sqrt(D)) / (2 * c);
                insert(x, 0, roots);
            }

        } else if (d != 0) {
            double x = -e / d;
            insert(x, 0, roots);
        } else if (e != 0) {
            System.out.println("No roots");
        } else {
            System.out.println("x can be any value");
        }
    }

    public static void insert(double re, double im, List<Pair> roots) {
        re = zeroOrValue(re, EPSILON_VALS);
        boolean found = false;
        for (Pair root : roots) {
            if (isZero(root.re - re, EPSILON_VALS)) {
                found = true;
                break;
            }
        }
        if (!found) {
            roots.add(new Pair(re, im));
        }
    }

}
