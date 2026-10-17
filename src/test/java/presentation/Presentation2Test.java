package presentation;

import dao.DaoImpl;
import dao.IDao;
import metier.IMetier;
import metier.MetierImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Presentation2Test {

    @Test
    @DisplayName("config.txt contient les deux classes fully qualified attendues")
    void configContientDeuxClasses() throws Exception {
        String[] names = Presentation2.loadClassNames("config.txt");
        assertEquals(2, names.length);
        assertEquals("dao.DaoImpl", names[0]);
        assertEquals("metier.MetierImpl", names[1]);
    }

    @Test
    @DisplayName("config-memoire.txt pointe vers la seconde implementation")
    void configMemoirePointeVersAutreDao() throws Exception {
        String[] names = Presentation2.loadClassNames("config-memoire.txt");
        assertEquals("dao.DaoImplMemoire", names[0]);
    }

    @Test
    @DisplayName("instantiateClass charge et instancie une classe par reflexion")
    void instanciationParReflexion() throws Exception {
        Object o = Presentation2.instantiateClass("dao.DaoImpl");
        assertNotNull(o);
        assertInstanceOf(IDao.class, o);
    }

    @Test
    @DisplayName("instantiateClass leve ClassNotFoundException sur un nom invalide")
    void instanciationNomInvalide() {
        assertThrows(ClassNotFoundException.class,
                () -> Presentation2.instantiateClass("dao.PasUneClasse"));
    }

    @Test
    @DisplayName("injectDependency appelle setDao par reflexion")
    void injectionParReflexion() throws Exception {
        Object metier = Presentation2.instantiateClass("metier.MetierImpl");
        Object dao = Presentation2.instantiateClass("dao.DaoImpl");
        Presentation2.injectDependency(metier, "setDao", dao);
        assertEquals(200.0, ((IMetier) metier).calcul(), 0.0001);
    }

    @Test
    @DisplayName("Chaine complete config.txt -> 200.0")
    void chaineCompleteDepuisConfig() throws Exception {
        String[] names = Presentation2.loadClassNames("config.txt");
        Object dao = Presentation2.instantiateClass(names[0]);
        Object metier = Presentation2.instantiateClass(names[1]);
        Presentation2.injectDependency(metier, "setDao", dao);
        assertEquals(200.0, ((IMetier) metier).calcul(), 0.0001);
    }

    @Test
    @DisplayName("Chaine complete config-memoire.txt -> 84.0 sans recompilation")
    void chaineCompleteAvecAutreConfig() throws Exception {
        String[] names = Presentation2.loadClassNames("config-memoire.txt");
        Object dao = Presentation2.instantiateClass(names[0]);
        Object metier = Presentation2.instantiateClass(names[1]);
        Presentation2.injectDependency(metier, "setDao", dao);
        assertEquals(84.0, ((IMetier) metier).calcul(), 0.0001);
    }

    @Test
    @DisplayName("Presentation1 (statique) et Presentation2 (dynamique) donnent le meme resultat")
    void statiqueEtDynamiqueEquivalents() throws Exception {
        MetierImpl statique = new MetierImpl();
        statique.setDao(new DaoImpl());
        double a = statique.calcul();

        String[] names = Presentation2.loadClassNames("config.txt");
        Object metier = Presentation2.instantiateClass(names[1]);
        Presentation2.injectDependency(metier, "setDao", Presentation2.instantiateClass(names[0]));
        double b = ((IMetier) metier).calcul();

        assertEquals(a, b, 0.0001);
        assertTrue(a == 200.0);
    }
}
