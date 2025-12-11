package fr.amu.iut.model.items;

/**
 * Classe abstraite représentant un objet dans le jeu.
 */
public abstract class Item {

    private String name;

    /**
     * Constructeur de la classe Item.
     *
     * @param name Le nom de l'objet.
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Obtient le nom de l'objet.
     *
     * @return Le nom de l'objet.
     */
    public String getName() {
        return name;
    }
}
