package helpers;

import java.util.ArrayList;
import java.util.Scanner;

//This menu is a helper class for user option entry at the command line
public class OptionMenu {
    private static String TempTitle;
    private ArrayList<String> optionValue;
    private ArrayList<String> optionLabel;
    private int menuLength;
    private String defaultValue;

    public OptionMenu() {
        menuLength = 0;
        optionValue = new ArrayList<>();
        optionLabel = new ArrayList<>();
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

    public OptionMenu addAllMenuOptions(String[][] valueLabelPairList){
        for (int i = 0; i < valueLabelPairList.length; i++) {
            addMenuOption(valueLabelPairList[i]);
        }
        return this;
    }

    public OptionMenu addMenuOption(String[] valueLabelPair) {
        //Check to see if a string array of two items has been provided
        if (valueLabelPair.length == 2) {
            return addMenuOption(valueLabelPair[0], valueLabelPair[1]);
        } else {
            throw new IllegalArgumentException("Expected a pair of strings for menu option");
        }
    }

    public OptionMenu addMenuOption(String value, String label){
        if (optionValue.contains(value)){
            int optionIndex = optionValue.indexOf(value);
            optionLabel.set(optionIndex, label);
        } else {
            optionValue.add(value);
            optionLabel.add(label);
            menuLength++;
        }
        return this;
    }

    public String menuAsString(){
        return menuAsString(false);
    }

    public String menuAsString(boolean inlined){
        StringBuilder menuString = new StringBuilder();
        final String RESET = "\033[49m\033[39m\033[0m";

        //Add Menu Title If Present
        String titleString = getTemp("title");
        if (titleString!=null) menuString.append("\033[47m\033[30m=== ").append(titleString).append(" ===").append(RESET).append("\n");

        for (int optionNum = 0; optionNum < menuLength; optionNum++) {
            //Use inline divider if inlined is true
            char newLine = (inlined) ? '/' : '\n';

            //Override character to space if it is the last menu item
            newLine = (optionNum!=menuLength-1) ? newLine : ' ';
            String optionString = String.format(" %s: %s ", optionValue.get(optionNum),optionLabel.get(optionNum));

            //Style if default value present
            String defaultValueStyle = (optionValue.get(optionNum).equals(defaultValue)) ? "\033[34m" : "";
            menuString.append(defaultValueStyle).append(optionString).append(RESET).append(newLine);
        }
        return menuString.toString();
    }

    public OptionMenu withTitle(String title){
        TempTitle = title;
        return this;
    }

    public OptionMenu withDefault(String defaultValue){
        if (optionValue.contains(defaultValue)){
            this.defaultValue = defaultValue;
        } else {
            throw new RuntimeException("Default option value does not exist");
        }
        return this;
    }

    public boolean isValidOption(String option){
        return optionValue.contains(option);
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
}
