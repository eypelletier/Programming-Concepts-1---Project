import java.util.Scanner;

public class ShippingCalculator {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter height in cm: ");
        double height = input.nextDouble();
        System.out.print("Enter width in cm: ");
        double width = input.nextDouble();
        System.out.print("Enter length in cm: ");
        double length = input.nextDouble();
        System.out.print("Enter weight in kg: ");
        double weight = input.nextDouble();

    }
}
