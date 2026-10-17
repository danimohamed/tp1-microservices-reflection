package dao;

/**
 * Contrat de persistance.
 *
 * <p>Cette interface represente l'abstraction de la couche de donnees.
 * Le code metier ne depend que de ce contrat, jamais d'une implementation
 * concrete : c'est le Dependency Inversion Principle.</p>
 */
public interface IDao {

    /**
     * Recupere la valeur depuis la source de donnees.
     *
     * @return la valeur numerique lue.
     */
    double getValue();
}
