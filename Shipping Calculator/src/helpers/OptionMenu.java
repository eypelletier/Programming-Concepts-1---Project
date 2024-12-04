package helpers;

import java.util.ArrayList;
import java.util.Scanner;

//This menu is a helper class for user option entry at the command line
public class OptionMenu {
    private static String TempTitle;

    //Create MenuItem record keyword
    private record MenuItem(String choiceValue,String label,String dataValue) {}

    private ArrayList<MenuItem> menuItems;

    private String defaultValue;

    public OptionMenu() {
        menuItems = new ArrayList<MenuItem>();
    }

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

    //addAllMenuOptions supports an array of both a pair (choiceValue, choiceLabel) and triplet (choiceValue, choiceLabel, choiceDataValue)
    public OptionMenu addAllMenuOptions(String[][] valueLabelPairList){
        for (int i = 0; i < valueLabelPairList.length; i++) {
            if (valueLabelPairList[i].length != 2 && valueLabelPairList[i].length != 3) {
                throw new RuntimeException("Improper menu item provided to OptionsMenu");
            }

            addMenuOption(valueLabelPairList[i]);
        }
        return this;
    }

    //addMenuOption supports an array representing a pair (choiceValue, choiceLabel) or triplet (choiceValue, choiceLabel, choiceDataValue)
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

    public OptionMenu addMenuOption(String choiceValue, String choiceLabel, String choiceDataValue) {
        return addInternalMenuOption(choiceValue, choiceLabel, choiceDataValue);
    }

    public OptionMenu addMenuOption(String choiceValue, String choiceLabel){
        return addInternalMenuOption(choiceValue,choiceLabel,null);
    }

    private OptionMenu addInternalMenuOption(String choiceValue, String choiceLabel, String choiceDataValue) {
        menuItems.add(new MenuItem(choiceValue, choiceLabel, choiceDataValue));
        return this;
    }

    public String menuAsString(){
        return menuAsString(false);
    }

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

            //Style if default value present
            String defaultValueStyle = (defaultValue != null) ? "\033[34m" : "";
            menuString.append(defaultValueStyle).append(optionString).append(RESET).append(newLine);
        }
        return menuString.toString();
    }

    public OptionMenu withTitle(String title){
        TempTitle = title;
        return this;
    }

    public OptionMenu withDefault(String defaultValue){
        if (getMenuItemForChoice(defaultValue)!=null) {
            this.defaultValue = defaultValue;
        } else {
            throw new RuntimeException("Default option value does not exist");
        }
        return this;
    }

    public boolean isValidOption(String selectedOption){
        return (getMenuItemForChoice(selectedOption) != null);
    }

    public String promptForChoice(){
        if (this.defaultValue!=null) {
            return promptForChoice("Choose an option ["+this.defaultValue+"]: ");
        } else {
            return promptForChoice("Choose an option: ");
        }
    }

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

    public String getDataValueForOption(String option){
        MenuItem selectedItem = getMenuItemForChoice(option);
        if (selectedItem != null) return selectedItem.dataValue();
        return null;
    }

    private MenuItem getMenuItemForChoice(String choiceValue){
        for(MenuItem menuItem : menuItems){
            if (menuItem.choiceValue().equals(choiceValue)) return menuItem;
        }
        return null;
    }
}