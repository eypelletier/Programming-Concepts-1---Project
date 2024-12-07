import helpers.OptionMenu;
import java.util.Scanner;

/** PackageBuilder is a helper class that supports the program flow and construction of package objects
 *
 */
public class PackageBuilder {

    /** Modes represents the current 'stateful' mode of the PackageBuilder,
     *
     */
    enum Modes {
        CREATE_MODE,
        EDIT_MODE,
    }

    /** mode stores the current mode for the ProjectBuilder
     *
     */
    private static Modes mode;

    /** creates a new package
     *
     * @return a package, the new package
     */
    public static Package createPackage(){
        PackageBuilder.mode = Modes.CREATE_MODE;
        Package pkg = new Package();
        configurePackage(pkg);
        return pkg;
    }

    /** modify a package
     *
     * @param pkg, a Package representing the package to modify
     */
    public static void modifyPackage(Package pkg){
        PackageBuilder.mode = Modes.EDIT_MODE;
        configurePackage(pkg);
    }

    /** configure the passed package
     *
     * @param pkg, a Package representing the package to configure (or assemble)
     */
    private static void configurePackage(Package pkg){
        assignPackageLabel(pkg);
        assignPackageDimensions(pkg);
        assignPackageWeight(pkg);
        assignPackageGoodsCategory(pkg);
    }

    /** assign the label for the package
     *
     * @param pkg, a Package representing the package to set the label for
     */
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

    /** assign the dimensions for the package
     *
     * @param pkg, a Package representing the package to assign the dimensions to
     */
    public static void assignPackageDimensions(Package pkg){
        String userPromptStart = "Enter package dimensions ";

        StringBuilder lengthPrompt = new StringBuilder(userPromptStart);
        StringBuilder widthPrompt = new StringBuilder(userPromptStart);
        StringBuilder heightPrompt = new StringBuilder(userPromptStart);

        double pkgLength = pkg.getLength();
        double pkgWidth = pkg.getWidth();
        double pkgHeight = pkg.getHeight();



        switch (PackageBuilder.mode) {
            case CREATE_MODE:
                lengthPrompt.append("(length in cm): ");
                widthPrompt.append("(width in cm): ");
                heightPrompt.append("(height in cm): ");
                break;
            case EDIT_MODE:
                lengthPrompt.append(String.format("(%s in cm) [%s]: ", "length", pkgLength));
                widthPrompt.append(String.format("(%s in cm) [%s]: ", "width", pkgWidth));
                heightPrompt.append(String.format("(%s in cm) [%s]: ", "height", pkgHeight));
                break;
        }
        pkgLength = retrievePackageDoubleInput(lengthPrompt.toString(), pkgLength);
        pkgWidth = retrievePackageDoubleInput(widthPrompt.toString(), pkgWidth);
        pkgHeight = retrievePackageDoubleInput(heightPrompt.toString(), pkgHeight);

        pkg.setLength(pkgLength);
        pkg.setWidth(pkgWidth);
        pkg.setHeight(pkgHeight);
    }

    /** prompt the user for a non-negative double value for a package field
     *  (TODO: excellent candidate for moving to a helper class/library)
     *
     * @param userPrompt, a string representing the prompt to display on the screen for input
     * @param defaultValue, a double representing any default value to provide if response is simply
     *                      a carriage return
     * @return a double representing a valid value
     */
    private static double retrievePackageDoubleInput(String userPrompt, double defaultValue){
        Scanner keyboard = new Scanner(System.in);
        boolean validInput = false;
        String userInput = "0.0";
        double userInputDouble = 0.0;

        while (!validInput) {
            System.out.print(userPrompt);
            userInput = keyboard.nextLine();

            try {

                if (userInput.isEmpty()) {
                    validInput = true;
                }
                else {
                    userInputDouble = Double.parseDouble(userInput);

                    if (userInputDouble < 0.0) {
                        System.out.println("Please enter a positive number.");
                    } else {
                        validInput = true;
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a positive number.");
            }

        }

        //If user input is empty return the default value
        if (userInput.isEmpty()) return defaultValue;

        return Double.parseDouble(userInput);
    }

    /** assign the weight to a package
     *
     * @param pkg, a Package representing the package to assign the weight to
     */
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

    /** assign the GoodsCategory for the package
     *
     * @param pkg, a Package representing the package to assign a GoodsCategory for
     */
    public static void assignPackageGoodsCategory(Package pkg){
        //Create menu for type of goods
        OptionMenu goodsTypeMenu = new OptionMenu();
        String[][] menuOptions = {{"1", "Regular","REG"},{"2", "Fragile","FRG"},{"3", "Hazardous","HAZ"},{"4", "Explosive","EXP"}};
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

        promptForGoodsCategory(userPrompt.toString(),pkg,goodsTypeMenu);

    }

    /** prompt the user to choose a GoodsCategory
     *
     * @param userPrompt, a string representing the prompt to display to the user
     * @param pkg, a package, representing the package to assign the GoodsCategory to
     * @param goodsTypeMenu, an OptionMenu presenting the available goods categories
     */
    private static void promptForGoodsCategory(String userPrompt, Package pkg, OptionMenu goodsTypeMenu){
        Scanner keyboard = new Scanner(System.in);
        boolean validUserOption = false;

        while (!validUserOption) {
            System.out.print(userPrompt);
            String pkgGoodsClassification = keyboard.nextLine();

            if (pkgGoodsClassification.isEmpty() && pkg.getGoodsClassification() != null) {
                validUserOption = true;
            } else {
                validUserOption = goodsTypeMenu.isValidOption(pkgGoodsClassification);

                if (validUserOption) {
                    String strId = goodsTypeMenu.getDataValueForOption(pkgGoodsClassification);
                    GoodsCategory category = GoodsCategory.getCategoryByStrId(strId);

                    if (category != null) {
                        pkg.setGoodsClassification(category);
                    } else {
                        throw new RuntimeException("Unable to retrieve proper GoodsCategory");
                    }
                }
            }

        }
    }

}
