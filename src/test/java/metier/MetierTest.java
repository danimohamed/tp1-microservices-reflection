package metier;

import dao.DaoImpl;
import dao.DaoImplMemoire;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetierTest {

    @Test
    @DisplayName("calcul() double la valeur fournie par le DAO : 100 -> 200")
    void calculDoubleLaValeur() {
        MetierImpl metier = new MetierImpl();
        metier.setDao(new DaoImpl());
        assertEquals(200.0, metier.calcul(), 0.0001);
    }

    @Test
    @DisplayName("calcul() reflete la nouvelle implementation : 42 -> 84")
    void calculAvecAutreDao() {
        MetierImpl metier = new MetierImpl();
        metier.setDao(new DaoImplMemoire());
        assertEquals(84.0, metier.calcul(), 0.0001);
    }

    @Test
    @DisplayName("calcul() sans injection leve IllegalStateException")
    void calculSansDaoLeveException() {
        MetierImpl metier = new MetierImpl();
        IllegalStateException ex = assertThrows(IllegalStateException.class, metier::calcul);
        assertTrue(ex.getMessage().contains("DAO"));
    }

    @Test
    @DisplayName("setDao conserve la reference injectee")
    void setDaoConserveLaReference() {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);
        assertSame(dao, metier.getDao());
    }
}
