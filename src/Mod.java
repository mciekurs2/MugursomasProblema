import java.math.BigInteger;
import java.util.Scanner;

public class Mod {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String check;
            do {
                try {
                    System.out.println("Ievadiet pirmo skaitli: ");
                    BigInteger first = sc.nextBigInteger();

                    System.out.println("Ievadiet otro skaitli: ");
                    BigInteger second = sc.nextBigInteger();

                    System.out.println(first + " reverse mod no " + second + " ir " + first.modInverse(second));
                } catch (ArithmeticException ex) {
                    System.out.println("Mod inversa nepastāv!");
                }

                System.out.println("Vai turpinat?: ");
                check = sc.next();
            } while (!check.equals("ne"));

    }
}
