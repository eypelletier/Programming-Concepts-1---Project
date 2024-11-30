import helpers.OptionMenu;

import java.util.Scanner;

public class ShippingCalculator {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to SuperShipper - Steam Early Release - pre-v0.1\n" +
                "We will assist with calculating the cost of your shipment.");

        System.out.println("1. Create new shipment\n" +
                "2. Quit");
        int menuChoice = input.nextInt();

        System.out.print("Please enter a label for the shipment: ");
        String shipmentLabel = input.nextLine();

        System.out.print("How many packages would you like to ship: ");
        int numPackages = input.nextInt();
        input.nextLine();

        for (int i = 0; i < numPackages; i++) {
            System.out.println("Package" + (i + 1) + ": ");
            System.out.println("Enter package label:");
            String label = input.nextLine();

            System.out.println("Enter package dimensions (Height x Width x Length in cm\nWeight in kg):");
            double height = input.nextDouble();
            double width = input.nextDouble();
            double length = input.nextDouble();
            double weight = input.nextDouble();

            //Create menu for type of goods
            OptionMenu goodsTypeMenu = new OptionMenu();
            goodsTypeMenu.addMenuOption("1", "Regular")
                    .addMenuOption("2", "Fragile")
                    .addMenuOption("3", "Hazardous")
                    .addMenuOption("4", "Explosive");
            System.out.printf("/---/\n%s\n",goodsTypeMenu.menuAsString(true));
            /*
            System.out.println("""
                    Select type of goods:
                    1. Regular
                    2. Fragile
                    3. Hazardous
                    4. Explosive""");
            */

            String goodsClassification = input.nextLine();
            goodsTypeMenu.isValidOption(goodsClassification);
            GoodsCategory = new GoodsCategory();
            //Package pkg = new Package(label, height, width, length, weight, goodsClassification);


        }

        System.out.println("Please select the Origin and Destination for the shipment:");
        System.out.print("Origin: 1. Montreal\n2. Toronto\n3. Vancouver");
        String shipmentOrigin = "Montreal";
        System.out.print("Destination: 1. Montreal\n2. Toronto\n3. Vancouver");
        String shipmentDestination = "Vancouver";

        System.out.print("""
                Please select the method of transport:
                1. Truck
                2. Rail
                3. Sea
                4. Air""");
        String methodOfTransport = "Truck";

        System.out.print("""
                Please select the priority for the shipment:
                1. Standard
                2. Express""");
        String shipmentPriority = "Express";

    }
}
