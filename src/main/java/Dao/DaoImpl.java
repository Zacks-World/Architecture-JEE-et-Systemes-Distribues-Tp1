package Dao;

import org.springframework.stereotype.Component;

// DaoImpl est "une" implementation de l'interface IDao
@Component("Dao")
public class DaoImpl implements IDao{
    @Override
    public double getData() {
        System.out.println("Version base de données");
        return 23;
    }
}
