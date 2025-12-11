package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;
import fr.amu.iut.model.characters.jobs.Lycanthrope;

/**
 * Représente un enclos dans le jeu, accessible uniquement aux lycanthropes.
 */
public final class Enclosure extends Space {

    /**
     * Constructeur de la classe Enclosure.
     *
     * @param name    Le nom de l'enclos.
     * @param surface La surface de l'enclos.
     */
    public Enclosure(String name, double surface) {
        super(name, surface);
    }

    /**
     * Vérifie si un personnage est autorisé à entrer dans l'enclos.
     *
     * @param c Le personnage à vérifier.
     * @return Vrai si le personnage est un lycanthrope, sinon faux.
     */
    @Override
    public boolean authorized(Character c){
        return c instanceof Lycanthrope;
    }
}
