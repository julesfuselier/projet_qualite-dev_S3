package fr.amu.iut.model.spaces;

import fr.amu.iut.model.characters.Character;

/**
 * Classe représentant un champ de bataille dans le jeu.
 * Un champ de bataille est un espace où tous les personnages sont autorisés à entrer.
 */
public final class Battlefield extends Space{

    /**
     * Constructeur de la classe Battlefield.
     *
     * @param name    Le nom du champ de bataille.
     * @param surface La surface du champ de bataille.
     */
    public Battlefield(String name, double surface){
        super(name, surface);
    }

    /**
     * Vérifie si un personnage est autorisé à entrer dans le champ de bataille.
     *
     * @param c Le personnage à vérifier.
     * @return Toujours vrai, car tous les personnages sont autorisés.
     */
    @Override
    public boolean authorized(Character c){
        return true;
    }
}
