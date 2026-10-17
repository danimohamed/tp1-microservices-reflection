# TP 1 : Programmation dynamique par réflexion dans une architecture Microservices

## Structure du projet : arborescence des packages `dao`, `metier` et `presentation`
![Structure du projet](images/structure.png)

## Exécution : `mvn exec:java` charge les classes depuis `config.txt` → `Resultats = 200.0`
![Exécution du projet](images/execution.png)

## Changer d'implémentation sans recompiler : `DaoImpl2` via `config-dao2.txt` → `Resultats = 84.0`
![Configuration externe](images/configuration.png)

## Débogage : `ClassNotFoundException` sur un nom de classe invalide (`dao.DaoInexistante`)
![Gestion des erreurs](images/erreurs.png)

## Tests unitaires : `Tests run: 17, Failures: 0` — `BUILD SUCCESS`
![Tests unitaires](images/tests.png)