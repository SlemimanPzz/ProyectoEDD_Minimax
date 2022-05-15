package Minimax.Actores

import Estructuras.Lista
import Minimax.Mesa.Nodo

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

    fun mueveEspecifico(i : Int) : Boolean

    fun clone() : Actor
}