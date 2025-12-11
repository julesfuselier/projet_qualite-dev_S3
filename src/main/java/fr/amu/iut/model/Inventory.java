package fr.amu.iut.model;

import fr.amu.iut.model.items.Item;
import fr.amu.iut.util.GameEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un inventaire pouvant contenir des objets de type Item ou ses sous-classes.
 * L'inventaire a une capacité maximale de 64 objets.
 *
 * @param <T> Le type d'objet que l'inventaire peut contenir, doit être une sous-classe de Item.
 */
public class Inventory<T extends Item> {

    private List<T> items;
    private static final int MAX_CAPACITY = 64;

    /**
     * Constructeur de la classe Inventory.
     * Initialise une liste vide d'objets.
     */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Ajoute un objet à l'inventaire si la capacité maximale n'est pas atteinte.
     *
     * @param item L'objet à ajouter.
     */
    public void addItem(T item) {
        if (items.size() < MAX_CAPACITY) {
            items.add(item);
        } else {
            GameEvents.log("Inventaire plein ! Impossible d'ajouter " + item.getName());
        }
    }

    /**
     * Retire un objet de l'inventaire.
     *
     * @param item L'objet à retirer.
     * @return true si l'objet a été retiré avec succès, false sinon.
     */
    public boolean removeItem(T item) {
        return items.remove(item);
    }

    /**
     * Retourne la liste des objets dans l'inventaire.
     *
     * @return La liste des objets.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Retourne la capacité maximale de l'inventaire.
     *
     * @return La capacité maximale.
     */
    public int getCapacity() {
        return MAX_CAPACITY;
    }

    /**
     * Retourne le nombre actuel d'objets dans l'inventaire.
     *
     * @return Le nombre d'objets.
     */
    public int getCurrentSize() {
        return items.size();
    }
}