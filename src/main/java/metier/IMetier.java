package metier;

/**
 * Contrat metier du microservice de calcul.
 */
public interface IMetier {

    /**
     * Effectue le calcul metier.
     *
     * @return le resultat du calcul.
     */
    double calcul();
}
