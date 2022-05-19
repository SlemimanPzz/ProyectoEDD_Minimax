package minimax.Actores

import Estructuras.Lista
import minimax.mesa.Nodo

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
     * Mueve el actor sus fichas, bajo sus criteros.
     */
    fun mueve() : Int

    /**
     * Mueve una ficha en específico
     *
     * @return `true` si se logro el movimiento, `false` si no es posible.
     */
    fun mueveEspecifico(i : Int) : Boolean

    /**
     * Clona el actor
     *
     * @return un clon del Actor
     */
    fun clone() : Actor
}