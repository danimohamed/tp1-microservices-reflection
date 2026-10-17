package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Conteneur de chargement dynamique.
 *
 * <p>Il lit les noms de classes dans un fichier de configuration, les charge
 * par reflexion ({@link Class#forName(String)}), les instancie
 * ({@link Constructor#newInstance(Object...)}) puis injecte les dependances
 * via les setters ({@link Method#invoke(Object, Object...)}).</p>
 *
 * <p>C'est exactement le mecanisme que Spring ou CDI automatisent avec des
 * annotations, mais ici uniquement avec les APIs natives du JDK.</p>
 */
public class Presentation2 {

    private static final String DEFAULT_CONFIG = "config.txt";

    public static void main(String[] args) {
        String configFile = args.length > 0 ? args[0] : DEFAULT_CONFIG;
        try {
            System.out.println("[CONTAINER] Fichier de configuration : " + configFile);

            String[] classNames = loadClassNames(configFile);

            IDao dao = (IDao) instantiateClass(classNames[0]);
            System.out.println("[CONTAINER] DAO instancie : " + dao.getClass().getName());

            IMetier metier = (IMetier) instantiateClass(classNames[1]);
            System.out.println("[CONTAINER] Metier instancie : " + metier.getClass().getName());

            injectDependency(metier, "setDao", dao);
            System.out.println("[CONTAINER] Dependance injectee via setDao(IDao)");

            double resultat = metier.calcul();
            System.out.println("Resultats = " + resultat);
        } catch (Exception e) {
            System.err.println("[CONTAINER] Echec du chargement : " + e);
            e.printStackTrace();
        }
    }

    /**
     * Lit les noms de classes depuis un fichier de configuration.
     * Les lignes vides et les commentaires commencant par # sont ignores.
     */
    static String[] loadClassNames(String fileName) throws IOException {
        List<String> names = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    names.add(line);
                }
            }
        }
        if (names.size() < 2) {
            throw new IllegalStateException(
                    "Le fichier " + fileName + " doit contenir au moins 2 noms de classes");
        }
        return names.toArray(new String[0]);
    }

    /**
     * Charge une classe par son nom complet et l'instancie via son
     * constructeur par defaut.
     */
    static Object instantiateClass(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        return constructor.newInstance();
    }

    /**
     * Injecte une dependance dans un objet via une methode setter.
     * Le type du parametre est deduit de la premiere interface implementee
     * par la dependance (ici {@code IDao}).
     */
    static void injectDependency(Object target, String setterName, Object dependency) throws Exception {
        Class<?> targetClass = target.getClass();
        Method setterMethod = targetClass.getMethod(setterName, dependency.getClass().getInterfaces()[0]);
        setterMethod.invoke(target, dependency);
    }
}
