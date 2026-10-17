package metier;

import dao.IDao;

/**
 * Implementation de {@link IMetier} dependant d'un {@link IDao}.
 *
 * <p>La dependance n'est pas instanciee ici : elle est injectee de
 * l'exterieur par le conteneur, via le setter {@link #setDao(IDao)}.</p>
 */
public class MetierImpl implements IMetier {

    private IDao dao;

    public MetierImpl() {
    }

    /**
     * Point d'injection appele dynamiquement par le conteneur.
     *
     * @param dao l'implementation de {@link IDao} a injecter.
     */
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public IDao getDao() {
        return dao;
    }

    @Override
    public double calcul() {
        if (dao == null) {
            throw new IllegalStateException("Le DAO n'a pas ete injecte.");
        }
        double valeurBase = dao.getValue();
        double resultat = valeurBase * 2;
        System.out.println("[METIER] Calcul effectue. Resultat : " + resultat);
        return resultat;
    }
}
