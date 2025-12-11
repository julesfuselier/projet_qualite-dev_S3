package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Fighter;
import fr.amu.iut.model.characters.jobs.Lycanthrope;

/**
 * Représente un camp fortifié romain dans le jeu.
 * Un camp fortifié romain autorise les personnages de la faction romaine
 * qui sont des combattants, ainsi que les lycanthropes.
 */
public final class RomanFortifiedCamp extends Space {

    /**
     * Constructeur de la classe RomanFortifiedCamp.
     *
     * @param name    Le nom du camp fortifié romain.
     * @param surface La surface du camp fortifié romain.
     * @param leader  Le personnage leader du camp fortifié romain.
     */
    public RomanFortifiedCamp(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    /**
     * Vérifie si un personnage est autorisé à entrer dans le camp fortifié romain.
     *
     * @param c Le personnage à vérifier.
     * @return Vrai si le personnage appartient à la faction Romain et est un combattant,
     * ou s'il est un Lycanthrope, sinon faux.
     */
    @Override
    public boolean authorized(Character c) {
        return (c.getFaction() == Faction.ROMAIN && c instanceof Fighter) || c instanceof Lycanthrope;
    }

}
