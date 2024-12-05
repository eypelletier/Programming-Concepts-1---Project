package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class FileUtils {
    public static String readLastManifest(String filePath) {
        File lastManifestFile = new File(filePath);
        String lastManifestString = "";

        if (!lastManifestFile.exists()) return lastManifestString;

        //File does exist, proceed with processing
        try {
            //Set the delimiter to end of string code, so that entire file is loaded at once into string
            lastManifestString = new Scanner(lastManifestFile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return lastManifestString;
    }

    public static boolean writeManifest(String lastManifestString, String filePath) {
        File manifestFile = new File(filePath);
        try (PrintWriter writer = new PrintWriter(new FileWriter(manifestFile))) {
            writer.print(lastManifestString);
            return true;
        } catch (IOException e) {
            // Handle any IOExceptions that occur during writing
            System.out.println("An error occurred while writing to the file.");
            return false;
        }
    }
}
