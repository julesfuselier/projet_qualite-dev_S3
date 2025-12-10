# Simulation Gaulois et Lycanthropes

## À propos du projet

"Simulation Gaulois et Lycanthropes" est un jeu de simulation stratégique au tour par tour qui se déroule dans un environnement inspiré de la Gaule antique, avec une touche de fantaisie. Le jeu est entièrement basé sur une interface en console, où le joueur peut gérer des clans, des personnages, et interagir avec un monde dynamique.

Le projet est développé en Java et utilise Maven pour la gestion des dépendances et du build.

## Prérequis

Pour compiler et exécuter ce projet, vous aurez besoin de :

-   **Java Development Kit (JDK)** : Version 17 ou supérieure.
-   **Apache Maven** : Pour gérer le build et les dépendances du projet.

## Comment lancer le jeu

1.  **Clonez le dépôt** (si ce n'est pas déjà fait) ou assurez-vous d'être à la racine du projet.
2.  **Ouvrez un terminal** ou une invite de commande dans le répertoire du projet.
3.  **Compilez le projet** avec Maven :
    ```bash
    mvn compile
    ```
4.  **Exécutez l'application** via le plugin Maven Exec :
    ```bash
    mvn exec:java -Dexec.mainClass="fr.amu.iut.MainApp"
    ```

L'application se lancera directement dans votre terminal.

## Manuel d'utilisation

### 1. Démarrage de la simulation

Au lancement, le jeu vous propose deux modes de départ :

-   **Mode DÉMO** : Ce mode génère automatiquement un monde pré-configuré avec un village gaulois peuplé de personnages iconiques (comme Abraracourcix et Panoramix) et un camp romain. C'est le moyen le plus rapide de découvrir le jeu.
-   **Mode MANUEL** : Ce mode vous donne le contrôle total pour créer votre propre monde. Vous pourrez créer des lieux (villages, camps, champs de bataille...) puis y placer les personnages de votre choix.

### 2. Le déroulement d'un tour

Le jeu est structuré en tours. Le menu principal vous offre plusieurs options :

1.  **Lancer la simulation temporelle** : Fait avancer le temps d'un tour. Des événements automatiques se produisent :
    -   Déplacements autonomes des personnages.
    -   Déclenchement des batailles entre factions ennemies sur un même lieu.
    -   Gestion de la faim des personnages.
    -   Apparition de nourriture.
    -   Mise à jour de la fraîcheur des aliments.

2.  **Actions de Chef de Clan** : Permet de prendre le contrôle d'un personnage de type "Chef de Clan" pour effectuer des actions manuelles (voir ci-dessous).

3.  **Afficher l'état du monde** : Affiche la liste de tous les personnages, leur état et leur localisation.

4.  **Quitter** : Termine la simulation.

### 3. Factions et Personnages

Le monde est peuplé de deux factions principales :

-   **Gaulois** : Peuvent avoir les métiers de Druide, Forgeron, Aubergiste, Marchand, et Chef de clan.
-   **Romains** : Peuvent être Légionnaire, Général, Préfet, ou Chef de clan.

Chaque rôle a des spécificités qui influencent le déroulement du jeu.

### 4. Actions du Joueur (en tant que Chef de Clan)

En choisissant l'option "Actions de Chef de Clan", vous pouvez sélectionner un chef disponible et effectuer les actions suivantes dans le lieu où il se trouve :

-   **Examiner le lieu** : Donne des informations sur le lieu actuel.
-   **Soigner un personnage** : Permet de restaurer les points de vie d'un autre personnage.
-   **Nourrir un personnage** : Permet de donner de la nourriture à un personnage pour calmer sa faim.
-   **Distribuer une potion magique** : Si un druide avec une potion se trouve sur le même lieu, le chef peut ordonner de donner la potion à un autre personnage pour le renforcer.
-   **Retour au menu principal**.