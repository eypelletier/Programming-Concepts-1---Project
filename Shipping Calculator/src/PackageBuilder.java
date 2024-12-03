import helpers.OptionMenu;

import java.util.Scanner;

public class PackageBuilder {
    enum Modes {
        CREATE_MODE,
        EDIT_MODE,
    }

    private static Modes mode;

    public static Package createPackage(){
        PackageBuilder.mode = Modes.CREATE_MODE;
        Package pkg = new Package();
        configurePackage(pkg);
        return pkg;
    }


    public static void modifyPackage(Package pkg){
        PackageBuilder.mode = Modes.EDIT_MODE;
        configurePackage(pkg);
    }

    private static void configurePackage(Package pkg){
        assignPackageLabel(pkg);
        assignPackageDimensions(pkg);
        assignPackageWeight(pkg);
        assignPackageGoodsCategory(pkg);
    }

    public static void assignPackageLabel(Package pkg){
        Scanner keyboard = new Scanner(System.in);
        String currentLabel = pkg.getLabel();
        String pkgLabel = "";

        StringBuilder userPrompt = new StringBuilder("Enter package label");

        switch(PackageBuilder.mode){
            case CREATE_MODE:
                userPrompt.append(": ");
                break;
            case EDIT_MODE:
                userPrompt.append(String.format(" [%s]: ", currentLabel));
                break;
        }

        System.out.print(userPrompt);
        pkgLabel = keyboard.nextLine();

        if (pkgLabel.isEmpty()) pkgLabel = currentLabel;

        pkg.setLabel(pkgLabel);
    }

    public static void assignPackageDimensions(Package pkg){
        String userPromptStart = "Enter package dimensions ";
        StringBuilder heightPrompt = new StringBuilder(userPromptStart);
        StringBuilder widthPrompt = new StringBuilder(userPromptStart);
        StringBuilder lengthPrompt = new StringBuilder(userPromptStart);

        double pkgHeight = pkg.getHeight();
        double pkgWidth = pkg.getWidth();
        double pkgLength = pkg.getLength();

        switch (PackageBuilder.mode) {
            case CREATE_MODE:
                heightPrompt.append("(height in cm): ");
                widthPrompt.append("(width in cm): ");
                lengthPrompt.append("(length in cm): ");
                break;
            case EDIT_MODE:
                heightPrompt.append(String.format("(%s in cm) [%s]: ", "height", pkgHeight));
                widthPrompt.append(String.format("(%s in cm) [%s]: ", "width", pkgWidth));
                lengthPrompt.append(String.format("(%s in cm) [%s]: ", "length", pkgLength));
                break;
        }
        pkgHeight = retrievePackageDoubleInput(heightPrompt.toString(), pkgHeight);
        pkgWidth = retrievePackageDoubleInput(widthPrompt.toString(), pkgWidth);
        pkgLength = retrievePackageDoubleInput(lengthPrompt.toString(), pkgLength);

        pkg.setHeight(pkgHeight);
        pkg.setLength(pkgLength);
        pkg.setWidth(pkgWidth);
    }

    private static double retrievePackageDoubleInput(String userPrompt, double defaultValue){
        Scanner keyboard = new Scanner(System.in);
        boolean validInput = false;
        String userInput = "0.0";

        while (!validInput) {
            System.out.print(userPrompt);
            userInput = keyboard.nextLine();
            if (userInput.isEmpty() || Double.parseDouble(userInput) > 0.0 ) {
                validInput = true;
            } else if (Double.parseDouble(userInput) < 0.0) {
                System.out.println("Please enter a positive number.");
            }
        }

        //If user input is empty return the default value
        if (userInput.isEmpty()) return defaultValue;

        return Double.parseDouble(userInput);
    }

    public static void assignPackageWeight(Package pkg){
        StringBuilder userPrompt = new StringBuilder("Enter package weight (in kg)");

        double pkgWeight = pkg.getWeight();

        switch(PackageBuilder.mode){
            case CREATE_MODE:
                userPrompt.append(": ");
                break;
            case EDIT_MODE:
                userPrompt.append(String.format(" [%s]: ", pkgWeight));
                break;
        }

        pkgWeight = retrievePackageDoubleInput(userPrompt.toString(),pkgWeight);
        pkg.setWeight(pkgWeight);
    }

    public static void assignPackageGoodsCategory(Package pkg){
        Scanner keyboard = new Scanner(System.in);

        //Create menu for type of goods
        OptionMenu goodsTypeMenu = new OptionMenu();
        String[][] menuOptions = {{"1", "Regular"},{"2", "Fragile"},{"3", "Hazardous"},{"4", "Explosive"}};
        goodsTypeMenu.addAllMenuOptions(menuOptions);
        System.out.println("Select a goods category From the following options");
        System.out.printf("(%s)\n",goodsTypeMenu.menuAsString(true));

        StringBuilder userPrompt = new StringBuilder("Choice");
        String currentGoodsCategoryLabel = "";

        switch(PackageBuilder.mode){
            case CREATE_MODE:
                userPrompt.append(": ");
                break;
            case EDIT_MODE:
                currentGoodsCategoryLabel = pkg.getGoodsClassification().toString();
                userPrompt.append(String.format(" [%s]: ", currentGoodsCategoryLabel));
                break;
        }

        boolean validUserOption = false;

        while (!validUserOption) {
            System.out.print(userPrompt);
            String pkgGoodsClassification = keyboard.nextLine();

            if (pkgGoodsClassification.isEmpty() && pkg.getGoodsClassification() != null) {
                validUserOption = true;
            } else {
                validUserOption = goodsTypeMenu.isValidOption(pkgGoodsClassification);

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


    }

}
