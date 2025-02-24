package Dao;

// DaoImpl est "une" implementation de l'interface IDao
public class DaoImpl implements IDao{
    @Override
    public double getData() {
        System.out.println("Version base de données");
        return 23;
    }
}
