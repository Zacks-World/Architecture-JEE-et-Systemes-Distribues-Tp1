package Metier;
import Dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {
    //Couplage faible
    private IDao dao;
    public MetierImpl(@Qualifier("dao") IDao dao) {
        this.dao = dao;
    }
    @Override
    public double calcul() {
        return dao.getData()*23;
    }
    /**
     * Pour injecter ds la variable dao un objet
     * d'une classe qui implémente l'interface IDao
     */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
