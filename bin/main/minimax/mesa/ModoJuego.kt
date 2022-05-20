package minimax.mesa

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

    /**
     * Regresa una representación en cadena de Modo de Juego.
     * @return una representación en cadena de Modo de Juego.
     */
    override fun toString(): String {
        return when(this){
            MINIMAX -> "Minimax"
            RANDOM -> "Random"
        }
    }
}