package Presentation;

import Dao.IDao;

import java.io.File;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            String daoClassname = scanner.nextLine();
            Class<?> cDao = Class.forName(daoClassname);
            IDao dao = (IDao) cDao.getConstructor().newInstance();
            System.out.println(dao.getData());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
