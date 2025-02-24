package Presentation;

import Dao.IDao;
import Metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ext","dao", "metier");
        IMetier metier = context.getBean(IMetier.class);
        IDao dao = context.getBean(IDao.class);
        System.out.println("RES="+metier.calcul());
    }
}
