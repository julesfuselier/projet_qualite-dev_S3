package fr.amu.iut.model.lycanthropes;

import fr.amu.iut.model.characters.jobs.Lycanthrope;
import fr.amu.iut.util.GameEvents;

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
        GameEvents.log(">>> HURLEMENT <<<");
        GameEvents.log("Émetteur : " + emitter.getName());
        GameEvents.log("Meute : " + (emitter.getPack() != null ? emitter.getPack().getName() : "Solitaire"));
        GameEvents.log("Type : " + type.getLabel());
        GameEvents.log("Message : " + message);
        GameEvents.log("-----------------");
    }

    public Lycanthrope getEmitter() { return emitter; }
    public HowlType getType() { return type; }
}