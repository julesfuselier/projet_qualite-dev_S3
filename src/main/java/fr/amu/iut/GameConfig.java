package fr.amu.iut;

/**
 * Singleton gérant la configuration globale du jeu.
 * Utilise le pattern Singleton (Eager Initialization) pour garantir
 * une instance unique de configuration dans toute l'application.
 *
 * <p>Ce pattern est utilisé pour centraliser la gestion des paramètres
 * de simulation et éviter la duplication de configuration.</p>
 */
public final class GameConfig {

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

        /**
         * Constructeur de l'énumération.
         *
         * @param multiplier Multiplicateur affectant les probabilités
         */
        DifficultyMode(double multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Retourne le multiplicateur associé au mode de difficulté.
         *
         * @return Le multiplicateur
         */
        public double getMultiplier() {
            return multiplier;
        }
    }

    // ============================================
    // GETTERS
    // ============================================

    /**
     * Retourne la probabilité d'apparition d'un événement de faim,
     * ajustée selon le mode de difficulté.
     *
     * @return Probabilité ajustée d'un événement de faim
     */
    public int getProbabilityHungerEvent() {
        return (int)(probabilityHungerEvent * difficultyMode.getMultiplier());
    }

    /**
     * Retourne la probabilité d'apparition de nourriture,
     * ajustée selon le mode de difficulté.
     *
     * @return Probabilité ajustée d'apparition de nourriture
     */
    public int getProbabilityFoodSpawn() {
        return probabilityFoodSpawn;
    }

    /**
     * Retourne la probabilité d'apparition d'une potion de druide.
     *
     * @return Probabilité d'apparition d'une potion de druide
     */
    public int getProbabilityDruidPotion() {
        return probabilityDruidPotion;
    }

    /**
     * Retourne l'augmentation maximale de la faim par tour.
     *
     * @return Augmentation maximale de la faim
     */
    public int getMaxHungerIncrease() {
        return maxHungerIncrease;
    }

    /**
     * Retourne la diminution maximale de la faim par potion.
     *
     * @return Diminution maximale de la faim
     */
    public int getMaxPotionDecrease() {
        return maxPotionDecrease;
    }

    /**
     * Retourne la durée d'un tour en millisecondes.
     *
     * @return Durée d'un tour en ms
     */
    public int getTurnDurationMs() {
        return turnDurationMs;
    }

    /**
     * Retourne la taille de base des personnages.
     *
     * @return Taille de base
     */
    public int getBaseSize() {
        return baseSize;
    }

    /**
     * Retourne la variation de taille des personnages.
     *
     * @return Variation de taille
     */
    public int getSizeVariation() {
        return sizeVariation;
    }

    /**
     * Retourne la probabilité de reproduction des lycans.
     *
     * @return Probabilité de reproduction des lycans
     */
    public int getProbabilityLycanReproduction() {
        return probabilityLycanReproduction;
    }

    /**
     * Retourne la probabilité de transformation en lycan.
     *
     * @return Probabilité de transformation en lycan
     */
    public int getProbabilityLycanTransformation() {
        return probabilityLycanTransformation;
    }

    /**
     * Retourne la probabilité de hurlement des lycans.
     *
     * @return Probabilité de hurlement
     */
    public int getProbabilityHowl() {
        return probabilityHowl;
    }

    /**
     * Retourne le mode de difficulté actuel du jeu.
     *
     * @return Mode de difficulté
     */
    public DifficultyMode getDifficultyMode() {
        return difficultyMode;
    }

    /**
     * Returns the bonus strength added by the magic potion.
     *
     * @return the bonus strength value
     */
    public int getBonusStrengthPotion() {
        return BONUS_STRENGTH_POTION;
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

    /**
     * Bonus strength added by the magic potion.
     */
    public static final int BONUS_STRENGTH_POTION = 10; // Example value
}