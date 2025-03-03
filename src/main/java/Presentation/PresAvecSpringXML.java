package Presentation;

import Metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresAvecSpringXML {
    public static void main(String[] args) {
        // Configuration de l'application
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("RES="+metier.calcul());
    }
}
