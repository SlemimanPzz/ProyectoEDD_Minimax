import Estructuras.Lista

/**
 * Interfaz para actores dentro del juego
 *
 * @property nodosJugador Una [Lista] de todos los nodos actuales que ocupa el jugador
 * @property oponente Oponente del mismo
 */
interface Actor {

    var nodosJugador : Lista<Nodo>
    var oponente : Actor?

    /**
     * Mueve el actor sus fichas
     */
    fun mueve()
}