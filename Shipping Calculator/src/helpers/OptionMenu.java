package helpers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** Assists with the creation of text menus for user to select options
 *
 */
public class OptionMenu {
    //Temporary title for use with a single invocation of menu rendering methods
    private static String TempTitle;

    //Create MenuItem record
    private record MenuItem(String choiceValue,String label,String dataValue) {}

    private ArrayList<MenuItem> menuItems;

    //Default value for the menu
    private String defaultValue;

    //Currently disabled options
    private ArrayList<String> disabledItems;

    //Invalid enum option reasons
    public enum OptionValidity {
        VALID,
        INVALID_NON_EXISTENT,
        INVALID_DISABLED
    }

    private OptionValidity lastValidityCheckResult;

    /** Constructor
     *
     */
    public OptionMenu() {
        this.lastValidityCheckResult = OptionValidity.VALID;
        menuItems = new ArrayList<MenuItem>();
        disabledItems = new ArrayList<String>();
    }

    /** retrieve a temporary class property and destroy it after retrieval
     *
     * @param text, a string representing the name of the content to retrieve
     * @return a string representing the string content of a string field
     */
    private static String getTemp(String text){
        String tempText = null;
        switch(text){
            case "title":
                tempText = OptionMenu.TempTitle;
                OptionMenu.TempTitle = null;
                break;
            default:
                tempText = "";
        }
        return tempText;
    }


    /** adds all menu options provided to the menu
     * Supports an array of both a pair (choiceValue, choiceLabel) and triplet (choiceValue, choiceLabel, choiceDataValue)
     * @param menuItemTupleList, an array of strings containing an array (tuple) of string values
     * @return this OptionMenu
     */
    public OptionMenu addAllMenuOptions(String[][] menuItemTupleList){
        for (int i = 0; i < menuItemTupleList.length; i++) {
            if (menuItemTupleList[i].length != 2 && menuItemTupleList[i].length != 3) {
                throw new RuntimeException("Improper menu item provided to OptionsMenu");
            }

            addMenuOption(menuItemTupleList[i]);
        }
        return this;
    }

    /** determines if the valueLabelTuple provided has two or three items and forwards it to the appropriate
     * method
     *
     * @param valueLabelTuple, a string array of either a pair (choiceValue, choiceLabel) or triplet (choiceValue, choiceLabel, choiceDataValue)
     * @return this OptionMenu
     */
    public OptionMenu addMenuOption(String[] valueLabelTuple) {
        //Check to see if a string array of two items has been provided
        if (valueLabelTuple.length == 2) {
            return addMenuOption(valueLabelTuple[0], valueLabelTuple[1]);
        } else if (valueLabelTuple.length == 3) {
            return addMenuOption(valueLabelTuple[0], valueLabelTuple[1],valueLabelTuple[2]);
        }else {
            throw new IllegalArgumentException("Expected a pair of strings for menu option");
        }
    }

    /** add menu item according to alternate approach with distinct variables for menu item related properties
     *
     * @param choiceValue, a string representing the value a user must type in
     * @param choiceLabel, a string representing the label printed for the menu option
     * @param choiceDataValue, a string representing other related data to be stored with the menu item
     * @return this OptionMenu to allow for method chaining
     */
    public OptionMenu addMenuOption(String choiceValue, String choiceLabel, String choiceDataValue) {
        return addInternalMenuOption(choiceValue, choiceLabel, choiceDataValue);
    }

    /** add menu item according to alternate approach with distinct variables for menu item related properties
     *
     * @param choiceValue, a string representing the value a user must type in
     * @param choiceLabel, a string representing the label printed for the menu option
     * @return this OptionMenu to allow for method chaining
     */
    public OptionMenu addMenuOption(String choiceValue, String choiceLabel){
        return addInternalMenuOption(choiceValue,choiceLabel,null);
    }

    /** private method that adds a created MenuItem to the menuItems for the menu
     *
     * @param choiceValue, a string representing the value a user must type in
     * @param choiceLabel, a string representing the label printed for the menu option
     * @param choiceDataValue, a string representing other related data to be stored with the menu item
     * @return this OptionMenu to allow for method chaining
     */
    private OptionMenu addInternalMenuOption(String choiceValue, String choiceLabel, String choiceDataValue) {
        menuItems.add(new MenuItem(choiceValue, choiceLabel, choiceDataValue));
        return this;
    }

    /** convenience method to default to non-inline presentation/rendering of menu
     *
     * @return a string representing the rendered menu
     */
    public String menuAsString(){
        return menuAsString(false);
    }

