package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Faction;
import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.model.characters.Character;

/**
 * Classe représentant une ville romaine dans le jeu.
 */
public final class RomanCity extends Space {

    /**
     * Constructeur de la classe RomanCity.
     *
     * @param name    Le nom de la ville romaine.
     * @param surface La surface de la ville romaine.
     * @param leader  Le personnage leader de la ville romaine.
     */
    public RomanCity(String name, double surface, Character leader) {
        super(name, surface, leader);
    }

    /**
     * Vérifie si un personnage est autorisé à entrer dans la ville romaine.
     *
     * @param c Le personnage à vérifier.
     * @return Vrai si le personnage appartient à la faction Romain ou est un Lycanthrope, sinon faux.
     */
    @Override
    public boolean authorized(Character c) {
        return c.getFaction() == Faction.ROMAIN || c instanceof Lycanthrope;
    }
}
