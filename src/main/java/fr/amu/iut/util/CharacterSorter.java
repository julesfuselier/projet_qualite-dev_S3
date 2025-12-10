package fr.amu.iut.util;

import fr.amu.iut.model.characters.Character;
import java.util.List;

public class CharacterSorter {

    /**
     * Trie une liste de personnages par Nom en utilisant l'algorithme QuickSort.
     * Complexité moyenne : O(n log n)
     */
    public static void quickSortByName(List<Character> list) {
        if (list == null || list.isEmpty()) return;
        quickSort(list, 0, list.size() - 1);
    }

    private static void quickSort(List<Character> list, int low, int high) {
        if (low < high) {
            // pi est l'index de partition
            int pi = partition(list, low, high);

            // Tri récursif des éléments avant et après la partition
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private static int partition(List<Character> list, int low, int high) {
        // On choisit le dernier élément comme pivot
        String pivotName = list.get(high).getName();
        int i = (low - 1); // Index du plus petit élément

        for (int j = low; j < high; j++) {
            // Si le nom courant est alphabétiquement plus petit que le pivot
            if (list.get(j).getName().compareToIgnoreCase(pivotName) < 0) {
                i++;
                swap(list, i, j);
            }
        }

        // On place le pivot à sa position correcte
        swap(list, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Character> list, int i, int j) {
        Character temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}