# Manuel d'Utilisation - Scénarios de Jeu

Bienvenue dans le manuel des scénarios pour la **Simulation d'envahissement de l'Armorique**. Ce guide vous explique comment exploiter les différentes fonctionnalités de la simulation, du lancement rapide à la gestion micro-stratégique.

---

## Scénario 1 : Lancement Rapide (Mode Démo)
*Idéal pour découvrir le jeu sans configuration préalable.*

Ce mode génère automatiquement une carte complète avec des personnages emblématiques (Abraracourcix, Panoramix, Romains, etc.).

1. **Lancement** : Lancez l'application.
2. **Choix du mode** : À la question *"Comment voulez-vous commencer ?"*, tapez `1` (Mode DÉMO).
3. **Résultat** : Le système initialise :
   - **Lieux** : Village des Irréductibles, Camps Romains (Babaorum), Lutetia, etc.
   - **Personnages** : Gaulois (Druides, Forgerons) et Romains (Légionnaires, Centurions) répartis stratégiquement.
4. **Action** : Une fois au menu principal, choisissez `3` (**Afficher l'état du monde**) pour voir la population générée avant de lancer le premier tour.

---

## Scénario 2 : Création de Monde Personnalisé (Mode Manuel)
*Pour tester des configurations spécifiques (ex: 50 Romains contre 1 Gaulois).*

Ce mode vous permet de construire l'environnement de A à Z.

1. **Choix du mode** : Au lancement, tapez `2` (Mode MANUEL).
2. **Phase 1 : Création des Lieux**
   - Ajoutez des lieux via le menu (ex: `2` pour un Village Gaulois).
   - *Conseil* : Créez au moins deux lieux (un pour chaque faction) pour permettre les déplacements.
   - Tapez `4` pour valider la carte.
3. **Phase 2 : Création des Personnages**
   - Sélectionnez la faction (Gaulois ou Romain).
   - Le système crée un personnage avec un métier aléatoire adapté à la faction.
   - **Placement** : Choisissez le lieu d'apparition.
   - *Attention* : Un Romain ne peut pas être placé initialement dans un Village Gaulois (règle d'autorisation).
4. **Finalisation** : Tapez `3` pour terminer l'initialisation et accéder au menu principal.

---

## Scénario 3 : Gestion Tactique (Incarner un Chef de Clan)
*Prenez le contrôle d'Abraracourcix ou d'un chef Romain pour influencer le destin de vos troupes.*

Ce scénario nécessite qu'un personnage de type `ClanLeader` soit présent sur la carte.

1. **Accès au menu** : Dans le menu principal, tapez `2` (**Actions de Chef de Clan**).
2. **Sélection** : Choisissez le chef que vous souhaitez incarner dans la liste proposée.
3. **Actions Disponibles** :
   - **Examiner le lieu (`1`)** : Affiche les ressources (nourriture) et les personnes présentes dans la zone actuelle du chef.
   - **Soigner (`2`)** : Sélectionnez un allié blessé dans la même zone pour lui rendre **20 PV**.
   - **Nourrir (`3`)** : Distribuez de la nourriture présente dans le lieu à un personnage affamé. *Condition : Il doit y avoir de la nourriture au sol.*
   - **Distribuer une potion (`4`)** : Ordre stratégique majeur.
     - *Prérequis* : Un **Druide** possédant une **Potion Magique** doit être présent dans le même lieu que le Chef.
     - Le chef ordonne au druide de donner la potion à un personnage cible (ex: un simple soldat qui gagnera une force surhumaine).

---

## Scénario 4 : Observation de la Meute (Les Lycanthropes)
*Suivez l'évolution autonome de la faction sauvage.*

Les Lycanthropes (Loups-garous) agissent différemment des humains. Ils possèdent une hiérarchie stricte.

1. **Configuration** : Assurez-vous que des Lycanthropes sont présents (via le mode Démo ou Manuel).
2. **Déroulement du temps** : Lancez plusieurs tours successifs via l'option `1` du menu principal.
3. **Observation** : Régulièrement, utilisez l'option `3` (**Afficher l'état du monde**) pour surveiller les colonies de Lycanthropes.
4. **Événements à surveiller** :
   - **Hiérarchie** : Regardez si le couple **Alpha** change suite à des combats de domination.
   - **Reproduction** : Si un couple Alpha est établi, de nouveaux membres (enfants) peuvent apparaître dans la meute.
   - **Rang Omega** : Identifiez le membre le plus faible (Omega), souvent victime de la meute.

---

## Scénario 5 : Simulation Automatisée (Mode Spectateur)
*Laissez la simulation tourner seule pour voir combien de temps les factions survivent.*

1. **Activation** : Depuis le menu principal, choisissez l'option `4` (**MODE AUTO**).
2. **Fonctionnement** :
   - Le jeu lance un *Thread* séparé.
   - Un nouveau tour est calculé toutes les **3 secondes**.
   - La console affiche les événements en temps réel (Batailles, Apparition de nourriture, Déplacements).
3. **Interruption** : Appuyez sur la touche `[ENTRÉE]` pour stopper le mode automatique et reprendre la main sur le menu principal.

---

## 💡 Conseils de Jeu

- **Les Druides sont vitaux** : Sans druide pour fabriquer des potions, les Gaulois perdent leur avantage principal. Assurez-vous qu'ils restent en vie.
- **La Faim tue** : Les personnages perdent des points de vie s'ils ne mangent pas. En tant que Chef, priorisez l'action **Nourrir** si la nourriture se fait rare.
- **Batailles de lieux** : Si des ennemis se retrouvent sur le même lieu (ex: Romains envahissant un Village Gaulois), le combat s'enclenche automatiquement au début du tour (`runNextTurn`).


