package Metier;
import Dao.IDao;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {
    //Couplage faible
    private IDao dao;
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }
    @Override
    public double calcul() {
        double t = dao.getData();
        double result = t*23;
        return result;
    }
    /**
     * Pour injecter ds la variable dao un objet
     * d'une classe qui implémente l'interface IDao
     */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
