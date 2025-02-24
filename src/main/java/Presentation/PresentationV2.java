package Presentation;

import Dao.IDao;
import Metier.IMetier;

import java.io.File;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            //DaoImpl dao = new DaoImpl();
            String daoClassname = scanner.nextLine();
            Class<?> cDao = Class.forName(daoClassname);
            IDao dao = (IDao) cDao.getConstructor().newInstance();
            //MetierImpl metier = new MetierImpl(dao);
            String metierClassname = scanner.nextLine();
            Class<?> cMetier = Class.forName(metierClassname);
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);
            System.out.println("RES="+metier.calcul());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
