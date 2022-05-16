package Minimax.Mesa

/**
 * Modos de juego posibles contra una CPU
 */
enum class ModoJuego {
    /**
     * Este utilizará el minimax para decidir su siguiente movimiento
     */
    MINIMAX,

    /**
     * Movimientos aleatorios
     */
    RANDOM;

    override fun toString(): String {
        return when(this){
            MINIMAX -> "Minimax"
            RANDOM -> "Random"
        }
    }
}