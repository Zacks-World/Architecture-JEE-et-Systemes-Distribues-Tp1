package Presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            String daoClassname = scanner.nextLine();
            System.out.println(daoClassname);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
