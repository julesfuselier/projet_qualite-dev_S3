package fr.amu.iut;

/**
 * Singleton gérant la configuration globale du jeu.
 * Utilise le pattern Singleton (Eager Initialization) pour garantir
 * une instance unique de configuration dans toute l'application.
 *
 * <p>Ce pattern est utilisé pour centraliser la gestion des paramètres
 * de simulation et éviter la duplication de configuration.</p>
 *
 * @see <a href="https://refactoring.guru/design-patterns/singleton">Singleton Pattern</a>
 */
public final class GameConfig {

    // ============================================
    // SINGLETON PATTERN - Eager Initialization
    // ============================================

    /**
     * Instance unique du Singleton, créée au chargement de la classe.
     * Thread-safe par garantie JVM.
     */
    private static final GameConfig INSTANCE = new GameConfig();

    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     * Garantit qu'une seule instance peut exister.
     */
    private GameConfig() {
        // Initialisation si nécessaire
    }

    /**
     * Retourne l'instance unique de GameConfig.
     *
     * @return L'instance Singleton de GameConfig
     */
    public static GameConfig getInstance() {
        return INSTANCE;
    }

    // ============================================
    // CONFIGURATION DU JEU
    // ============================================

    // Probabilités des événements
    private int probabilityHungerEvent = 10;
    private int probabilityFoodSpawn = 20;
    private int probabilityDruidPotion = 33;
    private int probabilityLycanReproduction = 10;
    private int probabilityLycanTransformation = 50;
    private int probabilityHowl = 20;

    // Paramètres de gameplay
    private int maxHungerIncrease = 5;
    private int maxPotionDecrease = 2;
    private int turnDurationMs = 3000;

    // Paramètres des personnages
    private int baseSize = 160;
    private int sizeVariation = 40;
    private int bonusStrengthPotion = 100;

    // Mode de difficulté
    private DifficultyMode difficultyMode = DifficultyMode.NORMAL;

    /**
     * Enum représentant les modes de difficulté.
     */
    public enum DifficultyMode {
        EASY(0.5),
        NORMAL(1.0),
        HARD(1.5);

        private final double multiplier;

        DifficultyMode(double multiplier) {
            this.multiplier = multiplier;
        }

        public double getMultiplier() {
            return multiplier;
        }
    }

    // ============================================
    // GETTERS
    // ============================================

    public int getProbabilityHungerEvent() {
        return (int)(probabilityHungerEvent * difficultyMode.getMultiplier());
    }

    public int getProbabilityFoodSpawn() {
        return probabilityFoodSpawn;
    }

    public int getProbabilityDruidPotion() {
        return probabilityDruidPotion;
    }

    public int getMaxHungerIncrease() {
        return maxHungerIncrease;
    }

    public int getMaxPotionDecrease() {
        return maxPotionDecrease;
    }

    public int getTurnDurationMs() {
        return turnDurationMs;
    }

    public int getBaseSize() {
        return baseSize;
    }

    public int getSizeVariation() {
        return sizeVariation;
    }

    public int getBonusStrengthPotion() {
        return bonusStrengthPotion;
    }

    public int getProbabilityLycanReproduction() {
        return probabilityLycanReproduction;
    }

    public int getProbabilityLycanTransformation() {
        return probabilityLycanTransformation;
    }

    public int getProbabilityHowl() {
        return probabilityHowl;
    }

    public DifficultyMode getDifficultyMode() {
        return difficultyMode;
    }

    // ============================================
    // SETTERS ( config dynamique )
    // ============================================

    /**
     * Modifie le mode de difficulté du jeu.
     * Affecte les probabilités des événements.
     *
     * @param mode Le nouveau mode de difficulté
     */
    public void setDifficultyMode(DifficultyMode mode) {
        this.difficultyMode = mode;
    }

    /**
     * Réinitialise la configuration aux valeurs par défaut.
     */
    public void resetToDefaults() {
        this.probabilityHungerEvent = 10;
        this.probabilityFoodSpawn = 20;
        this.difficultyMode = DifficultyMode.NORMAL;
        // TODO : Réinitialiser les autres paramètres si nécessaire
    }
}