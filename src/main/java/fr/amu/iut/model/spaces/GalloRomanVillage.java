package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.Character;

/**
 * Représente un village gallo-romain dans le jeu.
 * Seuls les personnages des factions Gaulois et Romain peuvent y accéder.
 */
public final class GalloRomanVillage extends Space {

    /**
     * Constructeur de la classe GalloRomanVillage.
     * @param name
     * @param surface
     * @param leader
     */
    public GalloRomanVillage(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    /**
     * Vérifie si un personnage est autorisé à entrer dans le village gallo-romain.
     * @param c Le personnage à vérifier.
     * @return Vrai si le personnage appartient à la faction Gaulois ou Romain, sinon faux.
     */
    @Override
    public boolean authorized(Character c) {
        return c.getFaction() == Faction.GAULOIS || c.getFaction() == Faction.ROMAIN;
    }
}
