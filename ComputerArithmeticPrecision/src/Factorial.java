import java.math.BigInteger;

public class Factorial {

    public static void main(String[] args) {
        for (int i = 0; i < 15; i++) {
            int x = Factorial(i);
            BigInteger y = Factorial(BigInteger.valueOf(i));
            System.out.println(i + ": int val : " + x + " , BigInt val : " + y);

            if (!y.equals(BigInteger.valueOf(x))) {
                System.out.println("Different values appeared :" + x + " and " + y +
                        "\nThe int and bigInt factorials differ at n=" + i + "\nBitLength of bigint value : " +
                        y.bitLength());
                break;
            }
        }
    }


    public static int Factorial(int n) {
        int f = 1;
        for (int i = 1; i < n + 1; i++)
            f = f * i;

        return f;
    }

    public static BigInteger Factorial(BigInteger n) {
        BigInteger f = BigInteger.valueOf(1);
        for (int i = 1; i < n.intValue() + 1; i++)
            f = f.multiply(BigInteger.valueOf(i));

        return f;
    }
}
