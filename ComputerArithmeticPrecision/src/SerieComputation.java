import java.math.BigDecimal;

public class SerieComputation {
    public static void main(String[] args) {
        System.out.println(Series(10000));

        System.out.println(series_separated(10000));

        System.out.println(series_separated2(10000));

        System.out.println(series_separated3(10000));

    }

    public static float Series(int n) {
        float s = 0;
        float i = 1;
        while (i < n) {
            if (i % 2 == 1)
                s += 1 / i;
            else
                s -= 1 / i;

            i += 1;
        }
        return s;
    }

    public static float series_separated(int n) {
        float s = 0;
        float s2 = 0;
        float i = 1;
        float j = 2;
        while (i < n) {
            s += 1 / i;
            i += 2;
        }
        while (j < n) {
            s2 -= 1 / j;
            j += 2;
        }
        return s + s2;
    }

    public static double series_separated2(int n) {
        double s = 0;
        double s2 = 0;
        float i = 1;
        float j = 2;
        while (i < n) {
            s += 1 / i;
            i += 2;
        }
        while (j < n) {
            s2 -= 1 / j;
            j += 2;
        }
        return s + s2;
    }

    public static BigDecimal series_separated3(int n) {
        BigDecimal s = new BigDecimal(0);
        BigDecimal s2 = new BigDecimal(0);
        float i = 1;
        float j = 2;
        while (i < n) {
            s= s.add(BigDecimal.valueOf(1 / i));
            i += 2;
        }
        while (j < n) {
            s2 = s2.subtract(BigDecimal.valueOf(1 / j));
            j += 2;
        }
        return s.add(s2);
    }
}
