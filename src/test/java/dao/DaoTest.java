package dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DaoTest {

    @Test
    @DisplayName("DaoImpl retourne la valeur fixe 100.0")
    void daoImplRetourneCent() {
        assertEquals(100.0, new DaoImpl().getValue(), 0.0001);
    }

    @Test
    @DisplayName("DaoImplMemoire retourne la valeur fixe 42.0")
    void daoImplMemoireRetourneQuaranteDeux() {
        assertEquals(42.0, new DaoImplMemoire().getValue(), 0.0001);
    }

    @Test
    @DisplayName("Les deux implementations respectent le contrat IDao")
    void implementationsRespectentLeContrat() {
        assertTrue(IDao.class.isAssignableFrom(DaoImpl.class));
        assertTrue(IDao.class.isAssignableFrom(DaoImplMemoire.class));
    }

    @Test
    @DisplayName("Le constructeur par defaut public existe (requis par la reflexion)")
    void constructeurParDefautPresent() throws Exception {
        assertNotNull(DaoImpl.class.getDeclaredConstructor());
        assertNotNull(DaoImplMemoire.class.getDeclaredConstructor());
    }

    @Test
    @DisplayName("Une classe inconnue leve ClassNotFoundException")
    void classeInconnueLeveException() {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("dao.DaoInexistante"));
    }
}
