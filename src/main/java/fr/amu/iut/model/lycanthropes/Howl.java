package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;

/**
 * Représente un hurlement émis par un lycanthrope.
 */
public class Howl {
    private final Lycanthrope emitter;
    private final HowlType type;
    private final String message;

    public Howl(Lycanthrope emitter, HowlType type, String message) {
        this.emitter = emitter;
        this.type = type;
        this.message = message;
    }

    public void showCharacteristics() {
        System.out.println(">>> HURLEMENT <<<");
        System.out.println("Émetteur : " + emitter.getName());
        System.out.println("Meute : " + (emitter.getPack() != null ? emitter.getPack().getName() : "Solitaire"));
        System.out.println("Type : " + type.getLabel());
        System.out.println("Message : " + message);
        System.out.println("-----------------");
    }

    public Lycanthrope getEmitter() { return emitter; }
    public HowlType getType() { return type; }
}