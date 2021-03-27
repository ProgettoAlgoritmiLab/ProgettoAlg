package LaboratorioAlg;

import java.util.Random;
import java.util.Scanner;

public class Progetto {

    public static void main(String[] args) {

        int A = 1000;
        double end;
        double start;
        int[] N = new int[130];
        double a = (Math.log(500000) - Math.log(A)) / 129;
        double Err = 0.001;
        Scanner in = new Scanner(System.in);
        int length = 1056;
        int mode;
        ArrayList<Integer> periodi = new ArrayList<Integer>();

        System.out.print("Inserire 1 se si vuole usare il metodo di generazione 1, 2 se si vuole usare il secondo: ");
        mode = in.nextInt();
        System.out.print("\n" + "Inserire un numero per iniziare la stima dei tempi per il periodo frazionario smart: ");

        if(mode <=3) {
            if (in.hasNextInt()) {
                System.out.println("Avvio calcolo:" + "\n");
                for (int j = 0; j <= 129; j++) {
                    double r = getResolution();
                    double Tmin = r * (1 / Err + 1);
                    N[j] = (int) (A * (Math.pow(Math.exp(a), j))); //A*(B^j)
                    start = System.currentTimeMillis();
                    int k = 0;
                    if(N[j] == 1000) {
                        do {
                            StringBuilder s = generateString(N[j], mode);
                            periodi.add(periodSmart(s));
                            end = System.currentTimeMillis();
                            k++;
                        } while (end - start < Tmin);
                    } else {
                        do {
                            StringBuilder s = generateString(N[j], mode);
                            periodSmart(s);
                            end = System.currentTimeMillis();
                            k++;
                        } while (end - start < Tmin);
                    }
                    double Tn = ((end - start) / k);
                    System.out.println(Tn + ",");
                }
            }
        } else {
            if (in.hasNextInt()) {
                System.out.println("Avvio calcolo:" + "\n");
                while(length < 500000) {
                    double r = getResolution();
                    double Tmin = r * (1 / Err + 1);
                    start = System.currentTimeMillis();
                    int k = 0;
                    do {
                        StringBuilder s = generateString(length, mode);
                        periodSmart(s);
                        end = System.currentTimeMillis();
                        k++;
                    } while (end - start < Tmin);
                    double Tn = ((end - start) / k);
                    System.out.println(Tn + ",");
                }
            }
        }
        
        System.out.println("\n" + "Processo terminato");

        System.out.print("\n" + "Inserire un numero per iniziare la stima dei tempi per il periodo frazionario naive: ");

        Scanner scan2 = new Scanner(System.in);
        if (scan2.hasNextInt()) {
            System.out.println("Avvio calcolo:" + "\n");
            for (int j = 0; j <= 129; j++) {
                double r = getResolution();
                double Tmin = r * (1 / Err + 1);
                N[j] = (int) (A * (Math.pow(Math.exp(a), j))); //A*(B^j)
                start = System.currentTimeMillis();
                int m = 0;
                do {
                    StringBuilder s = generateString(N[j], mode);
                    periodoFrazionarioNaive(s);
                    end = System.currentTimeMillis();
                    m++;
                } while (end - start < Tmin);
                double Tn = ((end - start) / m);
                System.out.println(Tn + "," /*+ "  " + N[j] */);
            }
        }
        System.out.println("\n" + "Processo terminato");
    }



    public static StringBuilder generateString(int N, int mode) {

        StringBuilder s = new StringBuilder(N);

        switch (mode) {
            case 1:
                for (int i = 0; i < N; i++) {
                    if (Math.random() <= 0.5) {
                        s.append('a');
                    } else {
                        s.append('b');
                    }
                }
                break;
            case 2:
                Random rand = new Random();
                int q = rand.nextInt(N - 1) + 1;
                for (int i = 0; i < q; i++) {
                    if (Math.random() <= 0.5) {
                        s.append('a');
                    } else {
                        s.append('b');
                    }
                }

                for (int i = q; i < N; i++) {
                    s.append(s.charAt(i % q));
                }
                break;
            case 3:
                Random rand2 = new Random();
                int p = rand2.nextInt(N - 1) + 1;
                for (int i = 0; i < p; i++) {
                    if (Math.random() <= 0.5) {
                        s.append('a');
                    } else {
                        s.append('b');
                    }
                }

                s.append('c');

                for (int i = p + 1; i < N; i++) {
                    s.append(s.charAt(i %( p+1)));
                }
                break;
        }
        return s;
    }

    public static double getResolution() {
        double end;
        double start = System.currentTimeMillis();
        do {
            end = System.currentTimeMillis();
        } while (start == end);
        return (end - start);
    }

    public static int periodoFrazionarioNaive(StringBuilder s) {
        int n = s.length();
        for (int i = 1; i < n; i++) { //costo: O(n)
            String s1 = s.substring(0, n - i);
            String s2 = s.substring(i, n);
            if (s1.equals(s2)) { //costo: O(n)
                return i;
            }
        } //costo tot: O(n^2)
        return n;
    }

    public static int periodSmart(StringBuilder s) { //costo tot: O(n)
        int n = s.length();
        int[] r = new int[n];
        r[0] = 0;
        for (int i = 1; i < n; i++) {
            int z = r[i - 1];
            while ((s.charAt(i) != s.charAt(z)) && z > 0) {
                z = r[z - 1];
            }
            if (s.charAt(i) == s.charAt(z)) {
                r[i] = z + 1;
            } else {
                r[i] = 0;
            }
        }
        return n - r[n - 1];  //r[n-1]=bordo max
    }
}
