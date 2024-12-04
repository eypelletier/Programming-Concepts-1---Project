package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtils {
    public static String readLastManifest(String filename) {
        File lastManifestFile = new File("./"+filename);
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
}
