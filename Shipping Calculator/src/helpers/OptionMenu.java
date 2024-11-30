package helpers;

import java.util.ArrayList;

//This menu is a helper class for user option entry at the command line
public class OptionMenu {
    private ArrayList<String> optionValue;
    private ArrayList<String> optionLabel;
    private int menuLength;

    public OptionMenu() {
        menuLength = 0;
        optionValue = new ArrayList<>();
        optionLabel = new ArrayList<>();
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
        String menuString ="";
        for (int optionNum = 0; optionNum < menuLength; optionNum++) {
            //Use inline divider if inlined is true
            char newLine = (inlined) ? '/' : '\n';

            //Override character to space if it is the last menu item
            newLine = (optionNum!=menuLength-1) ? newLine : ' ';
            menuString += String.format(" %s: %s %c", optionValue.get(optionNum),optionLabel.get(optionNum),newLine);
        }
        return menuString;
    }

    public boolean isValidOption(String option){
        return optionValue.contains(option);
    }
}
