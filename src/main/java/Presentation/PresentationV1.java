package Presentation;

import Dao.DaoImpl;
import Metier.MetierImpl;
import ext.DaoImplV2;

public class PresentationV1 {
    public static void main(String[] args) {
        /*
        Injection des dependances par instanciation statique => new
         */
        DaoImplV2 dao = new DaoImplV2();
        MetierImpl metier = new MetierImpl(dao);// Injection via le constructeur
        metier.setDao(dao); // Injection via le setter
        System.out.println("RES="+metier.calcul());
    }
}
