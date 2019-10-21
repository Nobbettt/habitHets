package main.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TxtDbCommunicator {

    static String readFile(String file) {
        String text = "";
        Scanner scanner;
        try {
            scanner = new Scanner( new File("C:\\Users\\norbe\\Documents\\habitHets\\HabitHets\\src\\main\\model\\"+file) );
            while (scanner.hasNextLine()) {
                text = scanner.useDelimiter("\\A").next();
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    static void writeFile(String file, String newTxt) {
        try {
            Files.write( Paths.get("C:\\Users\\norbe\\Documents\\habitHets\\HabitHets\\src\\main\\model\\"+file), newTxt.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
