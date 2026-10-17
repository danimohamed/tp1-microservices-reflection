package dao;

/**
 * Seconde implementation de {@link IDao}, retournant une valeur differente.
 *
 * <p>Elle sert a demontrer l'interet de la configuration externalisee :
 * on change de source de donnees en editant {@code config-dao2.txt},
 * sans recompiler ni modifier une seule ligne de code metier.</p>
 */
public class DaoImpl2 implements IDao {

    public DaoImpl2() {
    }

    @Override
    public double getValue() {
        System.out.println("[DAO] Lecture depuis le cache memoire local (DaoImpl2)...");
        return 42.0;
    }
}
