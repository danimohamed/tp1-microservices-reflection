package dao;

/**
 * Implementation concrete de {@link IDao}.
 *
 * <p>Simule une source de donnees distante (base SQL, cache, API REST).
 * Dans un projet reel, cette classe contiendrait du code JDBC, JPA ou HTTP.</p>
 */
public class DaoImpl implements IDao {

    /**
     * Constructeur par defaut explicite : indispensable pour
     * {@code Constructor.newInstance()} utilise par le conteneur.
     */
    public DaoImpl() {
    }

    @Override
    public double getValue() {
        System.out.println("[DAO] Recuperation de la valeur depuis la source de donnees...");
        return 100.0;
    }
}
