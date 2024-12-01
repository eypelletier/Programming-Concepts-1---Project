import helpers.OptionMenu;

import java.util.Scanner;

public class PackageBuilder {
    public static Package createPackage(){
        Package pkg = new Package();
        configurePackage(pkg);
        return pkg;
    }

    public static void configurePackage(Package pkg){
        assignPackageLabel(pkg);
        assignPackageDimensions(pkg);
        assignPackageWeight(pkg);
        assignPackageGoodsCategory(pkg);
    }

    public static void assignPackageLabel(Package pkg){
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter package label: ");
        String pkgLabel = keyboard.nextLine();
        pkg.setLabel(pkgLabel);
    }

    public static void assignPackageDimensions(Package pkg){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter package dimensions (Height x Width x Length in cm): ");
        double pkgHeight = keyboard.nextDouble();
        double pkgWidth = keyboard.nextDouble();
        double pkgLength = keyboard.nextDouble();

        pkg.setHeight(pkgHeight);
        pkg.setLength(pkgLength);
        pkg.setWidth(pkgWidth);
    }

    public static void assignPackageWeight(Package pkg){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter package weight (in kg): ");
        double pkgWeight = keyboard.nextDouble();

        pkg.setWeight(pkgWeight);
    }

    public static void assignPackageGoodsCategory(Package pkg){
        Scanner keyboard = new Scanner(System.in);

        //Create menu for type of goods
        OptionMenu goodsTypeMenu = new OptionMenu();
        goodsTypeMenu.addMenuOption("1", "Regular")
                .addMenuOption("2", "Fragile")
                .addMenuOption("3", "Hazardous")
                .addMenuOption("4", "Explosive");

        System.out.println("Select a goods category From the following options");
        System.out.printf("\n%s\n",goodsTypeMenu.menuAsString(true));
        System.out.print("Choice: ");
        String pkgGoodsClassification = keyboard.nextLine();
        goodsTypeMenu.isValidOption(pkgGoodsClassification);

        switch (pkgGoodsClassification){
            case "1":
                pkg.setGoodsClassification(GoodsCategory.REGULAR);
                break;
            case "2":
                pkg.setGoodsClassification(GoodsCategory.FRAGILE);
                break;
            case "3":
                pkg.setGoodsClassification(GoodsCategory.HAZARDOUS);
                break;
            case "4":
                pkg.setGoodsClassification(GoodsCategory.EXPLOSIVE);
                break;
        }
    }

}
