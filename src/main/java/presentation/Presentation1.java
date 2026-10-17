package presentation;

import dao.DaoImpl;
import dao.IDao;
import metier.IMetier;
import metier.MetierImpl;

/**
 * Version statique, sans reflexion : le couplage est fige a la compilation.
 * Sert de point de comparaison avec {@link Presentation2}.
 */
public class Presentation1 {

    public static void main(String[] args) {
        IDao dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);
        System.out.println("[STATIQUE] Injection manuelle en dur, sans reflexion");
        IMetier resultat = metier;
        System.out.println("Resultats = " + resultat.calcul());
    }
}