    /** renders the menu as a string
     *
     * @param inlined, a boolean indicating whether the menu is to be rendered inline or vertically
     * @return, a string representation of the rendered menu
     */
    public String menuAsString(boolean inlined){
        StringBuilder menuString = new StringBuilder();
        int menuLength = menuItems.size();
        final String RESET = "\033[49m\033[39m\033[0m";

        //Add Menu Title If Present
        String titleString = getTemp("title");
        if (titleString!=null) menuString.append("\033[47m\033[30m=== ").append(titleString).append(" ===").append(RESET).append("\n");

        for (int optionNum = 0; optionNum < menuLength; optionNum++) {
            MenuItem menuItem = menuItems.get(optionNum);

            //Use inline divider if inlined is true
            char newLine = (inlined) ? '/' : '\n';

            //Override character to space if it is the last menu item
            newLine = (optionNum!=menuLength-1) ? newLine : ' ';
            String optionString = String.format(" %s: %s ", menuItem.choiceValue(),menuItem.label());

            //Style if option is disabled
            String disabledStyle = "";
            if (isOptionDisabled(menuItem.choiceValue)) {
                disabledStyle = "\033[31m";
            }

            //Style if default value present and option isn't disabled
            String defaultValueStyle = (defaultValue != null && disabledStyle.isEmpty() ) ? "\033[34m" : "";
            menuString.append(defaultValueStyle).append(disabledStyle).append(optionString).append(RESET).append(newLine);
        }
        return menuString.toString();
    }

    /** set the title of the menu
     *
     * @param title, as string representing the title of the menu
     * @return this OptionMenu, to allow for method chaining
     */
    public OptionMenu withTitle(String title){
        TempTitle = title;
        return this;
    }

    /** set the default value for the menu
     *
     * @param defaultValue, as string representation of the default selection for the menu
     * @return, this OptionMenu, to allow for method chaining
     */
    public OptionMenu withDefault(String defaultValue){
        if (getMenuItemForChoice(defaultValue)!=null) {
            this.defaultValue = defaultValue;
        } else {
            throw new RuntimeException("Default option value does not exist");
        }
        return this;
    }

    /** check to see if the option provided is a valid option for the menu (case sensitive)
     *
     * @param selectedOption, a string representing the selected option by the user
     * @return a boolean representing if the provided option exists for the menu
     */
    public boolean isValidOption(String selectedOption){
        if (getMenuItemForChoice(selectedOption) == null){
            lastValidityCheckResult = OptionValidity.INVALID_NON_EXISTENT;
        }
        else if (disabledItems.contains(selectedOption)){
            lastValidityCheckResult = OptionValidity.INVALID_DISABLED;
        } else {
            lastValidityCheckResult = OptionValidity.VALID;
        }

        return (lastValidityCheckResult == OptionValidity.VALID);
    }

    /** prompts the user for a choice and presenting the default if present using standard language
     *
     * @return a string representing the user choice
     */
    public String promptForChoice(){
        if (this.defaultValue!=null) {
            return promptForChoice("Choose an option ["+this.defaultValue+"]: ");
        } else {
            return promptForChoice("Choose an option: ");
        }
    }

    /** prompts the user for their choice given a specific prompt passed to the method
     *
     * @param prompt, a string representing the prompt to present to the user
     * @return a string representing the input from the user
     */
    public String promptForChoice(String prompt){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\n"+prompt);
        String userChoice = keyboard.nextLine();

        if ( (this.defaultValue != null) && userChoice.isEmpty() ) {
            return this.defaultValue;
        } else {
            return userChoice;
        }


    }

    /** get the data value for a specific menu option
     *
     * @param option, a string representing the menu item to query
     * @return a string representing the data value of the associated menu item (if found), otherwise null
     */
    public String getDataValueForOption(String option){
        MenuItem selectedItem = getMenuItemForChoice(option);
        if (selectedItem != null) return selectedItem.dataValue();
        return null;
    }

    /** return a MenuItem for a given user choice
     *
     * @param choiceValue, a string representing the users choice
     * @return a MenuItem for the choice, or null if none found
     */
    private MenuItem getMenuItemForChoice(String choiceValue){
        for(MenuItem menuItem : menuItems){
            if (menuItem.choiceValue().equals(choiceValue)) return menuItem;
        }
        return null;
    }

    //Possible changes follow below
    public OptionMenu withDisabled(String[] disabledOptions){
        this.disabledItems.clear();
        disabledItems.addAll(Arrays.asList(disabledOptions));
        return this;
    }

    public boolean isOptionDisabled(String optionChoiceValue){
        return (this.disabledItems.contains(optionChoiceValue));

    }

    public OptionValidity getLastValidityCheckResult() {
        return lastValidityCheckResult;
    }
}